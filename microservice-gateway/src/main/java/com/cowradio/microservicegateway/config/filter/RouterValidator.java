package com.cowradio.microservicegateway.config.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class RouterValidator {

    public List<Pattern> openEndpointPatterns = List.of(
            Pattern.compile("/api/v1/user/register"),
            Pattern.compile("/api/v1/user/login"),
            Pattern.compile("/api/v1/artist/findAll"),
            Pattern.compile("/api/v1/artist/byGenre"),
            Pattern.compile("/api/v1/artist/byName"),
            Pattern.compile("/api/v1/album/findAll"),
            Pattern.compile("/api/v1/album/byArtistName"),
            Pattern.compile("/api/v1/album/byName"),
            Pattern.compile("/api/v1/song/findAll"),
            Pattern.compile("/api/v1/song/byGenre"),
            Pattern.compile("/api/v1/song/byAlbum"),
            Pattern.compile("/api/v1/song/byArtist"),
            Pattern.compile("/api/v1/song/bySongName"),
            Pattern.compile("/api/v1/user/validate"));

    public List<Pattern> userEndpointPatterns = List.of(
            Pattern.compile("/api/v1/playlist/create"),
            Pattern.compile("/api/v1/playlist/byUsername/feign/[a-zA-Z0-9]+"),
            Pattern.compile("/api/v1/playlist/update/\\d+"),
            Pattern.compile("/api/v1/playlist/deleteById/\\d+"),
            Pattern.compile("/api/v1/playlist/removeSong"),
            Pattern.compile("/api/v1/playlist/addSong"));

    public List<Pattern> adminEndpointPatterns = List.of(
            Pattern.compile("/api/v1/playlist/create"),
            Pattern.compile("/api/v1/playlist/byUsername/[a-zA-Z0-9]+"),
            Pattern.compile("/api/v1/playlist/findAll"),
            Pattern.compile("/api/v1/playlist/addSong"),
            Pattern.compile("/api/v1/playlist/\\d+"),
            Pattern.compile("/api/v1/playlist/update/\\d+"),
            Pattern.compile("/api/v1/playlist/byUsername/feign/[a-zA-Z0-9]+"),
            Pattern.compile("/api/v1/playlist/deleteById/\\d+"),
            Pattern.compile("/api/v1/playlist/removeSong"),
            Pattern.compile("/api/v1/user/findAll"),
            Pattern.compile("/api/v1/user/findById/\\d+"),
            Pattern.compile("/api/v1/user/findByUsername/[a-zA-Z0-9]+"),
            Pattern.compile("/api/v1/artist/findById/\\d+"),
            Pattern.compile("/api/v1/artist/create"),
            Pattern.compile("/api/v1/artist/deleteById/\\d+"),
            Pattern.compile("/api/v1/artist/update/\\d+"),
            Pattern.compile("/api/v1/album/create"),
            Pattern.compile("/api/v1/album/findById/\\d+"),
            Pattern.compile("/api/v1/album/update/\\d+"),
            Pattern.compile("/api/v1/album/deleteById/\\d+"),
            Pattern.compile("/api/v1/song/create"),
            Pattern.compile("/api/v1/song/findById/\\d+"),
            Pattern.compile("/api/v1/song/update/\\d+"),
            Pattern.compile("/api/v1/song/deleteById/\\d+"),
            Pattern.compile("/api/v1/user/deleteById/\\d+"));


    public Predicate<ServerHttpRequest> isSecure =
        request -> openEndpointPatterns.stream()
                .noneMatch(pattern -> pattern.matcher(request.getURI().getPath()).matches());

    public Predicate<ServerHttpRequest> isUserSecure =
            request -> userEndpointPatterns.stream()
                    .anyMatch(pattern -> pattern.matcher(request.getURI().getPath()).matches());

    public Predicate<ServerHttpRequest> isAdminSecure =
            request -> adminEndpointPatterns.stream()
                    .anyMatch(pattern -> pattern.matcher(request.getURI().getPath()).matches());
}
