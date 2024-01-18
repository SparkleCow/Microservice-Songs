package cowradio.microservicesongs.entities.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record AlbumRequestDto(
        @NotNull Long artistId,
        @NotBlank Date date,
        @NotBlank String albumName,
        @NotBlank String albumUrlImg
) {
}
