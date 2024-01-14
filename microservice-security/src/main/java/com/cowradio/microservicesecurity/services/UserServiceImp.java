package com.cowradio.microservicesecurity.services;

import com.cowradio.microservicesecurity.entities.Role;
import com.cowradio.microservicesecurity.entities.User;
import com.cowradio.microservicesecurity.entities.dtos.UserRegisterDto;
import com.cowradio.microservicesecurity.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
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
    public User createUser(UserRegisterDto userRegisterDto) {
        return userRepository.save(new User(null, userRegisterDto.username(), userRegisterDto.email(),
                passwordEncoder.encode(userRegisterDto.password()), Set.of(Role.USER), Set.of()));
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
