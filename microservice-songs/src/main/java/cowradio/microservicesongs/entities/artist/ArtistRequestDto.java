package cowradio.microservicesongs.entities.artist;

import cowradio.microservicesongs.entities.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ArtistRequestDto(
        @NotBlank String artistName,
        @NotBlank String description,
        @NotBlank String artistUrlImg,
        @NotEmpty List<Genre> genres){
}
