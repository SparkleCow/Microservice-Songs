package cowradio.microservicesongs.services.artistService;

import cowradio.microservicesongs.entities.artist.Artist;
import cowradio.microservicesongs.entities.artist.ArtistRequestDto;
import cowradio.microservicesongs.entities.artist.ArtistUpdateDto;
import cowradio.microservicesongs.exceptions.DuplicateElementException;
import cowradio.microservicesongs.exceptions.ResultNotFoundException;
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
    public Artist createArtist(ArtistRequestDto artistRequestDto) {
        Artist artist = new Artist(null, artistRequestDto.artistName(), artistRequestDto.description(),
                artistRequestDto.artistUrlImg(), artistRequestDto.genres(), List.of());

        if (artistRepository.findByArtistName(artist.getArtistName()).isPresent()) {
            System.out.print("Hola señor error");
            throw new DuplicateElementException("Artist already exists ", artist);
        }
        try {
            return artistRepository.save(artist);
        } catch (RuntimeException e) {
            throw new SaveFailureException("Error, artist could not be saved ", artist);
        }
    }

    @Override
    public Artist findById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResultNotFoundException("Artist not found with id: "+id));
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
                .orElseThrow(() -> new ResultNotFoundException("Artist not found with id: "+id));
        try{
            artist.update(artistUpdateDto);
            return artistRepository.save(artist);
        }catch (RuntimeException e){
            throw new SaveFailureException("Error, artist could not be updated ", artist);
        }
    }

    @Override
    public void deleteArtist(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResultNotFoundException("Artist not found with id: "+id));
        artistRepository.deleteById(id);
    }
}
