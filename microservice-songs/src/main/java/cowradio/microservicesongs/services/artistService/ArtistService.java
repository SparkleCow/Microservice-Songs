package cowradio.microservicesongs.services.artistService;

import cowradio.microservicesongs.entities.artist.Artist;
import cowradio.microservicesongs.entities.artist.ArtistRequestDto;
import cowradio.microservicesongs.entities.artist.ArtistUpdateDto;

import java.util.List;

public interface ArtistService{
    Artist createArtist(ArtistRequestDto artistRequestDto);
    Artist findById(Long id);
    List<Artist> findByArtistName(String artistName);
    List<Artist> findByGenre(String genre);
    List<Artist> findAllArtist();
    Artist updateArtist(ArtistUpdateDto artistUpdateDto, Long id);
    void deleteArtist(Long id);
}
