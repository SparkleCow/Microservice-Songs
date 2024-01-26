package com.microserviceplaylist.controllers;

import com.microserviceplaylist.entities.Playlist;
import com.microserviceplaylist.entities.PlaylistUpdateDto;
import com.microserviceplaylist.exceptions.AuthenticationException;
import com.microserviceplaylist.exceptions.DuplicateElementException;
import com.microserviceplaylist.exceptions.ResultNotFoundException;
import com.microserviceplaylist.exceptions.SaveFailureException;
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

    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestParam String playlistName, HttpServletRequest request)
            throws DuplicateElementException,
            AuthenticationException,
            SaveFailureException
    {
        return ResponseEntity.ok(playlistService.createPlaylist(playlistName, request));
    }

    @PostMapping("/addSong/")
    public ResponseEntity<Playlist> addSongToPlaylist(@RequestParam Long playlistId, @RequestParam Long songId)
            throws DuplicateElementException,
            ResultNotFoundException,
            SaveFailureException
    {
        return ResponseEntity.ok(playlistService.addSongToPlaylist(playlistId, songId));
    }

    @DeleteMapping("/removeSong/")
    public ResponseEntity<Playlist> removeSongToPlaylist(@RequestParam Long playlistId, @RequestParam Long songId)
            throws ResultNotFoundException,
            SaveFailureException
    {
        return ResponseEntity.ok(playlistService.removeSongToPlaylist(playlistId, songId));
    }

    @GetMapping
    public ResponseEntity<List<Playlist>> findAllPlaylist(){
        return ResponseEntity.ok(playlistService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> findPlaylistById(@PathVariable Long id) throws ResultNotFoundException {
        return ResponseEntity.ok(playlistService.findById(id));
    }

    @GetMapping("/byUsername/feign/{username}")
    public List<String> findAllPlaylistByUsernameFeign(@PathVariable String username){
        return playlistService.findByUsernameFeign(username);
    }

    @GetMapping("/byUsername/{username}")
    public List<Playlist> findByUsername(@PathVariable String username){
        return playlistService.findByUsername(username);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> updatePlaylist(@RequestBody PlaylistUpdateDto playlistUpdateDto, @PathVariable Long id)
            throws ResultNotFoundException,
            SaveFailureException
    {
        return ResponseEntity.ok(playlistService.updatePlaylist(playlistUpdateDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylistById(@PathVariable Long id) throws ResultNotFoundException{
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }
}
