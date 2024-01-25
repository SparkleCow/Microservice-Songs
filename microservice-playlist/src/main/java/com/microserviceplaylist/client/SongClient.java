package com.microserviceplaylist.client;

import com.microserviceplaylist.entities.Song;
import com.microserviceplaylist.entities.SongFeignDto;
import jakarta.persistence.NoResultException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-song", url = "http://localhost:8090/api/v1/song")
public interface SongClient{

    @GetMapping("/feign/{id}")
    SongFeignDto findSongFeignById(@PathVariable Long id) throws NoResultException;
}
