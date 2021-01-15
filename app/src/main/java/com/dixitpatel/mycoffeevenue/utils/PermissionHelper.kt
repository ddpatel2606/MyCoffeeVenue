package com.dixitpatel.mycoffeevenue.utils

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dixitpatel.mycoffeevenue.utils.annotation.PermissionDenied
import com.dixitpatel.mycoffeevenue.utils.annotation.PermissionGranted
import java.lang.reflect.InvocationTargetException

/**
 * A helper for checking and requesting permissions for app that targeting Android M (API >= 23)
 *
 */
class PermissionHelper {

    companion object {
        private const val TAG = "PermissionHelper"
        val instance = PermissionHelper()
    }

    /**
     * Return if the context has the permission.
     *
     * @param context
     * @param permission
     * @return
     */
    fun hasPermission(context: Context?, permission: String?): Boolean {
        return ContextCompat.checkSelfPermission(
            context!!,
            permission!!
        ) == PackageManager.PERMISSION_GRANTED
    }
    /**
     * Request the permission.
     *
     * @param context
     * @param permission
     * @param rationale
     */
    /**
     * Request the permission.
     *
     * @param context
     * @param permission
     */
    @JvmOverloads
    fun requestPermission(context: Activity, permission: String, rationale: String? = null) {
        if (hasPermission(context, permission)) {
            invokeGrantedMethod(context, permission)
        } else if (!TextUtils.isEmpty(rationale) && !ActivityCompat.shouldShowRequestPermissionRationale(
                context,
                permission
            )
        ) {
            showRequestPermissionRationale(context, permission, rationale)
        } else {
            ActivityCompat.requestPermissions(context, arrayOf(permission), 0)
        }
    }

    /**
     * Show UI with rationale for requesting a permission.
     *
     * @param activity
     * @param permission
     * @param rationale
     */
    private fun showRequestPermissionRationale(
        activity: Activity,
        permission: String,
        rationale: String?
    ) {
        val dialog = AlertDialog.Builder(activity)
            .setMessage(rationale)
            .setPositiveButton(R.string.ok) { dialog, which ->
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    0
                )
            }
            .setNegativeButton(R.string.cancel, null).create()
        dialog.show()
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on [.requestPermission].
     *
     * @param obj
     * @param permissions
     * @param grantResults
     */
    fun onRequestPermissionsResult(obj: Any, permissions: Array<String>, grantResults: IntArray) {
        if (isGranted(grantResults)) {
            invokeGrantedMethod(obj, permissions[0])
        } else {
            invokeDeniedMethod(obj, permissions[0])
        }
    }

    private fun isGranted(grantResults: IntArray): Boolean {
        return grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Invoke a method with annotation PermissionGranted.
     *
     * @param obj
     * @param permission
     */
    private fun invokeGrantedMethod(obj: Any, permission: String) {
        val clazz: Class<*> = obj.javaClass
        var permissionGranted: PermissionGranted
        for (method in clazz.methods) {
            if (method.isAnnotationPresent(PermissionGranted::class.java)) {
                permissionGranted = method.getAnnotation(PermissionGranted::class.java)
                if (permissionGranted.permission == permission) {
//                    if (method.getModifiers() != Modifier.PUBLIC) {
//                        throw new IllegalArgumentException(String.format("Annotation method %s must be public.", method));
//                    }
                    if (method.parameterTypes.isNotEmpty()) {
                        throw RuntimeException(
                            String.format(
                                "Cannot execute non-void method %s.",
                                method
                            )
                        )
                    }
                    try {
                        method.invoke(obj)
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    } catch (e: InvocationTargetException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    /**
     * Invoke a method with annotation PermissionDenied.
     *
     * @param obj
     * @param permission
     */
    private fun invokeDeniedMethod(obj: Any, permission: String) {
        val clazz: Class<*> = obj.javaClass
        var permissionDenied: PermissionDenied
        for (method in clazz.methods) {
            if (method.isAnnotationPresent(PermissionDenied::class.java)) {
                permissionDenied = method.getAnnotation(PermissionDenied::class.java)
                if (permissionDenied.permission == permission) {
//                    if (method.getModifiers() != Modifier.PUBLIC) {
//                        throw new IllegalArgumentException(String.format("Annotation method %s must be public.", method));
//                    }
                    if (method.parameterTypes.isNotEmpty()) {
                        throw RuntimeException(
                            String.format(
                                "Cannot execute non-void method %s.",
                                method
                            )
                        )
                    }
                    try {
                        method.invoke(obj)
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    } catch (e: InvocationTargetException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

}