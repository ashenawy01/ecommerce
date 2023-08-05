package com.sigma.ecommerce.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("https://smart-sigma.us.auth0.com/.well-known/jwks.json").build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .authorizeHttpRequests(config ->
                        config.requestMatchers("api/orders/**")
                                .authenticated().anyRequest().permitAll())
                 .oauth2ResourceServer((oauth2) -> oauth2
                            .jwt(Customizer.withDefaults()))
                 .httpBasic(Customizer.withDefaults());

         http.cors(Customizer.withDefaults());

         http.setSharedObject(ContentNegotiationStrategy.class,
                 new HeaderContentNegotiationStrategy());

        Okta.configureResourceServer401ResponseBody(http);

         return http.build();

    }
}
