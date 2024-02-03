package cowradio.microservicesongs.controllers;

import cowradio.microservicesongs.entities.artist.Artist;
import cowradio.microservicesongs.entities.artist.ArtistRequestDto;
import cowradio.microservicesongs.entities.artist.ArtistUpdateDto;
import cowradio.microservicesongs.exceptions.DuplicateElementException;
import cowradio.microservicesongs.exceptions.ResultNotFoundException;
import cowradio.microservicesongs.exceptions.SaveFailureException;
import cowradio.microservicesongs.services.artistService.ArtistService;
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
@RequestMapping("/api/v1/artist")
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping("/create")
    public ResponseEntity<Artist> createArtist(@RequestBody @Valid ArtistRequestDto artistRequestDto)
                                               throws URISyntaxException, SaveFailureException, DuplicateElementException {
        Artist artist = artistService.createArtist(artistRequestDto);
        URI uri = new URI("/api/v1/artist/"+artist.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Artist> updateArtist(@RequestBody ArtistUpdateDto artistUpdateDto,
                                               @PathVariable Long id) throws ResultNotFoundException {
        return ResponseEntity.ok(artistService.updateArtist(artistUpdateDto, id));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Artist> findArtistById(@PathVariable Long id) throws ResultNotFoundException{
        Artist artist = artistService.findById(id);
        return ResponseEntity.ok(artist);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Artist>> findAllArtist(){
        return ResponseEntity.ok(artistService.findAllArtist());
    }

    @GetMapping("/byName")
    public ResponseEntity<List<Artist>> findArtistByName(@RequestParam String name){
        return ResponseEntity.ok(artistService.findByArtistName(name));
    }

    @GetMapping("/byGenre")
    public ResponseEntity<List<Artist>> findArtistByGenre(@RequestParam String genre){
        return ResponseEntity.ok(artistService.findByGenre(genre));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Artist> deleteArtistById(@PathVariable Long id) throws ResultNotFoundException{
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
