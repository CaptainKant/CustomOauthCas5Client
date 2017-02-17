package org.pac4j.oauth.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.util.CommonHelper;
import org.scribe.builder.api.*;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.ror.RorProfile ;
import org.scribe.model.OAuthConfig;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.builder.api.RorPacaApi ;
import org.scribe.oauth.PayPalOAuth20ServiceImpl;
import org.scribe.oauth.ProxyOAuth10aServiceImpl;
import org.scribe.oauth.StateOAuth20ServiceImpl;

import java.io.OutputStream;

/**
 * Created by ecervetti on 14/02/2017.
 */
public class RorPacaClient  extends BaseOAuth20StateClient<RorProfile> {


    public RorPacaClient( String key,  String secret) {
        setKey(key);
        setSecret(secret);
    }

    public RorPacaClient() {

    }

    protected void internalInit(WebContext context) {
        super.internalInit(context);
        this.service = new StateOAuth20ServiceImpl(new RorPacaApi(), new OAuthConfig(this.key, this.secret, this.computeFinalCallbackUrl(context), SignatureType.Header, (String)null, (OutputStream)null), this.connectTimeout, this.readTimeout, this.proxyHost, this.proxyPort, false, true);
    }

    protected RorPacaClient newClient() {
        RorPacaClient newClient = new RorPacaClient();
        return newClient;
    }

    @Override
    protected boolean hasBeenCancelled(WebContext context) {
        String error = context.getRequestParameter("error");
        return "access_denied".equals(error);
    }

    @Override
    protected String getProfileUrl(final Token accessToken) {
        return RorPacaApi.BASE_URL + "/professionnel/rest/current.json?access_token=" + accessToken.toString();
    }


    @Override
    protected RorProfile extractUserProfile(String body)  {
        RorProfile profile = new RorProfile();
        JsonNode json = JsonHelper.getFirstNode(body);
        if (json == null) {
            return profile;
        }

        JsonNode user = (JsonNode) JsonHelper.get(json, "user");
        if (user != null) {
            profile.setId(JsonHelper.get(user, "id"));
            profile.addAttribute("todo", "recup profil");
        }
        return profile;
    }


}
