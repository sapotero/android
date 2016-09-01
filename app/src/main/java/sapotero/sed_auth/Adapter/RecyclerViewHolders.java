package sapotero.sed_auth.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sapotero.sed_auth.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
  public TextView title;
  public TextView uid;
  public TextView md5;

  public RecyclerViewHolders(View itemView) {
    super(itemView);
    itemView.setOnClickListener(this);
    title = (TextView)itemView.findViewById( R.id.title);
    uid = (TextView)itemView.findViewById( R.id.uid  );
    md5 = (TextView)itemView.findViewById( R.id.md5);
  }
  @Override
  public void onClick(View view) {
  }
}