package com.cowradio.microservicesecurity.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    //Implementación de la interfaz UserDetailsService para buscar un usuario por medio de su username.
    private final UserDetailsService userDetailsService;
    //Servicio con métodos para la manipulación de Jason Web Tokens
    private final JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token==null || !token.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        token = token.substring(7);
        String username = jwtUtils.extractUsername(token);
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if(user!=null && jwtUtils.validateUser(user, token)){
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user,
                    null,
                    user.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);
        return;
    }
}
