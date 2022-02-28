package com.project.momo.security.oauth.dto;

import com.project.momo.common.exception.InsufficientOAuthAttributesException;
import com.project.momo.security.consts.OauthType;
import org.springframework.util.StringUtils;

import java.util.Map;

public class GithubOAuthAttributes extends OAuthAttributes {

    private static final String NAME = "login";
    private static final String EMAIL = "email";
    private static final String IMAGE_URL = "avatar_url";
    private static final String USER_NAME_ATTRIBUTE_NAME = "id";

    public GithubOAuthAttributes(long oAuthId, String name, String email, String imageUrl) {
        super(OauthType.GITHUB,USER_NAME_ATTRIBUTE_NAME,  oAuthId, name, email, imageUrl);
    }

    public static GithubOAuthAttributes ofAttributes(Map<String, Object> attributes) {
        String name = (String) attributes.get(NAME);
        String email = (String) attributes.get(EMAIL);

        checkRequiredProperties(name, email);

        String imageUrl = (String) attributes.get(IMAGE_URL);
        long oAuthId = Long.parseLong(String.valueOf(attributes.get(USER_NAME_ATTRIBUTE_NAME)));

        return new GithubOAuthAttributes(oAuthId, name, email, imageUrl);
    }

    protected static void checkRequiredProperties(String name, String email) {
        if(!StringUtils.hasText(name) || !StringUtils.hasText(email)) throw InsufficientOAuthAttributesException.getInstance();
    }
}
