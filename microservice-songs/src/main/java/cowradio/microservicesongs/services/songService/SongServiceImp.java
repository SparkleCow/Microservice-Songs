package cowradio.microservicesongs.services.songService;

import cowradio.microservicesongs.entities.Album;
import cowradio.microservicesongs.entities.Song;
import cowradio.microservicesongs.entities.dtos.SongRequestDto;
import cowradio.microservicesongs.repositories.AlbumRepository;
import cowradio.microservicesongs.repositories.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImp implements SongService {

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;

    @Override
    public Song createSong(SongRequestDto songRequestDto) {
        Album album = albumRepository.findById(songRequestDto.albumId())
                .orElseThrow(() -> new RuntimeException("Album not found"));
        Song song = new Song(null, songRequestDto.songName(), album);
        return songRepository.save(song);
    }

    @Override
    public Song findBySongName(String songName) {
        return songRepository.findByName(songName)
                        .orElseThrow(() -> new RuntimeException("Song not found"));
    }

    @Override
    public Song findById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));
    }

    @Override
    public List<Song> findAllSongs() {
        return songRepository.findAll();
    }
}
