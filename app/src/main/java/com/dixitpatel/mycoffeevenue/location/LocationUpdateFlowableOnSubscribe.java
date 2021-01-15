package com.dixitpatel.mycoffeevenue.location;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 *  RxJava Wrapper class of Finding location
 *  2. LocationUpdate : Change User's location
 */
public class LocationUpdateFlowableOnSubscribe implements FlowableOnSubscribe<Location> {

    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mLocationProviderClient;

    public LocationUpdateFlowableOnSubscribe(Context context, LocationRequest locationRequest) {
        mLocationRequest = locationRequest;
        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    public void subscribe(FlowableEmitter<Location> emitter) throws Exception {
        FusedLocationCallback callback = new FusedLocationCallback(emitter);
        try {
            mLocationProviderClient
                    .requestLocationUpdates(mLocationRequest, callback, null)
                    .addOnFailureListener(emitter::onError);
        } catch (SecurityException e) {
            emitter.onError(e);
        }

        emitter.setCancellable(() -> {
            mLocationProviderClient.removeLocationUpdates(callback);
            callback.emitter = null;
        });
    }

    class FusedLocationCallback extends LocationCallback {

        FlowableEmitter<Location> emitter;

        FusedLocationCallback(FlowableEmitter<Location> emitter) {
            this.emitter = emitter;
        }

        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (emitter != null && locationResult.getLastLocation() != null) {
                emitter.onNext(locationResult.getLastLocation());
            } else {
                emitter.onError(new NoLocationAvailableException());
            }
        }
    }

}
