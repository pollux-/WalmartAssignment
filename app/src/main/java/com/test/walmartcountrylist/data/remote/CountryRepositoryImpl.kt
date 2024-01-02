package com.test.walmartcountrylist.data.remote

import com.test.walmartcountrylist.network.NetworkResult
import com.test.walmartcountrylist.network.WalmartNetworkService
import com.test.walmartcountrylist.network.handleApi
import com.test.walmartcountrylist.network.model.CountryResponseItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountryRepositoryImpl(
    private val service: WalmartNetworkService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CountryRepository {
    override suspend fun countryRepository(): NetworkResult<List<CountryResponseItem>> {
        return withContext(dispatcher) {
            handleApi { service.getCountries() }
        }
    }
}