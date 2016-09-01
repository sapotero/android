package sapotero.sed_auth.JSON.Documents;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Documents {
  @SerializedName("documents")
  private ArrayList<Document> documents = new ArrayList<Document>();

  @SerializedName("meta")
  private DocumentMeta meta;

  public List<Document> getDocuments() {
    return documents;
  }

  public DocumentMeta getMeta() {
    return meta;
  }
}
