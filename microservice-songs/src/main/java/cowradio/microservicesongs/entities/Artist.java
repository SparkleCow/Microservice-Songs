package cowradio.microservicesongs.entities;

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
    private String description;
    @Column(name = "artist_url_img")
    private String artistUrlImg;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Genre> genres = new ArrayList<>();
    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Album> albums = new ArrayList<>();
}
