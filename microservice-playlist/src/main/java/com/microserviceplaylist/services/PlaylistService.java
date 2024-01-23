package com.microserviceplaylist.services;

import com.microserviceplaylist.entities.Playlist;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PlaylistService {
    Playlist addSongToPlaylist(Long playlistId, Long songId);
    Playlist removeSongToPlaylist(Long playlistId, Long songId);
    Playlist createPlaylist(String playlistName, HttpServletRequest request);
    Playlist findById(Long id);
    Playlist findByName(String name);
    List<Playlist> findAll();
    List<Playlist> findByContainingName(String name);
    List<Playlist> findByUsername(String username);
    Playlist updatePlaylist(Playlist playlist);
    void deletePlaylist(String id);
}
