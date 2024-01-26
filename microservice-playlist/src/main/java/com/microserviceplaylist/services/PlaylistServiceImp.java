package com.microserviceplaylist.services;

import com.microserviceplaylist.client.SongClient;
import com.microserviceplaylist.entities.Playlist;
import com.microserviceplaylist.entities.PlaylistUpdateDto;
import com.microserviceplaylist.entities.Song;
import com.microserviceplaylist.entities.SongFeignDto;
import com.microserviceplaylist.exceptions.AuthenticationException;
import com.microserviceplaylist.exceptions.DuplicateElementException;
import com.microserviceplaylist.exceptions.ResultNotFoundException;
import com.microserviceplaylist.exceptions.SaveFailureException;
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
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(()-> new ResultNotFoundException("Playlist not found with id: "+playlistId));

        if(playlist.getSongs().stream().anyMatch(a -> a.getId().equals(songId)))
            throw new DuplicateElementException("Song already exists in playlist", playlist);

        Optional<Song> songOpt = songRepository.findById(songId);
        if(songOpt.isPresent()){
            Song song = songOpt.get();
            playlist.getSongs().add(song);
        }else{
            SongFeignDto songFeignDto = songService.findSongFeignById(songId);
            if(songFeignDto == null)
                throw new ResultNotFoundException("Song not found with id: "+songId);
            Song song = new Song(songFeignDto.id(), songFeignDto.songName(), songFeignDto.artistName(),
                    songFeignDto.views(), songFeignDto.albumUrlImg());
            songRepository.save(song);
            playlist.getSongs().add(song);
        }
        try {
            return playlistRepository.save(playlist);
        }catch (RuntimeException e){
            throw new SaveFailureException("Playlist could not be saved", playlist);
        }
    }

    @Override
    public Playlist removeSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(()-> new ResultNotFoundException("Playlist not found with id: "+playlistId));
        if(playlist.getSongs().stream().noneMatch(a -> a.getId().equals(songId)))
            throw new ResultNotFoundException("Song not found in playlist with id: "+songId);
        List<Song> songs = playlist.getSongs();
        songs.removeIf(song -> song.getId().equals(songId));
        playlist.setSongs(songs);
        try {
            return playlistRepository.save(playlist);
        }catch (RuntimeException e){
            throw new SaveFailureException("Playlist could not be saved", playlist);
        }
    }

    @Override
    public Playlist createPlaylist(String name, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token==null || !token.startsWith("Bearer"))
            throw new AuthenticationException("Invalid credentials");
        token = token.substring(7);
        Playlist playlist = new Playlist(null, name, jwtUtils.extractUsername(token), List.of());
        try{
            return playlistRepository.save(playlist);
        }catch (RuntimeException e){
            throw new SaveFailureException("Playlist could not be saved", playlist);
        }
    }

    @Override
    public Playlist findById(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(()-> new ResultNotFoundException("Playlist not found with id: "+id));
    }

    @Override
    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    @Override
    public List<Playlist> findByUsername(String username) {
        return playlistRepository.findByUsername(username);
    }

    @Override
    public Playlist updatePlaylist(PlaylistUpdateDto playlistUpdateDto, Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(()-> new ResultNotFoundException("Playlist not found with id: "+id));
        playlist.setName(playlistUpdateDto.name());
        try{
            return playlistRepository.save(playlist);
        }catch (RuntimeException e){
            throw new SaveFailureException("Playlist could not be saved", playlist);
        }
    }

    @Override
    public void deletePlaylist(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(()-> new ResultNotFoundException("Playlist not found with id: "+id));
        playlistRepository.deleteById(id);
    }

    @Override
    public List<String> findByUsernameFeign(String username) {
        return playlistRepository.findByUsername(username).stream().map(Playlist::getName).toList();
    }
}
