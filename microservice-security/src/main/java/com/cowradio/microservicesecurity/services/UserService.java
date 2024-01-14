package com.cowradio.microservicesecurity.services;

import com.cowradio.microservicesecurity.entities.User;
import com.cowradio.microservicesecurity.entities.dtos.UserRegisterDto;

import java.util.List;

public interface UserService {

    //Encontrar usuarios
    User findById(int id);
    User findByUsername(String username);
    List<User> findAllUser();

    //Creación de usuarios
    User createUser(UserRegisterDto user);
    User updateUser(User user);

    //Eliminar usuario
    User deleteUser(User user);
}
