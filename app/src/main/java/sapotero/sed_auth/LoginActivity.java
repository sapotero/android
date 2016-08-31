package sapotero.sed_auth;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "MESSAGE";
    public final static String TOKEN_URL = "http://mobile.esd.n-core.ru/token/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void tryToLogin(View view) {
        final Intent intent = new Intent(this, UserActivity.class);
        final Context context= this;

        EditText username = (EditText) findViewById(R.id.input_username);
        String usernameValue = username.getText().toString();

        EditText password = (EditText) findViewById(R.id.input_password);
        String passwordValue= password.getText().toString();


        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, TOKEN_URL+String.format("%s.json?password=%s", usernameValue, passwordValue),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        intent.putExtra( EXTRA_MESSAGE, response.toString() );
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                intent.putExtra(EXTRA_MESSAGE, "That didn't work!");
//                startActivity(intent);
                Toast.makeText( context, "That didn't work!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}
