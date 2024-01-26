package cowradio.microservicesongs.repositories;

import cowradio.microservicesongs.entities.albums.Album;
import cowradio.microservicesongs.entities.artist.Artist;
import cowradio.microservicesongs.entities.songs.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByAlbum(Album album);

    @Query(value = "SELECT * FROM songs WHERE LOWER(song_name) LIKE LOWER(CONCAT('%', :name, '%'))", nativeQuery = true)
    List<Song> findBySongName(@Param("name") String name);

    @Query(value = "SELECT * FROM songs AS s INNER JOIN song_genres AS sg ON s.id = sg.song_id WHERE LOWER(genres) LIKE LOWER(CONCAT('%', :genre, '%'))", nativeQuery = true)
    List<Song> findByGenre(@Param("genre") String genre);

    @Query(value = "SELECT * FROM songs WHERE LOWER(artist_name) LIKE LOWER(CONCAT('%', :artist, '%'))", nativeQuery = true)
    List<Song> findByArtist(@Param("artist") String artist);

}
