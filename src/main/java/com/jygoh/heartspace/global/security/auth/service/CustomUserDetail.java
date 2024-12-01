package com.jygoh.heartspace.global.security.auth.service;

import com.jygoh.heartspace.domain.user.model.Role;
import com.jygoh.heartspace.domain.user.model.Users;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomUserDetail implements UserDetails, OAuth2User {

    private final Users user;
    private final Long userId;
    private final boolean isNewUser;

    public CustomUserDetail(Users user, Long userId, boolean isNewUser) {
        this.user = user;
        this.userId = userId;
        this.isNewUser = isNewUser;
    }

    @Override
    public String getName() {
        return user != null ? String.valueOf(user.getId()) : null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        if (user != null) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("id", user.getId());
            attributes.put("email", user.getEmail());
            attributes.put("nickname", user.getNickname());
            attributes.put("profileImgUrl", user.getProfileImgUrl());
            return attributes;
        }

        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        switch (user.getRole()) {
            case ADMIN: authorities.add(getAuthority(Role.ADMIN));
            case USER: authorities.add(getAuthority(Role.USER));
        }

        return authorities;
    }

    private GrantedAuthority getAuthority(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role);
    }

    @Override
    public String getPassword() {
        return user != null ? user.getPassword() : null;
    }

    @Override
    public String getUsername() {
        return user != null ? user.getEmail() : null;
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

    public boolean isNewUser() {
        return isNewUser;
    }

    public String getNickname() {
        return user != null ? user.getNickname() : null;
    }
}
