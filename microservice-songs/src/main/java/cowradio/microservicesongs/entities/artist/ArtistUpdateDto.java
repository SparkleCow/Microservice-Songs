package cowradio.microservicesongs.entities.artist;

import cowradio.microservicesongs.entities.Genre;

import java.util.List;
public record ArtistUpdateDto(
        String name,
        String description,
        String artistUrlImg,
        List<Genre> genres
) {
}