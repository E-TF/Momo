package com.project.momo.security.oauth;

import com.project.momo.security.consts.OauthType;

import java.util.Map;

public class GithubOAuthAttributes extends OAuthAttributes {

    private static final String NAME = "login";
    private static final String EMAIL = "email";
    private static final String IMAGE_URL = "avatar_url";
    private static final String USER_NAME_ATTRIBUTE_NAME = "id";

    public GithubOAuthAttributes(String oAuthId, String name, String email, String imageUrl) {
        super(OauthType.GITHUB, oAuthId, name, email, imageUrl);
    }

    public static GithubOAuthAttributes ofAttributes(Map<String, Object> attributes) {
        String name = (String) attributes.get(NAME);
        String email = (String) attributes.get(EMAIL);
        String imageUrl = (String) attributes.get(IMAGE_URL);
        String oAuthId = String.valueOf(attributes.get(USER_NAME_ATTRIBUTE_NAME));

        return new GithubOAuthAttributes(oAuthId, name, email, imageUrl);
    }

}