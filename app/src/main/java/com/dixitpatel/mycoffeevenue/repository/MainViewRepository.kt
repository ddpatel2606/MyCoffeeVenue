package com.dixitpatel.mycoffeevenue.repository

import androidx.annotation.WorkerThread
import com.dixitpatel.mycoffeevenue.constant.CLIENT_ID
import com.dixitpatel.mycoffeevenue.constant.CLIENT_SECRET
import com.dixitpatel.mycoffeevenue.constant.SEARCH_SECTION
import com.dixitpatel.mycoffeevenue.model.NearByPlaceResponse
import com.dixitpatel.mycoffeevenue.network.ApiInterface
import com.dixitpatel.mycoffeevenue.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

/**
 *  MainViewRepository class to call Main places API.
 */
class MainViewRepository @Inject constructor() : Repository
{

    // Venue listing API
    @WorkerThread
    suspend fun getVenueApiCall(apiInterface : ApiInterface,
                                latLng: String,
                                onSuccess: () -> Unit,
                                onError: (String?) -> Unit)
             = flow {
                try {
                    val response : Response<NearByPlaceResponse>
                    = apiInterface.getVenueResults(CLIENT_ID, CLIENT_SECRET, Utils.getVersion(),SEARCH_SECTION,latLng)
                    if(response.isSuccessful)
                    {
                       response.body()?.let {
                           if(it.meta.code == 200) {
                               emit(it.response.groups[0].items)
                               onSuccess()
                           }else {
                               onError(it.meta.errorDetail)
                               Timber.e(response.errorBody().toString())
                       }
                       }
                    }else
                    {
                        onError(response.errorBody().toString())
                        Timber.e(response.errorBody().toString())
                    }
                } catch (e: UnknownHostException) {
                    onError(e.toString())
                    Timber.e(e.toString())
                } catch (e: Exception) {
                    onError(e.toString())
                    Timber.e(e.toString())
                }
    }.flowOn(Dispatchers.IO)
}