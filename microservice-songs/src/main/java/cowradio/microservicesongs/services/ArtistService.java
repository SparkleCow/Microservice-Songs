package cowradio.microservicesongs.services;

import cowradio.microservicesongs.entities.Artist;

import java.util.List;

public interface ArtistService{
    Artist createArtist(Artist artist);
    Artist findById(int id);
    Artist findByArtistName(String artistName);
    List<Artist> findAllArtist();
}
