package com.microserviceplaylist.services;

import com.microserviceplaylist.client.SongClient;
import com.microserviceplaylist.entities.Playlist;
import com.microserviceplaylist.entities.Song;
import com.microserviceplaylist.entities.SongFeignDto;
import com.microserviceplaylist.repositories.PlaylistRepository;
import com.microserviceplaylist.repositories.SongRepository;
import com.microserviceplaylist.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImp implements PlaylistService{

    private final PlaylistRepository playlistRepository;
    private final JwtUtils jwtUtils;
    private final SongClient songService;
    private final SongRepository songRepository;

    @Override
    public Playlist addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow();
        Optional<Song> songOptional = songRepository.findById(songId);
        if(songOptional.isPresent()){
            playlist.getSongs().add(songOptional.get());
            return playlistRepository.save(playlist);
        }
        SongFeignDto songFeignDto = songService.findSongFeignById(songId);
        Song song = new Song(songFeignDto.id(), songFeignDto.songName(), songFeignDto.artistName(),
                    songFeignDto.views(), songFeignDto.albumUrlImg());

        songRepository.save(song);
        playlist.getSongs().add(song);
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist removeSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow();
        List<Song> songs = playlist.getSongs();
        songs.removeIf(song -> song.getId().equals(songId));
        playlist.setSongs(songs);
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist createPlaylist(String name, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return playlistRepository.save(new Playlist(null, name, jwtUtils.extractUsername(token), List.of()));
    }

    @Override
    public Playlist findById(Long id) {
        return playlistRepository.findById(id).orElseThrow();
    }

    @Override
    public Playlist findByName(String name) {
        return null;
    }

    @Override
    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    @Override
    public List<Playlist> findByContainingName(String name) {
        return null;
    }

    @Override
    public List<Playlist> findByUsername(String userId) {
        return null;
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist) {
        return null;
    }

    @Override
    public void deletePlaylist(String id) {

    }
}
