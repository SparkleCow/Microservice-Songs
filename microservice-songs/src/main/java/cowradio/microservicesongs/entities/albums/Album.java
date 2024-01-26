package cowradio.microservicesongs.entities.albums;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cowradio.microservicesongs.entities.songs.Song;
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
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
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

    public void update(AlbumUpdateDto albumUpdateDto){
        if(albumUpdateDto.albumName()!=null && !albumUpdateDto.albumName().equals("")){
            this.albumName = albumUpdateDto.albumName();
        }
        if(albumUpdateDto.albumUrlImg()!=null && !albumUpdateDto.albumUrlImg().equals("")){
            this.albumUrlImg = albumUpdateDto.albumUrlImg();
        }
        if(albumUpdateDto.date()!=null){
            this.date = albumUpdateDto.date();
        }
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", albumName='" + albumName + '\'' +
                ", date=" + date +
                ", albumUrlImg='" + albumUrlImg + '\'' +
                ", songs=" + songs +
                '}';
    }
}
