package com.jygoh.heartspace.global.security.jwt.filter;

import com.jygoh.heartspace.global.security.auth.service.CustomUserDetailsService;
import com.jygoh.heartspace.global.security.jwt.service.JwtTokenProvider;
import com.jygoh.heartspace.global.security.utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider,
        CustomUserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // 필터 통과 경로 구현

        String token = TokenUtils.extractTokenFromRequest(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Long userId = TokenUtils.getUserIdFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserById(userId);
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (token == null) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

}
