package org.eclipse.openvsx.security;

import org.eclipse.openvsx.OVSXConfig;
import org.eclipse.openvsx.OVSXConfig.AuthConfig.AttributeNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class AuthUserFactory {

    private final OVSXConfig config;

    public AuthUserFactory(
        OVSXConfig config
    ) {
        this.config = config;
    }

    public AuthUser createAuthUser(String providerId, OAuth2User oauth2User) {
        var attributeNames = getAttributeNames(providerId);
        return new DefaultAuthUser(
            oauth2User.getName(),
            oauth2User.getAttribute(attributeNames.getAvatarUrl()),
            oauth2User.getAttribute(attributeNames.getEmail()),
            oauth2User.getAttribute(attributeNames.getFullName()),
            oauth2User.getAttribute(attributeNames.getLoginName()),
            providerId,
            oauth2User.getAttribute(attributeNames.getProviderUrl())
        );
    }

    private AttributeNames getAttributeNames(String provider) {
        return config.getAuthConfig().getAttributeNames().getOrDefault(provider, AttributeNames.EMPTY);
    }
}
