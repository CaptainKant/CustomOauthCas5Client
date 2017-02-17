package org.scribe.builder.api;

import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.Verb;
import org.scribe.utils.OAuthEncoder;

/**
 * Created by ecervetti on 14/02/2017.
 */
public class RorPacaApi extends StateApi20
{
    public  static final String BASE_URL="https://www.ror-paca.fr/" ;



    @Override
    public String getAccessTokenEndpoint() {
        return BASE_URL+"oauth/v2/token";
    }

    public String getAuthorizationUrl(OAuthConfig config, String state) {
        StringBuilder sb = new StringBuilder(
                String.format(BASE_URL+"/v2/auth?response_type=%s&client_id=%s&redirect_uri=%s&scope=%s&state=%s",
                        new Object[]{"code", config.getApiKey(), config.getCallback(), config.getScope(),state}));
        return sb.toString();
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        StringBuilder sb = new StringBuilder(
                String.format(BASE_URL+"/v2/auth?response_type=%s&client_id=%s&redirect_uri=%s&scope=%s",
                        new Object[]{"code", config.getApiKey(), config.getCallback(), config.getScope()}));
        return sb.toString();
    }

    @Override
    public Verb getAccessTokenVerb()
    {
        return Verb.POST;
    }

    @Override
    public AccessTokenExtractor getAccessTokenExtractor()
    {
        return new JsonTokenExtractor();
    }



}
