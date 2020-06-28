package com.himanshurawat.nasa.network

import com.himanshurawat.nasa.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {

        private var INSTANCE: Retrofit? = null

        fun getClient(): Retrofit{
            if(INSTANCE == null){
                INSTANCE = Retrofit
                    .Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                return INSTANCE as Retrofit
            }

            return INSTANCE as Retrofit
        }
    }
}