package com.cowradio.microservicesecurity.controllers;

import com.cowradio.microservicesecurity.config.jwt.JwtUtils;
import com.cowradio.microservicesecurity.entities.User;
import com.cowradio.microservicesecurity.entities.dtos.AuthResponse;
import com.cowradio.microservicesecurity.entities.dtos.TokenDto;
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

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody UserRegisterDto userRegisterDto){
        User user = userService.createUser(userRegisterDto);
        URI location = URI.create("/api/v1/user/" + user.getId());
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody UserLoginDto userLoginDto){
        return ResponseEntity.ok(userService.login(userLoginDto.username(), userLoginDto.password()));
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validateToken(@RequestParam String token){
        TokenDto tokenDto = userService.validateToken(token);
        if(tokenDto == null || tokenDto.getToken().isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/addPlaylist/feign")
    public void addPlaylistInformation(@RequestParam String username, @RequestParam String playlistName){
        userService.addPlaylistInUser(username, playlistName);
    }

    @PostMapping("/removePlaylist/feign")
    public void removePlaylistInformation(@RequestParam String username, @RequestParam String playlistName){
        userService.removePlaylistInUser(username, playlistName);
    }

    @PostMapping("/updatePlaylist/feign")
    public void updatePlaylistInformation(@RequestParam String username,
                                          @RequestParam String playlistName, @RequestParam String newPlaylistName){
        userService.updatePlaylistInUser(username, playlistName, newPlaylistName);
    }
}
