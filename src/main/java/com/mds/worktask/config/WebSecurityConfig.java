package com.mds.worktask.config;

import com.mds.worktask.config.security.AuthManager;
import com.mds.worktask.config.security.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.view.HttpMessageWriterView;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig implements WebFluxConfigurer {

    @Autowired
    SecurityFilter securityFilter;
    @Autowired
    AuthManager authManager;

//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.freeMarker();
//
//        Jackson2JsonEncoder encoder = new Jackson2JsonEncoder();
//        registry.defaultViews(new HttpMessageWriterView(encoder));
//    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity http) {
        http
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/actuator/health/**"))
                .authorizeExchange((exchanges) -> exchanges
                        .anyExchange().permitAll()
                );
        return http.build();
    }
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().authenticated()
                )
                .securityContextRepository(securityFilter)
                .authenticationManager(authManager)
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable();
        return http.build();
    }
}
