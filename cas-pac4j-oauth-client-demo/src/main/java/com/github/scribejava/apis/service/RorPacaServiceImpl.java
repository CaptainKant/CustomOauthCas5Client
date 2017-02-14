//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.github.scribejava.apis.service;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.AbstractRequest;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.oauth.OAuth20Service;

public class RorPacaServiceImpl extends OAuth20Service {
    public RorPacaServiceImpl(DefaultApi20 api, OAuthConfig config) {
        super(api, config);
    }

    public void signRequest(OAuth2AccessToken accessToken, AbstractRequest request) {
        request.addQuerystringParameter("oauth2_access_token", accessToken.getAccessToken());
    }

    protected <T extends AbstractRequest> T createAccessTokenRequest(String code, T request) {
        super.createAccessTokenRequest(code, request);
        if(!this.getConfig().hasGrantType()) {
            request.addParameter("grant_type", "authorization_code");
        }

        return request;
    }
}
