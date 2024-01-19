package cowradio.microservicesongs.controllers;

import cowradio.microservicesongs.entities.albums.Album;
import cowradio.microservicesongs.entities.albums.AlbumRequestDto;
import cowradio.microservicesongs.entities.albums.AlbumUpdateDto;
import cowradio.microservicesongs.exceptions.DuplicateAlbumException;
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

    @PostMapping
    public ResponseEntity<Album> createAlbum(@RequestBody @Valid AlbumRequestDto albumRequestDto)
            throws URISyntaxException, SaveFailureException, NoResultException, DuplicateAlbumException {
        Album newAlbum = albumService.createAlbum(albumRequestDto);
        URI uri = new URI("/api/v1/album/"+newAlbum.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> findAlbumById(@PathVariable Long id) throws NoResultException{
        return ResponseEntity.ok(albumService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Album>> findAllAlbum(){
        return ResponseEntity.ok(albumService.findAllAlbums());
    }

    @GetMapping("/byName")
    public ResponseEntity<List<Album>> findAlbumByName(@RequestParam String name){
        return ResponseEntity.ok(albumService.findByAlbumName(name));
    }

    @GetMapping("/byArtistName")
    public ResponseEntity<List<Album>> findAlbumByArtistName(@RequestParam String name){
        return ResponseEntity.ok(albumService.findByArtist(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbumById(@PathVariable Long id, @RequestBody AlbumUpdateDto albumUpdateDto)
            throws NoResultException, SaveFailureException {
        return ResponseEntity.ok(albumService.updateAlbum(id, albumUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Album> deleteAlbumById(@PathVariable Long id) throws NoResultException{
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
}
