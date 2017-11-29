package ro.upt.sma.context.location;

import android.annotation.SuppressLint;
import android.content.Context;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;

@SuppressLint("MissingPermission")
public class LocationHandler {

  private final FusedLocationProviderClient client;

  public LocationHandler(Context context) {
    this.client = LocationServices.getFusedLocationProviderClient(context);
  }

  public void registerLocationListener(LocationCallback locationCallback) {
    // TODO 1: Create a LocationRequest with PRIORITY_HIGH_ACCURACY and smallest displacement to 10m.

    // TODO 2: Register request and callback with the fused location service client.

  }

  public void unregisterLocationListener(LocationCallback locationCallback) {
    client.removeLocationUpdates(locationCallback);
  }

}
