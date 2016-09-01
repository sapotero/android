package sapotero.sed_auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import sapotero.sed_auth.Adapter.DocumentItemDecorator;
import sapotero.sed_auth.Adapter.RecyclerAdapter;
import sapotero.sed_auth.JSON.Documents.Document;
import sapotero.sed_auth.JSON.Documents.Documents;

public class UserActivity extends AppCompatActivity {
  static String TOKEN = "";
  static String USER  = "";
  static String DOCUMENTS_URL = LoginActivity.HOST + "v3/documents.json";

  private Timer timer;

  private final String TAG = "MainActivity";
  private RecyclerView recyclerView;
  private LinearLayoutManager layoutManager;
  private RecyclerAdapter adapter;


  /* ------------------------------------- */




  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);

    Intent intent = getIntent();
    String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
    USER = intent.getStringExtra(LoginActivity.USER_MESSAGE);
    TOKEN = message;

    TextView data = (TextView)findViewById(R.id.data);
    data.setText( message );


    message = getMd5Hash( SystemClock.currentThreadTimeMillis() + LoginActivity.getUniqueID() );
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
    recyclerView.addItemDecoration(new DocumentItemDecorator(this));
    layoutManager = new LinearLayoutManager(UserActivity.this);
    recyclerView.setLayoutManager(layoutManager);

  }


  @Override
  public void onBackPressed() {
    super.moveTaskToBack(true);
  }

  @Override
  protected void onUserLeaveHint() {
    Toast toast = Toast.makeText(getApplicationContext(), "Нажата кнопка HOME", Toast.LENGTH_SHORT);
    toast.show();
    super.onUserLeaveHint();
  }

  public static String getMd5Hash(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] messageDigest = md.digest(input.getBytes());
      BigInteger number = new BigInteger(1, messageDigest);
      String md5 = number.toString(16);

      while (md5.length() < 32)
        md5 = "0" + md5;

      return md5;
    } catch (NoSuchAlgorithmException e) {
      Log.e("MD5", e.getLocalizedMessage());
      return null;
    }
  }

  public void logUniqId(View view) {
    String message = getMd5Hash( System.nanoTime() + LoginActivity.getUniqueID() );
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void onPause() {
    super.onPause();

    timer = new Timer();
    Log.i("Main", "Invoking logout timer");
    LogOutTimerTask logoutTimeTask = new LogOutTimerTask();
    timer.schedule(logoutTimeTask, 1*60*1000 ); //auto logout in 1 minute
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (timer != null) {
      timer.cancel();
      Log.i("Main", "cancel timer");
      timer = null;
    }
  }

  public void getDocuments(View view){

    RequestQueue queue = Volley.newRequestQueue(this);

    Uri.Builder builder = new Uri.Builder();
    builder.scheme("http")
        .authority( "mobile.esd.n-core.ru" )
        .appendPath("v3")
        .appendPath("documents.json")
        .appendQueryParameter("login", USER)
        .appendQueryParameter("auth_token", TOKEN)
        .appendQueryParameter("status_code", "sent_to_the_report")
        .appendQueryParameter("request_uid", getMd5Hash( System.nanoTime() + LoginActivity.getUniqueID() ) );

    String url = builder.build().toString();

    StringRequest documentsRequest = new StringRequest(Request.Method.GET, url,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            Log.e("HttpClient", "success! response: " + response.toString());

            Gson gson = new Gson();
            Documents json = gson.fromJson(response, Documents.class);


            List<Document> documents = json.getDocuments();
            Log.i( "META", json.getMeta().getTotal() );
//            Log.v( "doc", documents.get(0).getMd5() );

            adapter = new RecyclerAdapter(UserActivity.this, documents);
            recyclerView.setAdapter(adapter);

          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.e("HttpClient", "error: " + error.toString());
          }
        });
// for post params
//    {
//      @Override
//      protected Map<String,String> getParams(){
//        Map<String,String> params = new HashMap<String, String>();
//        params.put("user","YOUR USERNAME");
//        params.put("pass","YOUR PASSWORD");
//        return params;
//      }
//      @Override
//      public Map<String, String> getHeaders() throws AuthFailureError {
//        Map<String,String> params = new HashMap<String, String>();
//        params.put("Content-Type","application/x-www-form-urlencoded");
//        return params;
//      }
//    };
    queue.add(documentsRequest);
  }

  private class LogOutTimerTask extends TimerTask {

    @Override
    public void run() {

      //redirect user to login screen
      Intent i = new Intent(UserActivity.this, LoginActivity.class);
      i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(i);
      finish();
    }
  }

}
