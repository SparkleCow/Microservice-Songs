package cowradio.microservicesongs.services.songService;

import cowradio.microservicesongs.entities.albums.Album;
import cowradio.microservicesongs.entities.artist.Artist;
import cowradio.microservicesongs.entities.songs.Song;
import cowradio.microservicesongs.entities.songs.SongFeignDto;
import cowradio.microservicesongs.entities.songs.SongRequestDto;
import cowradio.microservicesongs.entities.songs.SongUpdateDto;
import cowradio.microservicesongs.exceptions.SaveFailureException;
import cowradio.microservicesongs.repositories.AlbumRepository;
import cowradio.microservicesongs.repositories.ArtistRepository;
import cowradio.microservicesongs.repositories.SongRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImp implements SongService {

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    @Override
    public Song createSong(SongRequestDto songRequestDto) {
        try{
            Artist artist = artistRepository.findByArtistName(songRequestDto.artistName())
                    .orElseThrow(() -> new NoResultException("Artist not found with name: "+songRequestDto.artistName()));
            if(artist.getAlbums().stream().anyMatch(album -> album.getAlbumName().equalsIgnoreCase(songRequestDto.albumName()))){
                Album album = artist.getAlbums().stream().filter(a -> a.getAlbumName()
                                .equalsIgnoreCase(songRequestDto.albumName())).findFirst()
                        .orElseThrow(() -> new NoResultException("Album not found with name: "+songRequestDto.albumName()));

                Song song = new Song(songRequestDto.songName(), artist.getArtistName(), album, songRequestDto.genres());
                return songRepository.save(song);
            }
            throw new NoResultException("Album not found with artist name: "+songRequestDto.artistName()+" and album name: "+songRequestDto.albumName());
        }catch(SaveFailureException e){
            throw new SaveFailureException(e.getMessage(), songRequestDto);
        }
    }

    @Override
    public Song findById(Long id) {
        return songRepository.findById(id).orElseThrow(() -> new NoResultException("Song not found with id: "+id));
    }

    @Override
    public SongFeignDto findByIdFeign(Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new NoResultException("Song not found with id: "+id));
        return new SongFeignDto(song.getId(),
                song.getSongName(),
                song.getArtistName(),
                song.getViews(),
                song.getAlbum().getAlbumUrlImg());
    }

    @Override
    public List<Song> findAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public List<Song> findBySongName(String songName) {
        return songRepository.findBySongName(songName);
    }

    @Override
    public List<Song> findByGenre(String genre) {
        return songRepository.findByGenre(genre);
    }

    @Override
    public List<Song> findByArtist(String artist) {
        return songRepository.findByArtist(artist);
    }

    @Override
    public List<Song> findByAlbum(String album) {
        List<Album> albums = albumRepository.findByAlbumName(album);
        return albums.stream().flatMap(x -> x.getSongs().stream()).toList();
    }

    @Override
    public Song updateSong(Long id, SongUpdateDto songUpdateDto) {
        try{
            Song song = songRepository.findById(id)
                    .orElseThrow(() -> new NoResultException("Song not found with id: "+id));
            song.update(songUpdateDto);
            return songRepository.save(song);
        }catch(SaveFailureException e){
            throw new SaveFailureException(e.getMessage(), songUpdateDto);
        }
    }

    @Override
    public void deleteSong(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new NoResultException("Song not found with id: "+id));
        songRepository.deleteById(id);
    }

}


