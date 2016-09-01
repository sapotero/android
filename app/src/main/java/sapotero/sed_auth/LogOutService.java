package sapotero.sed_auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LogOutService extends Service {
  public LogOutService() {
  }

  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
