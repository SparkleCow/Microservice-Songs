package cowradio.microservicesongs.controllers;

import cowradio.microservicesongs.entities.songs.Song;
import cowradio.microservicesongs.entities.songs.SongFeignDto;
import cowradio.microservicesongs.entities.songs.SongRequestDto;
import cowradio.microservicesongs.entities.songs.SongUpdateDto;
import cowradio.microservicesongs.exceptions.DuplicateElementException;
import cowradio.microservicesongs.exceptions.SaveFailureException;
import cowradio.microservicesongs.services.songService.SongService;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
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

    @PostMapping("/create")
    public ResponseEntity<Song> createSong(@RequestBody @Valid SongRequestDto songRequestDto) throws DuplicateElementException, NoResultException, URISyntaxException {
        Song song = songService.createSong(songRequestDto);
        URI uri = new URI("/api/v1/song/"+song.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Song>> findAllSongs(){
        return ResponseEntity.ok(songService.findAllSongs());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Song> findSongById(@PathVariable Long id) throws NoResultException{
        return ResponseEntity.ok(songService.findById(id));
    }

    @GetMapping("/feign/{id}")
    public SongFeignDto findSongFeignById(@PathVariable Long id) throws NoResultException{
        return songService.findByIdFeign(id);
    }
    @GetMapping("/bySongName")
    public ResponseEntity<List<Song>> findBySongName(@RequestParam String name){
        return ResponseEntity.ok(songService.findBySongName(name));
    }

    @GetMapping("/byGenre")
    public ResponseEntity<List<Song>> findByGenre(@RequestParam String genre){
        return ResponseEntity.ok(songService.findByGenre(genre));
    }

    @GetMapping("/byArtist")
    public ResponseEntity<List<Song>> findByArtist(@RequestParam String artist){
        return ResponseEntity.ok(songService.findByArtist(artist));
    }

    @GetMapping("/byAlbum")
    public ResponseEntity<List<Song>> findByAlbum(@RequestParam String album){
        return ResponseEntity.ok(songService.findByAlbum(album));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id, @RequestBody SongUpdateDto songUpdateDto) throws NoResultException, SaveFailureException {
        return ResponseEntity.ok(songService.updateSong(id, songUpdateDto));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) throws NoResultException{
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }

}


