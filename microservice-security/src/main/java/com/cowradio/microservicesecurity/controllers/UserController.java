package com.cowradio.microservicesecurity.controllers;

import com.cowradio.microservicesecurity.config.jwt.JwtUtils;
import com.cowradio.microservicesecurity.entities.User;
import com.cowradio.microservicesecurity.entities.dtos.UserLoginDto;
import com.cowradio.microservicesecurity.entities.dtos.UserRegisterDto;
import com.cowradio.microservicesecurity.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("")
    public ResponseEntity createUser(@RequestBody UserRegisterDto userRegisterDto){
        User user = userService.createUser(userRegisterDto);
        URI location = URI.create("/api/v1/user/" + user.getId());
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto userLoginDto){
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userLoginDto.username(),
                userLoginDto.password());
        authenticationManager.authenticate(user);
        UserDetails userDetails = userService.findByUsername(userLoginDto.username());
        System.out.println(userDetails.toString());
        System.out.println(jwtUtils.createToken(userDetails));
        return ResponseEntity.ok(jwtUtils.createToken(userDetails));
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUser());
    }

}
