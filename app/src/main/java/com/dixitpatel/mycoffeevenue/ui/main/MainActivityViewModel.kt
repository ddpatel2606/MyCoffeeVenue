package com.dixitpatel.mycoffeevenue.ui.main

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.dixitpatel.mycoffeevenue.R
import com.dixitpatel.mycoffeevenue.application.MyApplication
import com.dixitpatel.mycoffeevenue.constant.LAT_LONG_STATIC
import com.dixitpatel.mycoffeevenue.model.NearByPlaceResponse
import com.dixitpatel.mycoffeevenue.network.ApiInterface
import com.dixitpatel.mycoffeevenue.repository.MainViewRepository
import com.dixitpatel.mycoffeevenue.ui.base.LiveCoroutinesViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 *  Main Activity ViewModel : ViewModel
 */
class MainActivityViewModel @Inject constructor(private val apiInterface: ApiInterface, private val mainViewRepository: MainViewRepository) : LiveCoroutinesViewModel()
{
    var placesListLiveData: LiveData<List<NearByPlaceResponse.Item?>>

    var latLong : String = LAT_LONG_STATIC

    private val _toastLiveData: MutableLiveData<String> = MutableLiveData()
    val toastLiveData: LiveData<String> get() = _toastLiveData

    val isLoading: ObservableBoolean = ObservableBoolean(false)

    init
    {
        Timber.d("init MainViewModel")
        isLoading.set(true)
            placesListLiveData = launchOnViewModelScope {
                this.mainViewRepository.getVenueApiCall(apiInterface = apiInterface,
                    latLng = latLong,
                    onSuccess = {
                        isLoading.set(false)
                    },
                    onError = {

                        isLoading.set(false)
                        if(it!!.contains("UnknownHostException"))
                        {
                            _toastLiveData.postValue(MyApplication.instance.getString(R.string.internet_not_available))
                        }else
                        {
                            _toastLiveData.postValue(it)
                        }

                    }
                ).asLiveData()
            }
    }
}