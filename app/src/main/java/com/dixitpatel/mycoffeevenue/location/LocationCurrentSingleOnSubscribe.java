package com.dixitpatel.mycoffeevenue.location;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 *  RxJava Wrapper class of Finding location
 *  2. locationCurrent : Current User location
 */
public class LocationCurrentSingleOnSubscribe implements SingleOnSubscribe<Location> {

    private static final int DEF_UPDATE_INTERVAL = 500;

    private FusedLocationProviderClient mLocationProviderClient;
    private LocationRequest mLocationRequest;

    public LocationCurrentSingleOnSubscribe(Context context, LocationRequest locationRequest) {
        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        mLocationRequest = locationRequest;

        // Override to restricted values
        mLocationRequest.setInterval(DEF_UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(DEF_UPDATE_INTERVAL);
        mLocationRequest.setNumUpdates(1);
    }

    @Override
    public void subscribe(SingleEmitter<Location> emitter) throws Exception {
        LocationCallback callback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult.getLastLocation() == null)
                    emitter.onError(new NoLocationAvailableException());
                else
                    emitter.onSuccess(locationResult.getLastLocation());
                mLocationProviderClient.removeLocationUpdates(this);
            }
        };

        try {
            mLocationProviderClient
                    .requestLocationUpdates(mLocationRequest, callback, null)
                    .addOnFailureListener(emitter::onError);
        } catch (SecurityException e) {
            emitter.onError(e);
        }

        emitter.setCancellable(() -> mLocationProviderClient.removeLocationUpdates(callback));
    }
}
