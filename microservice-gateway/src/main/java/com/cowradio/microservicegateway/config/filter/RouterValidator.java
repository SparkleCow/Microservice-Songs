package com.cowradio.microservicegateway.config.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouterValidator {

    public List<String> openEndpoints = List.of(
            "/api/v1/user/register",
            "/api/v1/user/login",
            "/api/v1/user/validate");

    public List<String> userEndpoints = List.of(
            "/api/v1/user/register",
            "/api/v1/user/login",
            "/api/v1/user/validate");

    public List<String> adminEndpoints = List.of(
            "/api/v1/user/findAll",
            "/api/v1/user/findById/{id}",
            "/api/v1/user/findByUsername/{username}",
            "/api/v1/user/deleteById/{id}");

    public Predicate<ServerHttpRequest> isSecure =
        request -> openEndpoints.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isUserSecure =
            request -> userEndpoints.stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isAdminSecure =
            request -> adminEndpoints.stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));
}
