package com.test.walmartcountrylist.view

import com.google.common.truth.Truth.assertThat
import com.test.walmartcountrylist.MainCoroutineRule
import com.test.walmartcountrylist.domain.CountryUseCase
import com.test.walmartcountrylist.domain.data.CountryUiModel
import com.test.walmartcountrylist.network.NetworkResult
import io.mockk.MockKAnnotations.init
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException


@ExperimentalCoroutinesApi
class CountryViewModelTest {

    // Subject under test
    private lateinit var viewModel: CountryViewModel

    @MockK
    private lateinit var useCase: CountryUseCase

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        init(this)
        viewModel = CountryViewModel(useCase)
    }

    @Test
    fun `load country successfully`() = runTest {

        Dispatchers.setMain(StandardTestDispatcher())

        // Arrange
        val mockCountry = CountryUiModel("Afghanistan", "AS", "AF", "Kabul", "")
        val mockResponse = NetworkResult.Success(listOf(mockCountry))

        coEvery { useCase.getCountries() } returns mockResponse

        val states = mutableListOf<CountryUiState>()
        val job = launch {
            viewModel.uiState.collect {
                states.add(it)
            }
        }

        // Act
        viewModel.loadCountries()

        // Assert
        val loadingUiState = viewModel.uiState.value as CountryUiState.Loading
        assertThat(loadingUiState.loading).isTrue()

        // Advance time to execute pending coroutines actions
        advanceUntilIdle()

        val resultUiState = viewModel.uiState.value as CountryUiState.Success
        assertThat(resultUiState.countries).hasSize(1)

        job.cancel()
    }


    @Test
    fun `load country with error`() = runTest {

        Dispatchers.setMain(StandardTestDispatcher())

        // Arrange
        val mockResponse = NetworkResult.Error<List<CountryUiModel>>(code = 500, message = "unknown error")

        coEvery { useCase.getCountries() } returns mockResponse

        val states = mutableListOf<CountryUiState>()
        val job = launch {
            viewModel.uiState.collect {
                states.add(it)
            }
        }

        // Act
        viewModel.loadCountries()

        // Assert
        val loadingUiState = viewModel.uiState.value as CountryUiState.Loading
        assertThat(loadingUiState.loading).isTrue()

        // Advance time to execute pending coroutines actions
        advanceUntilIdle()

        val resultUiState = viewModel.uiState.value as CountryUiState.Error
        assertThat(resultUiState.code == 500).isTrue()

        job.cancel()
    }



    @Test
    fun `load country with exception`() = runTest {

        Dispatchers.setMain(StandardTestDispatcher())

        // Arrange
        val mockResponse = NetworkResult.Exception<List<CountryUiModel>>(IOException("failed to read"))

        coEvery { useCase.getCountries() } returns mockResponse

        val states = mutableListOf<CountryUiState>()
        val job = launch {
            viewModel.uiState.collect {
                states.add(it)
            }
        }

        // Act
        viewModel.loadCountries()

        // Assert
        val loadingUiState = viewModel.uiState.value as CountryUiState.Loading
        assertThat(loadingUiState.loading).isTrue()

        // Advance time to execute pending coroutines actions
        advanceUntilIdle()

        val resultUiState = viewModel.uiState.value as CountryUiState.Exception
        assertThat(resultUiState.e is IOException).isTrue()

        job.cancel()
    }
}
