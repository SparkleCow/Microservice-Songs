package cowradio.microservicesongs.services.albumService;

import cowradio.microservicesongs.entities.Album;
import cowradio.microservicesongs.entities.dtos.AlbumRequestDto;

import java.util.List;

public interface AlbumService {

    Album createAlbum(AlbumRequestDto album);
    List<Album> findAllAlbums();
    Album findById(Long id);
    List<Album> findByAlbumName(String albumName);
}
