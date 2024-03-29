package com.test.walmartcountrylist.network

import com.test.walmartcountrylist.network.model.CountryResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface WalmartNetworkService {

    @GET("peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json")
    suspend fun getCountries(): Response<List<CountryResponseItem>>


}