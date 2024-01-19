package cowradio.microservicesongs.entities.albums;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record AlbumRequestDto(
        @NotNull String artistName,
        @NotNull Date date,
        @NotBlank String albumName,
        @NotBlank String albumUrlImg
) {
}
