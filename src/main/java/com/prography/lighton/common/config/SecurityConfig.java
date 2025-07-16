package com.prography.lighton.common.config;

import com.prography.lighton.auth.security.filter.JwtAuthenticationFilter;
import com.prography.lighton.auth.security.filter.SecurityExceptionFilter;
import com.prography.lighton.auth.security.handler.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityExceptionFilter securityExceptionFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(securityExceptionFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/health").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/members").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/token/refresh").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/phones/code").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/phones/code/verify").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/members/duplicate-check").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/members/*/info").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/members/*/info").permitAll()
                        .requestMatchers("/api/oauth/**").permitAll()
                        .requestMatchers("api/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/members/performances/popular").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/members/performances/nearby").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/members/performances/recent").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/members/performances").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/members/performances/*").permitAll()

                        // Swagger & OpenAPI
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/docs/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )
                .exceptionHandling(eh -> eh.accessDeniedHandler(customAccessDeniedHandler))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}