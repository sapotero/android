package sapotero.sed_auth.JSON.Documents;

import com.google.gson.annotations.SerializedName;

public class DocumentSigner {
  @SerializedName("id")
  private String id;

  @SerializedName("type")
  private String type;

  @SerializedName("name")
  private String name;

  @SerializedName("origanization")
  private String origanization;

  public String getId() {
    return id;
  }
  public String getTypr() {
    return type;
  }
  public String getName() {
    return name;
  }
  public String getOrganization() {
    return origanization;
  }
}
