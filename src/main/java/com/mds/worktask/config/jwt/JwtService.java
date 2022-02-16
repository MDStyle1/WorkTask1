package com.mds.worktask.config.jwt;

import com.mds.worktask.config.security.AuthenticationJwt;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

@Component
public class JwtService {

    @Value("${secret.key}")
    private String secret;

    public boolean validateToken(String token, AuthenticationJwt authentication) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token).getBody();
        Date now = new Date();
        Date tokenData = claims.get("exp", Date.class);
        if(now.getTime()<tokenData.getTime()){
            Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) claims.get("authorities");
            authentication.setAuthorities(authorities);
            authentication.setAuthenticated(true);
            return true;
        }
        return false;
    }
}
