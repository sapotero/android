package sapotero.sed_auth.JSON.Documents;

import com.google.gson.annotations.SerializedName;

public class Document {
  @SerializedName("uid")
  private String uid;

  @SerializedName("md5")
  private String md5;

  @SerializedName("title")
  private String title;

  @SerializedName("registration_number")
  private String registration_number;

  @SerializedName("registration_date")
  private String registration_date;

  @SerializedName("urgency")
  private String urgency;

  @SerializedName("short_description")
  private String short_description;

  @SerializedName("comment")
  private String comment;

  @SerializedName("external_document_number")
  private String external_document_number;

  @SerializedName("receipt_date")
  private String receipt_date;

  @SerializedName("viewed")
  private String viewed;

  public Document( String uid, String md5, String title, String registration_number, String registration_date, String urgency, String short_description, String comment, String external_document_number, String receipt_date, String viewed ){
      this.uid                      = uid;
      this.md5                      = md5;
      this.title                    = title;
      this.registration_number      = registration_number;
      this.registration_date        = registration_date;
      this.urgency                  = urgency;
      this.short_description        = short_description;
      this.comment                  = comment;
      this.external_document_number = external_document_number;
      this.receipt_date             = receipt_date;
      this.viewed                   = viewed;
    }

  public String getUid() {
    return uid;
  }

  public String getMd5() {
    return md5;
  }

  public String getTitle() {
    return title;
  }

  public String getRegistrationNumber() {
    return registration_number;
  }

  public String getRegistrationDate() {
    return registration_date;
  }

  public String getUrgency() {
    return urgency;
  }

  public String getShortDescription() {
    return short_description;
  }

  public String getComment() {
    return comment;
  }

  public String getExternalDocumentNumber() {
    return external_document_number;
  }

  public String getReceiptDate() {
    return receipt_date;
  }

  public String getViewed() {
    return viewed;
  }

}
