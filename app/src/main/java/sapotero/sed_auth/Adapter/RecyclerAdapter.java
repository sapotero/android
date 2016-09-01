package sapotero.sed_auth.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sapotero.sed_auth.JSON.Documents.Document;
import sapotero.sed_auth.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
  private List<Document> documentList;
  private Context context;

  public RecyclerAdapter(Context context, List<Document> itemList) {
    this.documentList = itemList;
    this.context = context;
  }
  @Override
  public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
    View layoutView = LayoutInflater.from( parent.getContext()).inflate(R.layout.document_item, null);
    RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
    return rcv;
  }
  @Override
  public void onBindViewHolder(RecyclerViewHolders holder, int position) {
    holder.title.setText( "Title: " + documentList.get(position).getUid()   );
    holder.uid.setText(   "UID: "   + documentList.get(position).getMd5()   );
    holder.md5.setText(   "MD5: "   + documentList.get(position).getTitle() );
  }
  @Override
  public int getItemCount() {
    return this.documentList.size();
  }
}