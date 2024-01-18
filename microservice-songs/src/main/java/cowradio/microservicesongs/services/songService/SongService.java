package cowradio.microservicesongs.services.songService;

import cowradio.microservicesongs.entities.Song;
import cowradio.microservicesongs.entities.dtos.SongRequestDto;

import java.util.List;

public interface SongService {
    Song createSong(SongRequestDto songRequestDto);
    Song findById(Long id);
    Song findBySongName(String songName);
    List<Song> findAllSongs();

}
