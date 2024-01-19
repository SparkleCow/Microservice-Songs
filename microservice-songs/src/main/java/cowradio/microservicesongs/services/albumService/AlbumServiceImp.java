package cowradio.microservicesongs.services.albumService;

import cowradio.microservicesongs.entities.albums.Album;
import cowradio.microservicesongs.entities.albums.AlbumUpdateDto;
import cowradio.microservicesongs.entities.artist.Artist;
import cowradio.microservicesongs.entities.albums.AlbumRequestDto;
import cowradio.microservicesongs.exceptions.DuplicateAlbumException;
import cowradio.microservicesongs.exceptions.SaveFailureException;
import cowradio.microservicesongs.repositories.AlbumRepository;
import cowradio.microservicesongs.repositories.ArtistRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumServiceImp implements AlbumService{

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    @Override
    public Album createAlbum(AlbumRequestDto album) {
        try {
            Artist artist = artistRepository.findByArtistName(album.artistName()).orElseThrow(
                () -> new NoResultException("Artist not found with name: "+album.artistName()));
            if(artist.getAlbums().stream().anyMatch(
                    a -> a.getAlbumName().equalsIgnoreCase(album.albumName()))){
                throw new DuplicateAlbumException("Artist " + artist.getArtistName() + " already has an album with name: " + album.albumName(), "Error 400 bad request");
            }
            Album newAlbum = new Album(null, artist, album.albumName(), album.date(), album.albumUrlImg(), List.of());
            return albumRepository.save(newAlbum);
        }catch(RuntimeException e){
            throw new SaveFailureException(e.getMessage(), album);
        }
    }

    @Override
    public Album findById(Long id) {
        return albumRepository.findById(id).orElseThrow(() -> new NoResultException("Album not found with id: "+id));
    }

    @Override
    public List<Album> findAllAlbums() {
        return albumRepository.findAll();
    }

    @Override
    public List<Album> findByAlbumName(String albumName) {
        return albumRepository.findByAlbumName(albumName);
    }

    @Override
    public List<Album> findByArtist(String artistName) {
        Artist artist = artistRepository.findByArtistName(artistName).orElseThrow(
                () -> new NoResultException("Artist not found with name: "+artistName));
        return albumRepository.findByArtist(artist);
    }

    @Override
    public Album updateAlbum(Long id, AlbumUpdateDto albumUpdateDto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new NoResultException("Album not found with id: "+id));
        album.update(albumUpdateDto);
        return albumRepository.save(album);
    }

    @Override
    public void deleteAlbum(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new NoResultException("Album not found with id: "+id));
        albumRepository.delete(album);
    }
}
