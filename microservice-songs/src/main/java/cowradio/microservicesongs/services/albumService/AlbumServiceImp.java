package cowradio.microservicesongs.services.albumService;

import cowradio.microservicesongs.entities.Album;
import cowradio.microservicesongs.entities.artist.Artist;
import cowradio.microservicesongs.entities.dtos.AlbumRequestDto;
import cowradio.microservicesongs.repositories.AlbumRepository;
import cowradio.microservicesongs.repositories.ArtistRepository;
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
        Artist artist = artistRepository.findById(album.artistId()).orElseThrow(
                () -> new RuntimeException("Artist not found"));
        Album newAlbum = new Album(null, artist, album.albumName(), album.date(), album.albumUrlImg(), List.of());
        return albumRepository.save(newAlbum);
    }

    @Override
    public Album findById(Long id) {
        return albumRepository.findById(id).orElseThrow(() -> new RuntimeException("Album not found"));
    }

    @Override
    public List<Album> findByAlbumName(String albumName) {
        return albumRepository.findByAlbumName(albumName);
    }

    @Override
    public List<Album> findAllAlbums() {
        return null;
    }
}
