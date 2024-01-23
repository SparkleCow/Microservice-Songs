package com.microserviceplaylist.entities;

public record SongFeignDto(
        Long id,
        String songName,
        String artistName,
        Long views,
        String albumUrlImg) {
}