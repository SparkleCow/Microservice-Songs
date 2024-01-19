package cowradio.microservicesongs.entities.artist;

import cowradio.microservicesongs.entities.Genre;
import cowradio.microservicesongs.entities.albums.Album;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "artist")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "artist_name", unique = true)
    private String artistName;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "artist_url_img")
    private String artistUrlImg;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Genre> genres = new ArrayList<>();
    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Album> albums = new ArrayList<>();

    public void update(ArtistUpdateDto artistUpdateDto){
        if(artistUpdateDto.name() != null && !artistUpdateDto.name().equals("")){
            this.artistName = artistUpdateDto.name();
        }
        if(artistUpdateDto.description() != null && !artistUpdateDto.description().equals("")){
            this.description = artistUpdateDto.description();
        }
        if(artistUpdateDto.artistUrlImg() != null && !artistUpdateDto.artistUrlImg().equals("")){
            this.artistUrlImg = artistUpdateDto.artistUrlImg();
        }
        if(!genres.isEmpty()){
            this.genres = artistUpdateDto.genres();
        }
    }
}
