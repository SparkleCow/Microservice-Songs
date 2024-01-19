package cowradio.microservicesongs.services.albumService;

import cowradio.microservicesongs.entities.albums.Album;
import cowradio.microservicesongs.entities.albums.AlbumRequestDto;
import cowradio.microservicesongs.entities.albums.AlbumUpdateDto;

import java.util.List;

public interface AlbumService {

    Album createAlbum(AlbumRequestDto album);
    Album findById(Long id);
    List<Album> findAllAlbums();
    List<Album> findByAlbumName(String albumName);
    List<Album> findByArtist(String artistName);
    Album updateAlbum(Long id, AlbumUpdateDto album);
    void deleteAlbum(Long id);
}
