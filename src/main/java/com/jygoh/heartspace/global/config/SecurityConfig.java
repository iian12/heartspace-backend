package com.jygoh.heartspace.global.config;

import com.jygoh.heartspace.global.handler.CustomAccessDeniedHandler;
import com.jygoh.heartspace.global.handler.CustomOAuth2SuccessHandler;
import com.jygoh.heartspace.global.handler.JwtAuthenticationEntryPoint;
import com.jygoh.heartspace.global.security.auth.service.CustomOAuth2UserService;
import com.jygoh.heartspace.global.security.auth.service.CustomUserDetailsService;
import com.jygoh.heartspace.global.security.jwt.filter.JwtTokenFilter;
import com.jygoh.heartspace.global.security.jwt.service.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;


    public SecurityConfig(JwtTokenProvider jwtTokenProvider,
        CustomUserDetailsService userDetailsService,
        CustomOAuth2UserService customOAuth2UserService,
        CustomOAuth2SuccessHandler customOAuth2SuccessHandler,
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
        CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.customOAuth2UserService = customOAuth2UserService;
        this.customOAuth2SuccessHandler = customOAuth2SuccessHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/chat/**").permitAll()
                .requestMatchers("/ws").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
                .anyRequest().authenticated())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2Login(oauth2 ->
                oauth2.successHandler(customOAuth2SuccessHandler)
                .userInfoEndpoint(userInfo ->
                    userInfo.userService(customOAuth2UserService)))
            .exceptionHandling(configurer -> {
                configurer.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                configurer.accessDeniedHandler(customAccessDeniedHandler);
            })
            .addFilterAfter(jwtTokenFilter(), OAuth2LoginAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(jwtTokenProvider, userDetailsService);
    }
}
