package ro.upt.sma.context.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

@SuppressLint("MissingPermission")
public class LocationHandler {

  private static final String TAG = LocationHandler.class.getSimpleName();

  private final FusedLocationProviderClient client;

  public LocationHandler(Context context) {
    this.client = LocationServices.getFusedLocationProviderClient(context);
  }

  public void registerLocationListener(LocationCallback locationCallback) {
    LocationRequest request = new LocationRequest();
    request.setSmallestDisplacement(10);
    request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    client.requestLocationUpdates(request, locationCallback, null)
        .addOnCompleteListener(
            new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                  Log.d(TAG, "onComplete: " + task.isSuccessful());
                }
              }
            });
  }

  public void unregisterLocationListener(LocationCallback locationCallback) {
    client.removeLocationUpdates(locationCallback);
  }

}
