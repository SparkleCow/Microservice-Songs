package com.cowradio.microservicesecurity.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "msvc-playlist", url = "http://localhost:8070/api/v1/playlist")
public interface PlaylistClient {

    @GetMapping
    public String findAllPlaylistByUsername(String username);
}
