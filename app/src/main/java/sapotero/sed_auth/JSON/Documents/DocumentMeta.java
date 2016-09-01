package sapotero.sed_auth.JSON.Documents;

import com.google.gson.annotations.SerializedName;

public class DocumentMeta {
  @SerializedName("total")
  private String total;

  @SerializedName("skip_count")
  private String skip_count;

  @SerializedName("limit")
  private String limit;

  public String getTotal() {
    return total;
  }
  public String getSkipCount() {
    return skip_count;
  }
  public String getLimit() {
    return limit;
  }
}
