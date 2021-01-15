package com.dixitpatel.mycoffeevenue.network

import com.dixitpatel.mycoffeevenue.model.NearByPlaceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *  All Network Calling Apis are define here with Coroutine function.
 */
interface ApiInterface {

    @GET("/v2/venues/explore?")
    suspend fun getVenueResults(@Query("client_id") client_id: String,
                           @Query("client_secret") client_secret: String,
                           @Query("v") vDate: String,
                           @Query("section") section: String,
                           @Query("ll") latLng: String): Response<NearByPlaceResponse>

}