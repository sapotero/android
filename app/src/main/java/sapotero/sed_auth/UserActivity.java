package sapotero.sed_auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {
    public final static String TOKEN = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        System.out.println( message );

        TextView data = (TextView)findViewById(R.id.data);
        data.setText( message );

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();



    }
}
