package com.cowradio.microservicegateway.config.filter;

import com.cowradio.microservicegateway.dtos.TokenDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {


    private final WebClient.Builder webClient;

    public AuthFilter(WebClient.Builder webClient) {
        super(Config.class);
        this.webClient = webClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if(!exchange.getRequest().getHeaders().containsKey("Authorization")){
                return onError(exchange, HttpStatus.NOT_ACCEPTABLE);
            }
            String token = exchange.getRequest().getHeaders().get("Authorization").get(0);
            if(token == null || !token.startsWith("Bearer")){
                return onError(exchange, HttpStatus.BAD_REQUEST);
            }
            return webClient.build()
                    .post()
                    .uri("http://localhost:9090/api/v1/user/validate?token="+token.substring(7))
                    .retrieve()
                    .bodyToMono(TokenDto.class)
                    .map(tokenDto -> {
                        tokenDto.getToken();
                        return exchange;
                    }).flatMap(chain::filter);
        };
    }

    public Mono<Void> onError(ServerWebExchange exchange, HttpStatus status){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    public static class Config{}
}
