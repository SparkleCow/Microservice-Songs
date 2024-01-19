package cowradio.microservicesongs.entities.albums;

import java.util.Date;

public record AlbumUpdateDto(
        String albumName,
        Date date,
        String albumUrlImg
) {
}