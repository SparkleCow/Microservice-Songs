package cowradio.microservicesongs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cowradio.microservicesongs.entities.artist.Artist;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "artist_id")
    private Artist artist;
    @Column(name = "album_name")
    private String albumName;
    private Date date;
    @Column(name = "album_url_img")
    private String albumUrlImg;
    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private List<Song> songs = new ArrayList<>();
}
