package com.dbd.nanal.config.oauth;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class ApplicationOAuth2User implements OAuth2User {
    private String userId;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private OAuth2AccessToken oAuth2AccessToken;

    public ApplicationOAuth2User(String userId, Map<String, Object> attributes, OAuth2AccessToken oAuth2AccessToken) {
        this.userId = userId;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        this.attributes = attributes;
        this.oAuth2AccessToken = oAuth2AccessToken;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        return this.userId;
    }

    public OAuth2AccessToken getAccessToken() {
        return this.oAuth2AccessToken;
    }
}
