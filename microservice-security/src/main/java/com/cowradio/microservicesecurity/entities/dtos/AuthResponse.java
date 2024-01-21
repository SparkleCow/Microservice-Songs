package com.cowradio.microservicesecurity.entities.dtos;

import com.cowradio.microservicesecurity.entities.Playlist;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {
    public String token;
    public List<Playlist> playlist;
}
