package com.dixitpatel.mycoffeevenue.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

/**
 * LiveCoroutinesViewModel : extened by all ViewModel class which can call
 * coroutine function and send live data
 */
abstract class LiveCoroutinesViewModel : ViewModel() {

  inline fun <T> launchOnViewModelScope(crossinline block: suspend () -> LiveData<T>): LiveData<T> {

    return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
      emitSource(block())
    }
  }
}
