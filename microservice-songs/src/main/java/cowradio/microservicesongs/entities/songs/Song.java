package cowradio.microservicesongs.entities.songs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cowradio.microservicesongs.entities.albums.Album;
import cowradio.microservicesongs.entities.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode @ToString
@Table(name = "songs")
public class Song{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "song_name")
    private String songName;
    @Column(name = "artist_name")
    private String artistName;
    private Long views = 0L;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Genre> genres = new ArrayList<>();

    public Song(String songName, String artistName, Album album, List<Genre> genres) {
        this.songName = songName;
        this.artistName = artistName;
        this.album = album;
        this.genres = genres;
    }

    public void incrementCount(){
        this.views++;
    }

    public void update(SongUpdateDto songUpdateDto){
        if(songUpdateDto.songName() != null && !songUpdateDto.songName().equalsIgnoreCase("")){
            this.songName = songUpdateDto.songName();
        }
        if(songUpdateDto.artistName() != null && !songUpdateDto.artistName().equalsIgnoreCase("")){
            this.artistName = songUpdateDto.artistName();
        }
        if (songUpdateDto.genres() != null){
            this.genres = songUpdateDto.genres();
        }
    }
}
