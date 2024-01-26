package cowradio.microservicesongs.services.songService;

import cowradio.microservicesongs.entities.albums.Album;
import cowradio.microservicesongs.entities.artist.Artist;
import cowradio.microservicesongs.entities.songs.Song;
import cowradio.microservicesongs.entities.songs.SongFeignDto;
import cowradio.microservicesongs.entities.songs.SongRequestDto;
import cowradio.microservicesongs.entities.songs.SongUpdateDto;
import cowradio.microservicesongs.exceptions.DuplicateElementException;
import cowradio.microservicesongs.exceptions.ResultNotFoundException;
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
        Artist artist = artistRepository.findByArtistName(songRequestDto.artistName())
                .orElseThrow(() -> new ResultNotFoundException("Artist not found with name: "+songRequestDto.artistName()));
        Album album = artist.getAlbums().stream().filter(a -> a.getAlbumName()
                        .equalsIgnoreCase(songRequestDto.albumName())).findFirst()
                .orElseThrow(() -> new ResultNotFoundException("Album not found with artist name: "+songRequestDto.artistName()+" and album name: "+songRequestDto.albumName()));
        if(album.getSongs().stream().anyMatch(song -> song.getSongName().equalsIgnoreCase(songRequestDto.songName()))){
            throw new DuplicateElementException("Song already exists with name: "+songRequestDto.songName()+" in album: "+album.getAlbumName()+" ", songRequestDto);
        }
        try{
            Song song = new Song(songRequestDto.songName(), artist.getArtistName(), album.getAlbumUrlImg(), album, songRequestDto.genres());
            return songRepository.save(song);
        }catch(SaveFailureException e){
            throw new SaveFailureException(e.getMessage(), songRequestDto);
        }
    }

    @Override
    public Song findById(Long id) {
        return songRepository.findById(id).orElseThrow(() -> new ResultNotFoundException("Song not found with id: "+id));
    }

    @Override
    public SongFeignDto findByIdFeign(Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new ResultNotFoundException("Song not found with id: "+id));
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
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResultNotFoundException("Song not found with id: "+id));
        try{
            song.update(songUpdateDto);
            return songRepository.save(song);
        }catch(SaveFailureException e){
            throw new SaveFailureException("Error, song could not be updated", songUpdateDto);
        }
    }

    @Override
    public void deleteSong(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResultNotFoundException("Song not found with id: "+id));
        songRepository.deleteById(id);
    }

}


