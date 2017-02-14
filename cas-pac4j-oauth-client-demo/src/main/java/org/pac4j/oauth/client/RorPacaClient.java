package org.pac4j.oauth.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.apis.RorPacaApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.ror.RorProfile ;

/**
 * Created by ecervetti on 14/02/2017.
 */
public class RorPacaClient  extends BaseOAuth20Client<RorProfile> {


    public RorPacaClient( String key,  String secret) {
        setKey(key);
        setSecret(secret);
    }

    @Override
    protected String getProfileUrl(final OAuth2AccessToken accessToken) {
        return RorPacaApi.BASE_URL + "/professionnel/current.json?access_token=" + accessToken.toString();
    }

    @Override
    protected BaseApi<OAuth20Service> getApi() {
        return RorPacaApi.instance();
    }


    @Override
    protected RorProfile extractUserProfile(String body) throws HttpAction {
        RorProfile profile = new RorProfile();
        JsonNode json = JsonHelper.getFirstNode(body);
        if (json == null) {
            return profile;
        }

        JsonNode user = (JsonNode) JsonHelper.getElement(json, "user");
        if (user != null) {
            profile.setId(JsonHelper.getElement(user, "id"));

            for (final String attribute : profile.getAttributesDefinition().getPrimaryAttributes()) {
                profile.addAttribute(attribute, JsonHelper.getElement(user, attribute));
            }
        }
        return profile;
    }

}
