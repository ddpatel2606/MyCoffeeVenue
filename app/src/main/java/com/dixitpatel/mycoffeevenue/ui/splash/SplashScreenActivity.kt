package com.dixitpatel.mycoffeevenue.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.PowerManager
import com.dixitpatel.mycoffeevenue.R
import com.dixitpatel.mycoffeevenue.databinding.ActivityMapsBinding
import com.dixitpatel.mycoffeevenue.ui.base.BaseActivity
import com.dixitpatel.mycoffeevenue.ui.main.MainActivity
import com.dixitpatel.mycoffeevenue.utils.Alerts
import com.dixitpatel.mycoffeevenue.utils.PermissionHelper
import com.dixitpatel.mycoffeevenue.utils.annotation.PermissionDenied
import com.dixitpatel.mycoffeevenue.utils.annotation.PermissionGranted
import timber.log.Timber
import javax.inject.Inject

/**
 *  Splash Activity Class : Access Permission to get current location.
 */
class SplashScreenActivity : BaseActivity<SplashScreenViewModel?>() {

    @Inject
    lateinit var model : SplashScreenViewModel

    override fun getViewModel(): SplashScreenViewModel {
        return model
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(PermissionHelper.instance.hasPermission(this@SplashScreenActivity,Manifest.permission.ACCESS_FINE_LOCATION))
        {
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        }
        else
        {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        PermissionHelper.instance.requestPermission(this@SplashScreenActivity, Manifest.permission.ACCESS_FINE_LOCATION)
    }


    @PermissionGranted(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    internal fun locationPermissionGranted() {
        Timber.e("ACCESS_FINE_LOCATION_GRANT")
        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
    }

    @PermissionDenied(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    internal fun locationPermissionDenied() {
        Timber.e("ACCESS_FINE_LOCATION_DENIED")
        Alerts.showBottomSheetSimpleConfirmationDialog(this@SplashScreenActivity,
            getString(R.string.alert),
            getString(R.string.denied_alert), false, getString(R.string.request_permission),
            getString(R.string.ok), object : Alerts.OnConfirmationDialog {
                override fun onYes() {
                    this@SplashScreenActivity.finish()
                }
                override fun onNo() {
                    requestLocationPermission()
                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionHelper.instance.onRequestPermissionsResult(this, permissions, grantResults)
    }
}