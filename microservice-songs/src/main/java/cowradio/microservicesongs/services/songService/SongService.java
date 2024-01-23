package cowradio.microservicesongs.services.songService;

import cowradio.microservicesongs.entities.songs.Song;
import cowradio.microservicesongs.entities.songs.SongFeignDto;
import cowradio.microservicesongs.entities.songs.SongRequestDto;
import cowradio.microservicesongs.entities.songs.SongUpdateDto;

import java.util.List;

public interface SongService {
    Song createSong(SongRequestDto songRequestDto);
    Song findById(Long id);
    SongFeignDto findByIdFeign(Long id);
    List<Song> findAllSongs();
    List<Song> findBySongName(String songName);
    List<Song> findByGenre(String genre);
    List<Song> findByArtist(String artist);
    List<Song> findByAlbum(String album);
    Song updateSong(Long id, SongUpdateDto songUpdateDto);
    void deleteSong(Long id);
}
