package com.github.scribejava.apis;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.utils.OAuthEncoder;

/**
 * Created by ecervetti on 14/02/2017.
 */
public class RorPacaApi extends DefaultApi20
{
    public  static final String BASE_URL="https://www.ror-paca.fr/" ;

    public static RorPacaApi instance() {
        return RorPacaApi.InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final RorPacaApi INSTANCE = new RorPacaApi();
    }

    @Override
    public String getAccessTokenEndpoint() {
        return BASE_URL+"oauth/v2/token";
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        StringBuilder sb = new StringBuilder(
                String.format(BASE_URL+"/v2/auth?response_type=%s&client_id=%s&redirect_uri=%s&scope=%s",
                        new Object[]{config.getResponseType(), config.getApiKey(), OAuthEncoder.encode(config.getCallback()), OAuthEncoder.encode(config.getScope())}));
        String state = config.getState();
        if(state != null) {
            sb.append('&').append("state").append('=').append(OAuthEncoder.encode(state));
        }
        return sb.toString();
    }


}
