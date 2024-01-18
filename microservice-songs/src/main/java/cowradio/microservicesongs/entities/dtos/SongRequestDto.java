package cowradio.microservicesongs.entities.dtos;

import jakarta.validation.constraints.NotBlank;

public record SongRequestDto(
        @NotBlank String songName,
        @NotBlank Long albumId
) {
}
