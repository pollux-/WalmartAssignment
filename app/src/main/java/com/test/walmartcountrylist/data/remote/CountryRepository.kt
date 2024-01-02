package com.test.walmartcountrylist.data.remote

import com.test.walmartcountrylist.network.NetworkResult
import com.test.walmartcountrylist.network.model.CountryResponseItem

interface CountryRepository {

    suspend fun countryRepository() : NetworkResult<List<CountryResponseItem>>
}