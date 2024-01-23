package cowradio.microservicesongs.entities.songs;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

public record SongFeignDto(
                         @NotBlank Long id,
                         @NotBlank String songName,
                         @NotBlank String artistName,
                         @NotBlank Long views,
                         @NotBlank String albumUrlImg) {
}
