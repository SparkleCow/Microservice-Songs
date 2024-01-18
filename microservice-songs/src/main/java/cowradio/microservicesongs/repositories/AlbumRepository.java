package cowradio.microservicesongs.repositories;

import cowradio.microservicesongs.entities.Album;
import cowradio.microservicesongs.entities.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtist(Artist artist);

    List<Album> findByAlbumName(String albumName);
}
