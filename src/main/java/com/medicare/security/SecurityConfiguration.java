package com.medicare.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private String[] WHITE_LISTED_URLS={
            "/api/auth/**",
            "/product"



    };

    private static final String[] ADMIN_WHITE_LIST_URLS={


            "/product"

    };

    private static final String[] USER_WHITE_LIST_URLS={

            "/auth/user",
            "/auth/checking",
            "/product",
            "/cart/**",
            "/cart/remove/**"
    };

    private final AuthenticationProvider authProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf((cs)->cs.disable())
                        .authorizeHttpRequests((authHttp) -> authHttp.requestMatchers(WHITE_LISTED_URLS).permitAll()
                                .requestMatchers(USER_WHITE_LIST_URLS).hasAuthority("USER")
                                .requestMatchers(ADMIN_WHITE_LIST_URLS).hasAuthority("ADMIN"))
                                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                        .authenticationProvider(authProvider)
                                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);



        return httpSecurity.build();

    }

}
