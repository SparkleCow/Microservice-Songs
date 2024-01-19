package cowradio.microservicesongs.repositories;

import cowradio.microservicesongs.entities.albums.Album;
import cowradio.microservicesongs.entities.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtist(Artist artist);

    @Query(value="SELECT * FROM albums WHERE LOWER(album_name) LIKE LOWER(CONCAT('%', :albumName, '%'))", nativeQuery = true)
    List<Album> findByAlbumName(String albumName);
}
