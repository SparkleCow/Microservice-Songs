package com.cowradio.microservicesecurity.client;

import jakarta.persistence.NoResultException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "msvc-playlist", url = "http://localhost:8070/api/v1/playlist")
public interface PlaylistClient {

    @GetMapping("/byUsername/{username}")
    List<String> findAllPlaylistByUsername(@PathVariable String username) throws NoResultException;
}
