package com.cowradio.microservicesecurity.services;

import com.cowradio.microservicesecurity.config.jwt.JwtUtils;
import com.cowradio.microservicesecurity.entities.Role;
import com.cowradio.microservicesecurity.entities.User;
import com.cowradio.microservicesecurity.entities.dtos.AuthResponse;
import com.cowradio.microservicesecurity.entities.dtos.TokenDto;
import com.cowradio.microservicesecurity.entities.dtos.UserRegisterDto;
import com.cowradio.microservicesecurity.exceptions.DuplicateElementException;
import com.cowradio.microservicesecurity.exceptions.ResultNotFoundException;
import com.cowradio.microservicesecurity.exceptions.SaveFailureException;
import com.cowradio.microservicesecurity.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public User createUser(UserRegisterDto userRegisterDto) {
        User user = new User(userRegisterDto.username(), userRegisterDto.email(),
                passwordEncoder.encode(userRegisterDto.password()));
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new DuplicateElementException("User already exists ", user);
        }
        try{
            user.getRoles().add(Role.USER);
            return userRepository.save(user);
        }catch(RuntimeException e){
            throw new SaveFailureException("User could not be saved ", user);
        }
    }

    @Override
    public AuthResponse login(String username, String password) {
        UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(username,
                password);
        authenticationManager.authenticate(userAuth);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResultNotFoundException("Username not found with name: "+username));
        return new AuthResponse(jwtUtils.createToken(user), user.getPlaylists());
    }

    @Override
    public TokenDto validateToken(String token) {
        String username = jwtUtils.extractUsername(token);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResultNotFoundException("User not found with username: "+username));
        if(jwtUtils.validateUser(user, token)){
            return new TokenDto(token);
        }
        return null;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResultNotFoundException("User not found with id: "+id));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResultNotFoundException("Username not found: "+username));
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResultNotFoundException("User not found with id: "+id));
        userRepository.deleteById(id);
    }

    @Override
    public void addPlaylistInUser(String username, String playlistName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResultNotFoundException("Username not found: "+username));
        user.getPlaylists().add(playlistName);
        try{
            userRepository.save(user);
        }catch(RuntimeException e){
            throw new SaveFailureException("User could not be saved ", user);
        }
    }

    @Override
    public void removePlaylistInUser(String username, String playlistName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResultNotFoundException("Username not found: "+username));
        user.setPlaylists(user.getPlaylists().stream()
                .filter(a -> !a.equalsIgnoreCase(playlistName)).collect(Collectors.toList()));
        try{
            userRepository.save(user);
        }catch(RuntimeException e){
            throw new SaveFailureException("User could not be saved ", user);
        }
    }

    @Override
    public void updatePlaylistInUser(String username, String playlistName, String newPlaylistName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResultNotFoundException("Username not found: "+username));
        List<String> albums = user.getPlaylists();
        user.setPlaylists(albums.stream()
                .map(a -> a.equalsIgnoreCase(playlistName) ? newPlaylistName : a)
                .collect(Collectors.toList()));
        try{
            userRepository.save(user);
        }catch(RuntimeException e){
            throw new SaveFailureException("User could not be saved ", user);
        }
    }
}
