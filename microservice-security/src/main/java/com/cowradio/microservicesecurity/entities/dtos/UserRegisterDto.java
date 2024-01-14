package com.cowradio.microservicesecurity.entities.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterDto(
        @NotBlank  String username, @NotBlank String email, @NotBlank String password
) {
}
