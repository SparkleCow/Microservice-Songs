package com.microserviceplaylist.repositories;

import com.microserviceplaylist.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
