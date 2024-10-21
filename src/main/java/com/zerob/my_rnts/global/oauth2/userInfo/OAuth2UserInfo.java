package com.zerob.my_rnts.global.oauth2.userInfo;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public abstract class OAuth2UserInfo {

    protected final Map<String, Object> attributes;

    public abstract String getSocialId();
    public abstract String getMail();
    public abstract String getNickname();
    public abstract String getProfileImage();
}
