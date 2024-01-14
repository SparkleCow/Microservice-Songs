package cowradio.microservicesongs.services;

import cowradio.microservicesongs.entities.Artist;
import cowradio.microservicesongs.repositories.ArtistRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class ArtistServiceImp implements ArtistService{

    private final ArtistRepository artistRepository;
    @Override
    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    @Override
    public Artist findById(int id) {
        return null;
    }

    @Override
    public Artist findByArtistName(String artistName) {
        return null;
    }

    @Override
    public List<Artist> findAllArtist() {
        return null;
    }
}
