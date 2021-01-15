package com.dixitpatel.mycoffeevenue.binding

import android.app.Activity
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.dixitpatel.mycoffeevenue.utils.Alerts
import com.dixitpatel.mycoffeevenue.utils.CircularProgressView
import com.squareup.picasso.Picasso
import timber.log.Timber

/**
 *  This is View Binding class where Toast, Image view and Progress visibility update called from layout.
 */
object ViewBinding {

  @JvmStatic
  @BindingAdapter("toast")
  fun bindToast(view: View, text: String?) {
    text?.let {
      Alerts.showSnackBar(view.context as Activity, it)
    }
  }


  @JvmStatic
  @BindingAdapter("paletteImage")
  fun bindLoadImageView(view: AppCompatImageView, url: String) {
    Picasso.get()
      .load(url)
      .into(view)
  }

  @JvmStatic
  @BindingAdapter("gone")
  fun bindGone(view: CircularProgressView, shouldBeGone: Boolean) {
    Timber.e("timber Progress $shouldBeGone")
    view.visibility = if (shouldBeGone) {
      View.GONE
    } else {
      View.VISIBLE
    }
  }

}
