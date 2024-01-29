package com.microserviceplaylist.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "msvc-auth", url = "http://localhost:9090/api/v1/user" )
public interface UserClient {

    @PostMapping("/addPlaylist/feign")
    public void addPlaylistInformation(@RequestParam String username, @RequestParam String playlistName);

    @PostMapping("/removePlaylist/feign")
    public void removePlaylistInformation(@RequestParam String username, @RequestParam String playlistName);

    @PostMapping("/updatePlaylist/feign")
    public void updatePlaylistInformation(@RequestParam String username,
                                          @RequestParam String playlistName,
                                          @RequestParam String newPlaylistName);

}
