package cowradio.microservicesongs.controllers;

import cowradio.microservicesongs.entities.Song;
import cowradio.microservicesongs.entities.dtos.SongRequestDto;
import cowradio.microservicesongs.services.songService.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/song")
public class SongController {

    private final SongService songService;

    @PostMapping
    public ResponseEntity<Song> createSong(@RequestBody SongRequestDto songRequestDto) throws URISyntaxException {
        Song song = songService.createSong(songRequestDto);
        URI uri = new URI("/api/v1/song/"+song.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<Song>> findAllSongs(){
        return ResponseEntity.ok(songService.findAllSongs());
    }

    @GetMapping("/")
    public ResponseEntity<Song> findSongByName(@RequestParam String songName){
        return ResponseEntity.ok(songService.findBySongName(songName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> findSongById(@PathVariable Long id){
        return ResponseEntity.ok(songService.findById(id));
    }
}
