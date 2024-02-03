package cowradio.microservicesongs.controllers;

import cowradio.microservicesongs.entities.albums.Album;
import cowradio.microservicesongs.entities.albums.AlbumRequestDto;
import cowradio.microservicesongs.entities.albums.AlbumUpdateDto;
import cowradio.microservicesongs.exceptions.DuplicateElementException;
import cowradio.microservicesongs.exceptions.SaveFailureException;
import cowradio.microservicesongs.services.albumService.AlbumService;
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
@RequestMapping("/api/v1/album")
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping("/create")
    public ResponseEntity<Album> createAlbum(@RequestBody @Valid AlbumRequestDto albumRequestDto)
            throws URISyntaxException, SaveFailureException, NoResultException, DuplicateElementException {
        Album newAlbum = albumService.createAlbum(albumRequestDto);
        URI uri = new URI("/api/v1/album/" + newAlbum.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Album>> findAllAlbum(){
        return ResponseEntity.ok(albumService.findAllAlbums());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Album> findAlbumById(@PathVariable Long id){
        return ResponseEntity.ok(albumService.findById(id));
    }
    @GetMapping("/byName")
    public ResponseEntity<List<Album>> findAlbumByName(@RequestParam String name){
        return ResponseEntity.ok(albumService.findByAlbumName(name));
    }

    @GetMapping("/byArtistName")
    public ResponseEntity<List<Album>> findAlbumByArtistName(@RequestParam String name){
        return ResponseEntity.ok(albumService.findByArtist(name));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Album> updateAlbumById(@PathVariable Long id, @RequestBody AlbumUpdateDto albumUpdateDto)
            throws NoResultException, SaveFailureException {
        return ResponseEntity.ok(albumService.updateAlbum(id, albumUpdateDto));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Album> deleteAlbumById(@PathVariable Long id) throws NoResultException{
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
}
