package com.microserviceplaylist.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "songs")
@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String songName;
    private String artistName;
    private Long views;
    private String albumUrlImg;
    @ManyToMany(mappedBy = "songs")
    @JsonIgnore
    private List<Playlist> playlist = new ArrayList<>();

    public Song(Long id, String songName, String artistName, Long views, String albumUrlImg) {
        this.id = id;
        this.songName = songName;
        this.artistName = artistName;
        this.views = views;
        this.albumUrlImg = albumUrlImg;
    }
}

