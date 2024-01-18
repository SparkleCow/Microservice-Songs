package cowradio.microservicesongs.controllers;

import cowradio.microservicesongs.entities.Album;
import cowradio.microservicesongs.entities.dtos.AlbumRequestDto;
import cowradio.microservicesongs.services.albumService.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/album")
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping
    public ResponseEntity<Album> createAlbum(@RequestBody AlbumRequestDto albumRequestDto) throws URISyntaxException {
        Album newAlbum = albumService.createAlbum(albumRequestDto);
        URI uri = new URI("/api/v1/album/"+newAlbum.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> findAlbumById(@PathVariable Long id){
        Album album = albumService.findById(id);
        return ResponseEntity.ok(album);
    }
}
