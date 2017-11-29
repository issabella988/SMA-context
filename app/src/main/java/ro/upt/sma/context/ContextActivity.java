package ro.upt.sma.context;

import android.Manifest.permission;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import java.text.MessageFormat;
import ro.upt.sma.context.activity.ActivityRecognitionHandler;
import ro.upt.sma.context.location.LocationHandler;

public class ContextActivity extends AppCompatActivity implements OnMapReadyCallback {

  private static final int PERMISSION_REQUEST_ID = 111;

  private SupportMapFragment fMap;
  private TextView tvLocation;
  private TextView tvActivity;

  private GoogleMap googleMap;

  private LocationHandler locationHandler;
  private ActivityRecognitionHandler activityRecognitionHandler;
  private LocationCallback locationCallback;
  private PendingIntent activityPendingIntent;
  private BroadcastReceiver activityRecognitionReceiver;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    this.fMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.f_map);
    this.tvLocation = findViewById(R.id.tv_location);
    this.tvActivity = findViewById(R.id.tv_activity);

    this.locationHandler = new LocationHandler(this);
    this.activityRecognitionHandler = new ActivityRecognitionHandler(this);

    if (!isLocationPermissionGranted()) {
      ActivityCompat.requestPermissions(
          this,
          new String[]{permission.ACCESS_FINE_LOCATION},
          PERMISSION_REQUEST_ID);
    }
  }

  private boolean isLocationPermissionGranted() {
    return ContextCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED;
  }

  @Override
  protected void onResume() {
    super.onResume();

    if (isLocationPermissionGranted()) {
      fMap.getMapAsync(this);
      setupLocation();
      setupActivityRecognition();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();

    if (locationCallback != null) {
      locationHandler.unregisterLocationListener(locationCallback);
    }
    if (activityPendingIntent != null) {
      activityRecognitionHandler.unregisterPendingIntent(activityPendingIntent);
    }
    if (activityRecognitionReceiver != null) {
      unregisterReceiver(activityRecognitionReceiver);
    }
  }

  @Override
  public void onRequestPermissionsResult(
      int requestCode,
      @NonNull String permissions[],
      @NonNull int[] grantResults
  ) {
    switch (requestCode) {
      case PERMISSION_REQUEST_ID: {
        if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
          Toast.makeText(this, R.string.toast_location_permission, Toast.LENGTH_SHORT)
              .show();
        }
      }
    }
  }

  @Override
  public void onMapReady(final GoogleMap googleMap) {
    this.googleMap = googleMap;
  }

  private void setupLocation() {
    this.locationCallback = new LocationCallback() {
      @Override
      public void onLocationResult(LocationResult locationResult) {
        Location location = locationResult.getLastLocation();
        updateMap(location);
        updateLocationCard(location);
      }
    };
    locationHandler.registerLocationListener(locationCallback);
  }

  private void setupActivityRecognition() {
    this.activityPendingIntent = activityRecognitionHandler.registerPendingIntent();

    this.activityRecognitionReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        // TODO 6: Extract activity type from intent extras and pass it to updateActivityCard() method.
        // Take a look at ActivityRecognitionService to see how intent extras are formed.

      }
    };

    // TODO 7: Register created receiver only for ActivityRecognitionService.INTENT_ACTION.
    registerReceiver(activityRecognitionReceiver, new IntentFilter());
  }

  private void updateMap(Location location) {
    if (googleMap != null) {
      // TODO 3: Clear current marker and create a new marker based on the received location object.

      // TODO 4: Use CameraUpdateFactory to perform a zoom in.

    }
  }

  private void updateLocationCard(Location location) {
    tvLocation.setText(MessageFormat.format("Latitude: {0}\nLongitude: {1}\nAltitude: {2}",
        location.getLatitude(), location.getLongitude(), location.getAltitude()));
  }

  private void updateActivityCard(int activityType) {
    int activityResId;

    switch (activityType) {
      case DetectedActivity.IN_VEHICLE:
        activityResId = R.string.activity_in_vehicle;
        break;
      case DetectedActivity.ON_BICYCLE:
        activityResId = R.string.activity_on_bicycle;
        break;
      case DetectedActivity.ON_FOOT:
        activityResId = R.string.activity_on_foot;
        break;
      case DetectedActivity.RUNNING:
        activityResId = R.string.activity_running;
        break;
      case DetectedActivity.WALKING:
        activityResId = R.string.activity_walking;
        break;

      case DetectedActivity.TILTING:
        activityResId = R.string.activity_tilting;
        break;
      case DetectedActivity.STILL:
        activityResId = R.string.activity_still;
        break;
      default:
        activityResId = R.string.activity_unknown;
        break;
    }

    tvActivity.setText(
        String.format("%s: %s", getString(R.string.activity_title), getString(activityResId)));
  }

}
