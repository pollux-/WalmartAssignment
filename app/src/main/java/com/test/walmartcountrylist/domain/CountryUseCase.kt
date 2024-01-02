package com.test.walmartcountrylist.domain

import com.test.walmartcountrylist.domain.data.CountryUiModel
import com.test.walmartcountrylist.network.NetworkResult

interface CountryUseCase {
    suspend fun getCountries() : NetworkResult<List<CountryUiModel>>
}