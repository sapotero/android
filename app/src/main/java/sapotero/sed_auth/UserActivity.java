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
import java.util.Timer;
import java.util.TimerTask;

public class UserActivity extends AppCompatActivity {
  public final static String TOKEN = "";

  private Timer timer;

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
