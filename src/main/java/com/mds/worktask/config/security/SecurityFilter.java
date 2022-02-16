package com.mds.worktask.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityFilter implements ServerSecurityContextRepository {
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return null;
    }
    @Autowired
    AuthManager authManager;

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        SecurityContext securityContext = new SecurityContextImpl();
        AuthenticationJwt authentication = new AuthenticationJwt();
        securityContext.setAuthentication(authentication);
        String token = exchange.getRequest().getHeaders().getFirst("Authentication");
        if(token==null){
            return Mono.just(securityContext);
        }
        authentication.setCredentials(token);
        authManager.authenticate(authentication);
        return Mono.just(securityContext);
    }
}
