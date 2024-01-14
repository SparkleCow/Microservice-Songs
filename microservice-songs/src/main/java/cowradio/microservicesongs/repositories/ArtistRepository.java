package cowradio.microservicesongs.repositories;

import cowradio.microservicesongs.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    @Query(value = "SELECT * FROM artist WHERE LOWER(artist_name) LIKE LOWER(CONCAT('%', :search, '%'))", nativeQuery = true)
    List<Artist> findByArtistName(@Param("search") String search);
}
