package cowradio.microservicesongs.entities.songs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cowradio.microservicesongs.entities.Genre;
import cowradio.microservicesongs.entities.albums.Album;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public record SongUpdateDto(
        String songName,
        String artistName,
        List<Genre> genres
) {
}