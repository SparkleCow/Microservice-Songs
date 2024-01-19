package cowradio.microservicesongs.repositories;

import cowradio.microservicesongs.entities.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    @Query(value = "SELECT * FROM artist WHERE LOWER(artist_name) LIKE LOWER(CONCAT('%', :search, '%'))", nativeQuery = true)
    List<Artist> findByName(@Param("search") String search);

    @Query(value = "SELECT * FROM artist AS a INNER JOIN artist_genres AS ag ON a.id = ag.artist_id WHERE LOWER(genres) LIKE LOWER(CONCAT('%',:genre,'%')) ", nativeQuery = true)
    List<Artist> findByGenre(@Param("genre") String genre);

    @Query(value = "SELECT * FROM artist WHERE LOWER(artist_name) = LOWER(:search) LIMIT 1", nativeQuery = true)
    Optional<Artist> findByArtistName(@Param("search") String name);
}
