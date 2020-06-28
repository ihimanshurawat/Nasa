package com.himanshurawat.nasa.ui

import com.himanshurawat.nasa.network.NasaNetworkService
import com.himanshurawat.nasa.network.NasaResponse
import com.himanshurawat.nasa.network.RetrofitClient
import com.himanshurawat.nasa.utils.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivityModel(private val presenter: MainActivityContracts.Presenter): MainActivityContracts.Model {

    override fun getData(date: String) {
        /*
        fetching data for the given date
         */
        RetrofitClient
            .getClient()
            .create(NasaNetworkService::class.java)
            .getMediaOfTheDay(API,date)
            .enqueue(object: Callback<NasaResponse>{
            override fun onFailure(call: Call<NasaResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<NasaResponse>, response: Response<NasaResponse>) {
                if (response.isSuccessful && response.body() != null){
                    val res = response.body() as NasaResponse
                    presenter.populateData(res)
                }
            }

        })
    }
}