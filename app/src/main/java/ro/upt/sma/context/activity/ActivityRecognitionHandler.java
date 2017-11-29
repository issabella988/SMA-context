package ro.upt.sma.context.activity;

import android.app.PendingIntent;
import android.content.Context;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;

public class ActivityRecognitionHandler {

  private final ActivityRecognitionClient client;

  public ActivityRecognitionHandler(Context context) {
    this.client = ActivityRecognition.getClient(context);
  }

  public PendingIntent registerPendingIntent() {
    PendingIntent pendingIntent = null;

    // TODO 5: Create a pending intent for ActivityRecognitionService and register for updates to activity recognition client.

    return pendingIntent;
  }

  public void unregisterPendingIntent(PendingIntent pendingIntent) {
    client.removeActivityUpdates(pendingIntent);
  }

}
