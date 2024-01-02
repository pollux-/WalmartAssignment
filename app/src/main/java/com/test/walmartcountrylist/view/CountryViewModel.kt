package com.test.walmartcountrylist.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.test.walmartcountrylist.di.ServiceLocator
import com.test.walmartcountrylist.domain.CountryUseCase
import com.test.walmartcountrylist.domain.data.CountryUiModel
import com.test.walmartcountrylist.network.NetworkResult
import com.test.walmartcountrylist.util.WhileUiSubscribed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class CountryViewModel(private val useCase: CountryUseCase) : ViewModel() {

    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val useCase = ServiceLocator.provideCountryUseCase()
                CountryViewModel(
                    useCase
                )
            }
        }
    }


    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<CountryUiState>(CountryUiState.Success(emptyList()))

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<CountryUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = CountryUiState.Loading(loading = true)
    )

    fun loadCountries(force: Boolean = false) {
        if (force.not() && uiState.value is CountryUiState.Success) {
            return
        }
        viewModelScope.launch {
            _uiState.value = CountryUiState.Loading(true)
           val result =  when (val response = useCase.getCountries()) {
                is NetworkResult.Success ->  CountryUiState.Success(response.data)
                is NetworkResult.Error ->CountryUiState.Error(response.code, response.message)
                is NetworkResult.Exception -> CountryUiState.Exception(response.e)
            }
            _uiState.value = CountryUiState.Loading(false)
            _uiState.value = result
        }
    }


}


// Represents different states
sealed class CountryUiState {
    data class Success(val countries: List<CountryUiModel>) : CountryUiState()
    data class Error(val code: Int, val message: String?) : CountryUiState()
    data class Exception(val e: Throwable) : CountryUiState()
    data class Loading(val loading: Boolean = false) : CountryUiState()
}
