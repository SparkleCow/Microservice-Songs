package cowradio.microservicesongs.services.albumService;

import cowradio.microservicesongs.entities.albums.Album;
import cowradio.microservicesongs.entities.artist.Artist;
import cowradio.microservicesongs.entities.albums.AlbumRequestDto;
import cowradio.microservicesongs.repositories.AlbumRepository;
import cowradio.microservicesongs.repositories.ArtistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

class AlbumServiceImpTest {

    @InjectMocks
    private AlbumServiceImp albumService;
    @Mock
    private AlbumRepository albumRepository;
    @Mock
    private ArtistRepository artistRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void create_album(){

        // Given
        AlbumRequestDto albumDto = new AlbumRequestDto(1L, new Date(), "AlbumName", "https://example.com/image.jpg");

        Artist artist = new Artist(1L, "ArtistName", "Description", "https://example.com/artist", List.of(), List.of());
        Album newAlbum = new Album(1L, artist, "AlbumName", new Date(), "https://example.com/image.jpg", List.of());

        // Mocks
        Mockito.when(artistRepository.findById(albumDto.artistId())).thenReturn(Optional.of(artist));
        Mockito.when(albumRepository.save(any(Album.class))).thenReturn(newAlbum);

        // When
        Album resultAlbum = albumService.createAlbum(albumDto);

        // Then
        Assertions.assertNotNull(resultAlbum);
        Assertions.assertEquals(newAlbum, resultAlbum);

        Mockito.verify(albumRepository, times(1)).save(any(Album.class));

    }

}