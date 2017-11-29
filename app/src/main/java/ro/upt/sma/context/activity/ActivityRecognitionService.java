package ro.upt.sma.context.activity;


import android.app.IntentService;
import android.content.Intent;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

public class ActivityRecognitionService extends IntentService {

  public static final String INTENT_ACTION = "ro.upt.sma.context.activity";
  public static final String ACTIVITY_EXTRA = "activity";

  public ActivityRecognitionService() {
    super("ActivityRecognitionService");
  }

  public ActivityRecognitionService(String name) {
    super(name);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    if (ActivityRecognitionResult.hasResult(intent)) {
      ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
      DetectedActivity detectedActivity = result.getMostProbableActivity();
      Intent broadcastIntent = new Intent(INTENT_ACTION);
      broadcastIntent.putExtra(ACTIVITY_EXTRA, detectedActivity.getType());
      sendBroadcast(broadcastIntent);
    }
  }

}
