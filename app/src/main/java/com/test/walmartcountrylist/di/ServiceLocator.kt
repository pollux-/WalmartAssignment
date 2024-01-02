package com.test.walmartcountrylist.di

import com.test.walmartcountrylist.data.remote.CountryRepository
import com.test.walmartcountrylist.data.remote.CountryRepositoryImpl
import com.test.walmartcountrylist.domain.CountryUseCase
import com.test.walmartcountrylist.domain.CountryUseCaseImpl
import com.test.walmartcountrylist.domain.mapper.CountryDataMapper
import com.test.walmartcountrylist.network.NetworkModule

object ServiceLocator {

    private fun provideCountryRepository(): CountryRepository {
        return CountryRepositoryImpl(NetworkModule.walmartNetworkService)

    }

    fun provideCountryUseCase(): CountryUseCase {
        val mapper = CountryDataMapper()
        val repo = provideCountryRepository()
        return CountryUseCaseImpl(repo, mapper)

    }
}