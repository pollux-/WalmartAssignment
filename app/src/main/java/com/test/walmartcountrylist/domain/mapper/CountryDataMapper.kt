package com.test.walmartcountrylist.domain.mapper

import com.test.walmartcountrylist.domain.data.CountryUiModel
import com.test.walmartcountrylist.network.NetworkResult
import com.test.walmartcountrylist.network.model.CountryResponseItem

class CountryDataMapper :
    DataMapper<NetworkResult<List<CountryResponseItem>>, NetworkResult<List<CountryUiModel>>> {
    override suspend fun map(input: NetworkResult<List<CountryResponseItem>>): NetworkResult<List<CountryUiModel>> {
        return when (input) {
            is NetworkResult.Success -> {
                val data = input.data.map {
                    CountryUiModel(
                        name = it.name.orEmpty(),
                        region = it.region.orEmpty(),
                        code = it.code.orEmpty(),
                        capital = it.capital.orEmpty(),
                        formattedName = it.name +", " + it.region
                    )
                }
                NetworkResult.Success(data)
            }

            is NetworkResult.Error -> NetworkResult.Error(
                code = input.code,
                message = input.message
            )

            is NetworkResult.Exception -> NetworkResult.Exception(input.e)
        }


    }
}
