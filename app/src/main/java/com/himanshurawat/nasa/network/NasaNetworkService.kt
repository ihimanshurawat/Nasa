package com.himanshurawat.nasa.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaNetworkService {

    @GET("")
    fun getMediaOfTheDay(@Query("api_key")key: String , @Query("date")date: String): Call<NasaResponse>

}