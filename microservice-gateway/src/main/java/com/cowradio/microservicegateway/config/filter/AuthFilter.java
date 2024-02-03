package com.cowradio.microservicegateway.config.filter;

import com.cowradio.microservicegateway.config.jwt.JwtUtils;
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
    private final RouterValidator routerValidator;
    private final JwtUtils jwtUtils;
    public AuthFilter(WebClient.Builder webClient, RouterValidator routerValidator, JwtUtils jwtUtils) {
        super(Config.class);
        this.webClient = webClient;
        this.routerValidator = routerValidator;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.out.println("Es usuario " +routerValidator.isUserSecure.test(exchange.getRequest()));
            System.out.println("Es abierto seguro? " +!routerValidator.isSecure.test(exchange.getRequest()));
            System.out.println("Es admin " +routerValidator.isAdminSecure.test(exchange.getRequest()));

            String path = exchange.getRequest().getPath().toString();
            if (!routerValidator.isSecure.test(exchange.getRequest())) {
                return chain.filter(exchange);
            }
            if(!exchange.getRequest().getHeaders().containsKey("Authorization")){
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            if(routerValidator.isUserSecure.test(exchange.getRequest())){
                String token = exchange.getRequest().getHeaders().get("Authorization").get(0);
                if(token == null || !token.startsWith("Bearer")){
                    return onError(exchange, HttpStatus.BAD_REQUEST);
                }
                System.out.println(jwtUtils.isUser(token.substring(7)));
                if(jwtUtils.isUser(token.substring(7))){
                    return webClient.build()
                            .post()
                            .uri("http://localhost:9090/api/v1/user/validate?token="+token.substring(7))
                            .retrieve()
                            .bodyToMono(TokenDto.class)
                            .map(tokenDto -> {
                                tokenDto.getToken();
                                return exchange;
                            }).flatMap(chain::filter);
                }
            }
            if(routerValidator.isAdminSecure.test(exchange.getRequest())){
                String token = exchange.getRequest().getHeaders().get("Authorization").get(0);
                if(token == null || !token.startsWith("Bearer")){
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }
                if(jwtUtils.isAdmin(token.substring(7))){
                    return webClient.build()
                            .post()
                            .uri("http://localhost:9090/api/v1/user/validate?token="+token.substring(7))
                            .retrieve()
                            .bodyToMono(TokenDto.class)
                            .map(tokenDto -> {
                                tokenDto.getToken();
                                return exchange;
                            }).flatMap(chain::filter);
                }
            }
            return onError(exchange, HttpStatus.UNAUTHORIZED);
        };
    }

    public Mono<Void> onError(ServerWebExchange exchange, HttpStatus status){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    public static class Config{}
}
