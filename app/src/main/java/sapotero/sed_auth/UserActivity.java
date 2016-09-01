package sapotero.sed_auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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


    message = getMd5Hash( SystemClock.currentThreadTimeMillis() + LoginActivity.getUniqueID() );
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

}
