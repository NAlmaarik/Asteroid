package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.dataBase.AsteroidDao
import com.udacity.asteroidradar.dataBase.AsteroidRoom
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.getFormattedCurrentDate

class MainFragment : Fragment() {

    private val application: Application by lazy {
        requireNotNull(this.activity).application
    }

    private val dataBase: AsteroidDao by lazy {
        AsteroidRoom.getInstance(application).asteroidDao
    }

    private val viewModelFactory: MainViewModelFactory by lazy {
        MainViewModelFactory(dataBase, application)
    }
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            viewModel.displayDetails(it)
        })

        setHasOptionsMenu(true)


        viewModel.navigateToDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                val action = MainFragmentDirections.actionShowDetail(it)
                findNavController().navigate(action)
                viewModel.displayDetailsCompleted()
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.filterType.value = when (item.itemId) {
            R.id.show_today_menu -> FilterType.TODAY
            R.id.show_week_menu -> FilterType.WEEK
            else -> FilterType.SAVED
        }

        return true
    }
}
