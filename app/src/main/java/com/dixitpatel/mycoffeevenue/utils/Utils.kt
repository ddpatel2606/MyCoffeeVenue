package com.dixitpatel.mycoffeevenue.utils

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.inputmethod.InputMethodManager
import kotlin.jvm.JvmOverloads
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * Common utils methods
 */
class Utils
{
    companion object {

        fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
            val vectorDrawable =
                ContextCompat.getDrawable(context, vectorResId)
            vectorDrawable!!.setBounds(
                0,
                0,
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight
            )
            val bitmap = Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            vectorDrawable.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }


        fun getVersion(): String {
            val todayDate = Calendar.getInstance().time
            @SuppressLint("SimpleDateFormat") val formatter =
                SimpleDateFormat("yyyy-MM-dd")
            return formatter.format(todayDate).replace("-", "")
        }

        fun isNetworkAvailable(context: Context): Boolean
        {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            var isNetworkAvail = false
            try {
                val n = cm?.activeNetwork
                    val nc = cm?.getNetworkCapabilities(n)
                    isNetworkAvail = nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    return isNetworkAvail
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return isNetworkAvail
        }

        fun hideKeyboard(activity: Activity, flags: Int) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val viewWithFocus = activity.currentFocus
            val windowToken = viewWithFocus?.windowToken
            if (windowToken != null) {
                imm.hideSoftInputFromWindow(windowToken, flags)
            }
        }

        @JvmOverloads
        fun hideKeyboard(fragment: Fragment, flags: Int = InputMethodManager.HIDE_NOT_ALWAYS) {
            val activity: Activity? = fragment.activity
            if (activity != null) {
                hideKeyboard(activity, flags)
            }
        }

        fun showSoftKeyboard(me: Activity, edt: EditText?) {
            try {
                val imm = me.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}