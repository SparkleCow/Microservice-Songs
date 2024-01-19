package cowradio.microservicesongs.entities.songs;

import cowradio.microservicesongs.entities.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SongRequestDto(
        @NotBlank String songName,
        @NotBlank String albumName,
        @NotBlank String artistName,
        @NotNull List<Genre> genres
        ) {
}
