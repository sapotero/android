package sapotero.sed_auth.JSON;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sapotero on 01.09.16.
 */
public class AuthTokenJson {
    @SerializedName("auth_token")
    private String token;

    public String getToken() {
      return token;

  }
}
