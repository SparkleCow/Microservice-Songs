package com.microserviceplaylist.services;

import com.microserviceplaylist.entities.Playlist;
import com.microserviceplaylist.entities.PlaylistUpdateDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PlaylistService {
    Playlist addSongToPlaylist(Long playlistId, Long songId);
    Playlist removeSongToPlaylist(Long playlistId, Long songId);
    Playlist createPlaylist(String playlistName, HttpServletRequest request);
    Playlist findById(Long id);
    List<Playlist> findAll();
    List<Playlist> findByUsername(String username);
    Playlist updatePlaylist(PlaylistUpdateDto playlistUpdateDto, Long id);
    void deletePlaylist(Long id);
    List<String> findByUsernameFeign(String username);
}
