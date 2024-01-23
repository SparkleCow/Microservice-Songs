package com.microserviceplaylist.controllers;

import com.microserviceplaylist.entities.Playlist;
import com.microserviceplaylist.services.PlaylistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping("")
    public ResponseEntity<Playlist> createPlaylist(@RequestParam String playlistName,
                                                   HttpServletRequest request){
        return ResponseEntity.ok(playlistService.createPlaylist(playlistName, request));
    }

    @PostMapping("/")
    public ResponseEntity<Playlist> addSongToPlaylist(@RequestParam Long playlistId,
                                                  @RequestParam Long songId){
        return ResponseEntity.ok(playlistService.addSongToPlaylist(playlistId, songId));
    }

    @GetMapping
    public ResponseEntity<List<Playlist>> findAllPlaylist(){
        return ResponseEntity.ok(playlistService.findAll());
    }

    @GetMapping("/byUsername")
    public List<String>
}
