package cowradio.microservicesongs.repositories;

import cowradio.microservicesongs.entities.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    @Query(value = "SELECT * FROM artist WHERE LOWER(artist_name) LIKE LOWER(CONCAT('%', :search, '%'))", nativeQuery = true)
    List<Artist> findByArtistName(@Param("search") String search);

    @Query(value = "SELECT * FROM artist AS a INNER JOIN artist_genres AS ag ON a.id = ag.artist_id WHERE genres = :genre", nativeQuery = true)
    List<Artist> findByGenre(@Param("genre") String genre);
}
