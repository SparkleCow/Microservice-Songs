package cowradio.microservicesongs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode @ToString
@Table(name = "songs")
public class Song{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long views = 0L;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    public Song(Long id, String name, Album album) {
        this.id = id;
        this.name = name;
        this.album = album;
    }

    private void incrementCount(){
        this.views++;
    }
}
