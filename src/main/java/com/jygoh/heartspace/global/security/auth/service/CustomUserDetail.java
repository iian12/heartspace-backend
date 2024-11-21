package com.jygoh.heartspace.global.security.auth.service;

import com.jygoh.heartspace.domain.user.model.Users;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomUserDetail implements UserDetails, OAuth2User {

    private Users user;
    private final OAuth2User oAuth2User;
    private final Long userId;

    public CustomUserDetail(Users user, Long userId) {
        this.user = user;
        this.oAuth2User = null;
        this.userId = userId;
    }

    @Override
    public String getName() {
        return oAuth2User != null ? oAuth2User.getName() : null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User != null ? oAuth2User.getAttributes() : null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user != null) {
            return null;
        } else if (oAuth2User != null) {
            return oAuth2User.getAuthorities();
        }
        return null;
    }

    @Override
    public String getPassword() {
        return user != null ? user.getPassword() : null;
    }

    @Override
    public String getUsername() {
        return user != null ? user.getEmail()
            : Objects.requireNonNull(oAuth2User).getAttribute("email");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getUserId() {
        return userId;
    }

    public Users getUser() {
        return user;
    }

    public String getProfileId() {
        return user != null ? user.getProfileImgUrl() : null;
    }
}