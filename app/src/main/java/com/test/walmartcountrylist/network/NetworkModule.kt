package com.test.walmartcountrylist.network

import com.google.gson.GsonBuilder
import com.test.walmartcountrylist.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    val walmartNetworkService : WalmartNetworkService by lazy {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Constants.BASE_URL)
            .build()
        // Create Retrofit client
        return@lazy retrofit.create(WalmartNetworkService::class.java)
    }

}