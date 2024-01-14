package cowradio.microservicesongs.entities;

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
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    private void incrementCount(){
        this.views++;
    }
}
