package com.cowradio.microservicesecurity.services;

import com.cowradio.microservicesecurity.config.jwt.JwtUtils;
import com.cowradio.microservicesecurity.entities.Role;
import com.cowradio.microservicesecurity.entities.User;
import com.cowradio.microservicesecurity.entities.dtos.AuthResponse;
import com.cowradio.microservicesecurity.entities.dtos.TokenDto;
import com.cowradio.microservicesecurity.entities.dtos.UserRegisterDto;
import com.cowradio.microservicesecurity.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public User createUser(UserRegisterDto userRegisterDto) {
        return userRepository.save(new User(userRegisterDto.username(), userRegisterDto.email(),
                passwordEncoder.encode(userRegisterDto.password())));
    }

    @Override
    public AuthResponse login(String username, String password) {
        UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(username,
                password);
        authenticationManager.authenticate(userAuth);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: "+username));
        return new AuthResponse(jwtUtils.createToken(user), user.getPlaylists());
    }

    @Override
    public TokenDto validateToken(String token) {
        String username = jwtUtils.extractUsername(token);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found: "+username));
        if(jwtUtils.validateUser(user, token)){
            return new TokenDto(token);
        }
        return null;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found: "+username));
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User deleteUser(User user) {
        return null;
    }
}
