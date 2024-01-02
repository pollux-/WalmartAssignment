package com.test.walmartcountrylist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.walmartcountrylist.R
import kotlinx.coroutines.launch

class CountryFragment : Fragment() {


    private val viewModel: CountryViewModel by viewModels { CountryViewModel.Factory }
    private lateinit var countryListAdapter: CountryListAdapter
    private lateinit var progress: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_country_feed, container, false)

        val rv = view.findViewById<RecyclerView>(R.id.countryList)
        progress = view.findViewById(R.id.progress)
        countryListAdapter = CountryListAdapter()

        rv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = countryListAdapter
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadCountries()
        // Create a new coroutine in the lifecycleScope
        viewLifecycleOwner.lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                // This happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                viewModel.uiState.collect {
                    when (it) {
                        is CountryUiState.Error -> {
                            Toast.makeText(
                                context,
                                getString(R.string.error_message),
                                Toast.LENGTH_LONG
                            ).show()
                            progress.visibility = View.GONE
                        }

                        is CountryUiState.Exception -> {
                            Toast.makeText(
                                context,
                                getString(R.string.error_message),
                                Toast.LENGTH_LONG
                            ).show()
                            progress.visibility = View.GONE
                        }

                        is CountryUiState.Loading -> {
                            progress.visibility = if (it.loading) View.VISIBLE else View.GONE
                        }

                        is CountryUiState.Success -> {
                            countryListAdapter.submitList(it.countries)
                            progress.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }


}

