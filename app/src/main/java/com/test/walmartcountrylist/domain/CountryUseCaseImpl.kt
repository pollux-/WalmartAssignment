package com.test.walmartcountrylist.domain

import com.test.walmartcountrylist.data.remote.CountryRepository
import com.test.walmartcountrylist.domain.data.CountryUiModel
import com.test.walmartcountrylist.domain.mapper.DataMapper
import com.test.walmartcountrylist.network.NetworkResult
import com.test.walmartcountrylist.network.model.CountryResponseItem

class CountryUseCaseImpl(
    private val repository: CountryRepository,
    private val mapper: DataMapper<NetworkResult<List<CountryResponseItem>>, NetworkResult<List<CountryUiModel>>>,
) : CountryUseCase {
    override suspend fun getCountries(): NetworkResult<List<CountryUiModel>> {
        val response = repository.countryRepository()
        return mapper.map(response)
    }
}