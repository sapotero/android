package sapotero.sed_auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.UUID;

public class LoginActivity extends AppCompatActivity {

  public final static String EXTRA_MESSAGE = "MESSAGE";
  public final static String USER_MESSAGE  = "USER_MESSAGE";

  public final static String HOST = "http://mobile.esd.n-core.ru/";

  public final static String TOKEN_URL  = "http://mobile.esd.n-core.ru/token/";
  public ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
  }

  public void tryToLogin(View view) {

    EditText username = (EditText) findViewById(R.id.input_username);
    final String usernameValue = username.getText().toString();

    EditText password = (EditText) findViewById(R.id.input_password);
    final String passwordValue= password.getText().toString();

    progressDialog = new ProgressDialog(LoginActivity.this);
    progressDialog.setIndeterminate(true);
    progressDialog.setMessage("Authenticating...");
    progressDialog.show();

    new android.os.Handler().postDelayed(
      new Runnable() {
        public void run() {
          sendLoginRequest( usernameValue, passwordValue );
        }
      }, 3000);

  }

  public void sendLoginRequest( final String username, String password){
    final Intent intent = new Intent(this, UserActivity.class);
    final Context context= this;

    RequestQueue queue = Volley.newRequestQueue(this);

    // Request a string response from the provided URL.
    StringRequest stringRequest = new StringRequest(Request.Method.PUT, TOKEN_URL+String.format("%s.json?password=%s", username, password),
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {

            AuthTokenJson token = new Gson().fromJson(response, AuthTokenJson.class);

            intent.putExtra( EXTRA_MESSAGE, token.getToken() );
            progressDialog.dismiss();
            intent.putExtra( USER_MESSAGE, username);
            startActivity(intent);
            onLoginSuccess();
          }
        }, new Response.ErrorListener() {

      @Override
      public void onErrorResponse(VolleyError error) {
        progressDialog.dismiss();
        Toast.makeText( context, "That didn't work!", Toast.LENGTH_SHORT).show();
      }
    });
    queue.add(stringRequest);
  }

  public void onLoginSuccess() {
    finish();
  }

  public static String getUniqueID() {
    String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

    String serial = null;
    try {
      serial = android.os.Build.class.getField("SERIAL").get(null).toString();

      return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    } catch (Exception exception) {
      serial = "serial"; // some value
    }

    return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
  }
}