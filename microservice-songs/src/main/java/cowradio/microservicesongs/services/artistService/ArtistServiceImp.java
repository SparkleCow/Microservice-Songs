package cowradio.microservicesongs.services.artistService;

import cowradio.microservicesongs.entities.artist.Artist;
import cowradio.microservicesongs.entities.artist.ArtistUpdateDto;
import cowradio.microservicesongs.exceptions.SaveFailureException;
import cowradio.microservicesongs.repositories.ArtistRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArtistServiceImp implements ArtistService {

    private final ArtistRepository artistRepository;
    @Override
    public Artist createArtist(Artist artist) {
        try{
            return artistRepository.save(artist);
        }catch(RuntimeException e){
            throw new SaveFailureException(e.getMessage(), artist);
        }
    }

    @Override
    public Artist findById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new NoResultException("Artist not found with id: "+id));
    }

    @Override
    public List<Artist> findByArtistName(String artistName) {
        return artistRepository.findByName(artistName);
    }

    @Override
    public List<Artist> findByGenre(String genre) {
        return artistRepository.findByGenre(genre);
    }

    @Override
    public List<Artist> findAllArtist() {
        return artistRepository.findAll();
    }

    @Override
    public Artist updateArtist(ArtistUpdateDto artistUpdateDto, Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new NoResultException("Artist not found with id: "+id));
        artist.update(artistUpdateDto);
        return artistRepository.save(artist);
    }

    @Override
    public void deleteArtist(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new NoResultException("Artist not found with id: "+id));
        artistRepository.deleteById(id);
    }
}
