package com.dixitpatel.mycoffeevenue.ui.main

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.dixitpatel.mycoffeevenue.R
import com.dixitpatel.mycoffeevenue.databinding.ActivityMapsBinding
import com.dixitpatel.mycoffeevenue.location.RxLocation
import com.dixitpatel.mycoffeevenue.model.NearByPlaceResponse
import com.dixitpatel.mycoffeevenue.ui.adapter.PlacesAdapter
import com.dixitpatel.mycoffeevenue.ui.base.BaseActivity
import com.dixitpatel.mycoffeevenue.utils.Alerts
import com.dixitpatel.mycoffeevenue.utils.Utils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

/**
 *  MainActivity: Display data of Nearest Venue Details on map and see location.
 */
class MainActivity : BaseActivity<MainActivityViewModel?>(), OnMapReadyCallback, PlacesAdapter.OnClickListener {

    // RxJava Disposable Object.
    private var rxLocationDisposable: Disposable? = null

    // Google Map Object
    private lateinit var mMap: GoogleMap

    // bind as Lazy : when binding property call then It it will be Initialized.
    private val binding: ActivityMapsBinding by binding(R.layout.activity_maps)

    @Inject
    lateinit var model: MainActivityViewModel

    // Save all markers in this list
    var markers:  ArrayList<Marker> = ArrayList()

    override fun getViewModel(): MainActivityViewModel {
        return model
    }

    private var backPressedTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        binding.progressBar.run {
            roundBorder = true
            indeterminateMode = true
            progressBarColor = ContextCompat.getColor(this@MainActivity, R.color.color_primary)
            progressBarWidth = 4F
            backgroundProgressBarWidth = 4F
            backgroundProgressBarColor = ContextCompat.getColor(
                this@MainActivity,
                R.color.transparent
            )
        }

        binding.apply {
            lifecycleOwner = this@MainActivity
            adapter = PlacesAdapter(this@MainActivity)
            vm = model
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Apply custom style in map.
        val style =
            MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_grayscale)
        mMap.setMapStyle(style)
        mMap.isIndoorEnabled = false
        mMap.isMyLocationEnabled = true


        getCurrentLocation()

        updateDataOnMap()
    }

    // get Places data from server and display markers on map.
    private fun updateDataOnMap()
    {
        // Once list receives from server observed here and calculate formatted average.
        model.placesListLiveData.observe(this, Observer {

            markers.clear()
            model.placesListLiveData.value?.forEach {

                mMap.let { it2 ->

                    // Add marker with loaded images
                    Picasso.get().load(it?.venue?.categories?.get(0)?.getIconUrl()).into(object : Target {

                        override fun onBitmapLoaded(bitmap: Bitmap?, loadedFrom: LoadedFrom?) {

                            // Add marker and bind title, snippet nd bitmap.
                            val nearestVenueLocation =
                                it?.venue?.location.let { it1 -> LatLng(it1?.lat!!, it1.lng) }
                            val marker = it2.addMarker(nearestVenueLocation.let { it1 ->
                                MarkerOptions().position(it1)
                                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                                    .title(it?.venue?.name).draggable(false)
                                    .snippet("${it?.venue?.location?.address}, ${it?.venue?.location?.crossStreet}, ${it?.venue?.location?.city}")

                            })
                            marker.tag = it?.venue?.id
                            markers.add(marker)
                        }

                        override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {}

                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                    }
                    )
                }
            }
        })

    }


    // getCurrent location of User.
    private fun getCurrentLocation()
    {
        rxLocationDisposable = RxLocation.locationCurrentBuilder(this)
            .build()
            .subscribe({ address ->
                try {
                    if (address != null) {

                        // Add current location marker on map.
                        val currentLocation = LatLng(address.latitude, address.longitude)
                        mMap.addMarker(
                            MarkerOptions().position(currentLocation).title("Current Location")
                                .icon(Utils.bitmapDescriptorFromVector(this, R.drawable.ic_m_location_new)
                                ).draggable(false)
                        )
                        val cameraPosition = CameraPosition.Builder().target(currentLocation).zoom(
                            14f
                        ).build()
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                        model.latLong = "${currentLocation.latitude},${currentLocation.longitude}"
                        Timber.e("getLocation")
                    }
                } catch (e: Exception) {
                    Alerts.dismissProgressBar()
                    Alerts.showSnackBar(this@MainActivity, getString(R.string.something_went_wrong))
                }
            }, { throwable ->
                Timber.e("Throwable :  $throwable")
                Alerts.showSnackBar(this@MainActivity, getString(R.string.something_went_wrong))
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        // dispose disposable instance
        if(rxLocationDisposable != null && !rxLocationDisposable?.isDisposed!!)
           rxLocationDisposable?.dispose()
    }


    override fun onBackPressed() {
        if (backPressedTime + 2500 > System.currentTimeMillis()) {
            super.onBackPressed()
            ActivityCompat.finishAffinity(this)
        } else {
            Alerts.showSnackBar(
                this@MainActivity, resources.getString(R.string.double_press_exit)
            )
        }
        backPressedTime = System.currentTimeMillis()
    }

    // Recyclerview Adapter List Item click event.
    // Show Info window title and snippet.
    override fun onClick(view: View, position: Int, item: NearByPlaceResponse.Item) {

        for (marker in markers) {
            if (marker.tag == item.venue.id)
            {
                val currentLocation = LatLng(item.venue.location.lat, item.venue.location.lng)
                val cameraPosition = CameraPosition.Builder().target(currentLocation).zoom(16f).build()
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                marker.snippet = "${item.venue.location.address}, ${item.venue.location.crossStreet}, ${item.venue.location.city}"
                marker.showInfoWindow()
            }
        }

    }
}