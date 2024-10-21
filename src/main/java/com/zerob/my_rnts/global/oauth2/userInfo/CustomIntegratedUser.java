package com.zerob.my_rnts.global.oauth2.userInfo;

import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.global.oauth2.OAuth2Attributes;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class CustomIntegratedUser implements UserDetails, OAuth2User {

    private final Member member;
    private OAuth2Attributes oAuth2Attributes;

    public CustomIntegratedUser(Member member) {
        this.member = member;
    }

    public CustomIntegratedUser(Member member, OAuth2Attributes oAuth2Attributes) {
        this.member = member;
        this.oAuth2Attributes = oAuth2Attributes;
    }

    @Override
    public String getName() {
        return oAuth2Attributes.getOAuth2UserInfo().getSocialId();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Attributes.getOAuth2UserInfo().attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(((GrantedAuthority) () -> member.getRole().name()));
        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword().password();
    }

    @Override
    public String getUsername() {
        return member.getLoginId().loginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
