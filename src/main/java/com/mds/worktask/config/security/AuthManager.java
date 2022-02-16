package com.mds.worktask.config.security;

import com.mds.worktask.config.jwt.JwtService;
import io.netty.handler.codec.spdy.SpdyHttpResponseStreamIdHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthManager implements ReactiveAuthenticationManager {

    @Autowired
    JwtService jwtService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        jwtService.validateToken(authentication.getCredentials().toString(), (AuthenticationJwt) authentication);
        return Mono.just(authentication);
    }
}
