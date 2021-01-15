package com.dixitpatel.mycoffeevenue.location;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 *  RxJava Wrapper class of Finding location
 *  2. location last : Last User's location
 */
public class LocationLastSingleOnSubscribe implements SingleOnSubscribe<Location> {

    private final FusedLocationProviderClient mLocationProviderClient;

    public LocationLastSingleOnSubscribe(Context context) {
        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    public void subscribe(SingleEmitter<Location> emitter) throws Exception {
        try {
            mLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location == null)
                            emitter.onError(new NoLocationAvailableException());
                        else
                            emitter.onSuccess(location);
                    })
                    .addOnFailureListener(emitter::onError);
        } catch (SecurityException e) {
            emitter.onError(e);
        }
    }
}
