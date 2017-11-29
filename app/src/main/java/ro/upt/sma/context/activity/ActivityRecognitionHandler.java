package ro.upt.sma.context.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;

public class ActivityRecognitionHandler {

  private final ActivityRecognitionClient client;

  public ActivityRecognitionHandler(Context context) {
    this.client = ActivityRecognition.getClient(context);
  }

  public PendingIntent registerPendingIntent() {
    Intent intent = new Intent(client.getApplicationContext(), ActivityRecognitionService.class);
    PendingIntent pendingIntent = PendingIntent.getService(
        client.getApplicationContext(),
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT);
    client.requestActivityUpdates(1000, pendingIntent);

    return pendingIntent;
  }

  public void unregisterPendingIntent(PendingIntent pendingIntent) {
    client.removeActivityUpdates(pendingIntent);
  }

}
