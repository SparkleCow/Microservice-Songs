package com.cowradio.microservicesecurity.controllers;

import com.cowradio.microservicesecurity.entities.User;
import com.cowradio.microservicesecurity.entities.dtos.AuthResponse;
import com.cowradio.microservicesecurity.entities.dtos.TokenDto;
import com.cowradio.microservicesecurity.entities.dtos.UserLoginDto;
import com.cowradio.microservicesecurity.entities.dtos.UserRegisterDto;
import com.cowradio.microservicesecurity.exceptions.DuplicateElementException;
import com.cowradio.microservicesecurity.exceptions.ResultNotFoundException;
import com.cowradio.microservicesecurity.exceptions.SaveFailureException;
import com.cowradio.microservicesecurity.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody UserRegisterDto userRegisterDto) throws
                                            DuplicateElementException,
                                            SaveFailureException,
                                            ResultNotFoundException{
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

    @GetMapping("/findById/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) throws ResultNotFoundException{
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) throws ResultNotFoundException{
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAllUser(){
        return ResponseEntity.ok(userService.findAllUser());
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteUserById(@PathVariable Long id) throws ResultNotFoundException{
        userService.deleteUserById(id);
    }

    @PostMapping("/addPlaylist/feign")
    public void addPlaylistInformation(@RequestParam String username, @RequestParam String playlistName){
        userService.addPlaylistInUser(username, playlistName);
    }

    @DeleteMapping("/removePlaylist/feign")
    public void removePlaylistInformation(@RequestParam String username, @RequestParam String playlistName){
        userService.removePlaylistInUser(username, playlistName);
    }

    @PutMapping("/updatePlaylist/feign")
    public void updatePlaylistInformation(@RequestParam String username,
                                          @RequestParam String playlistName, @RequestParam String newPlaylistName){
        userService.updatePlaylistInUser(username, playlistName, newPlaylistName);
    }
}
