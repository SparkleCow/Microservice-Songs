package com.cowradio.microservicesecurity.services;

import com.cowradio.microservicesecurity.entities.User;
import com.cowradio.microservicesecurity.entities.dtos.AuthResponse;
import com.cowradio.microservicesecurity.entities.dtos.TokenDto;
import com.cowradio.microservicesecurity.entities.dtos.UserRegisterDto;

import java.util.List;

public interface UserService {
    User createUser(UserRegisterDto user);
    AuthResponse login(String username, String password);
    TokenDto validateToken(String token);
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAllUser();
    void deleteUserById(Long id);
    void addPlaylistInUser(String username, String playlistName);
    void removePlaylistInUser(String username, String playlistName);
    void updatePlaylistInUser(String username, String playlistName, String newPlaylistName);
}
