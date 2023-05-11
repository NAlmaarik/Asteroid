package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.ImageDomain
import com.udacity.asteroidradar.dataBase.AsteroidDao
import com.udacity.asteroidradar.dataBase.AsteroidRoom
import kotlinx.coroutines.launch

enum class FilterType {
    TODAY,
    WEEK,
    SAVED
}

class MainViewModel(dataBase: AsteroidDao, application: Application) :
    AndroidViewModel(application) {


    private val repository = AsteroidRepository(dataBase)

    var filterType = MutableLiveData(FilterType.SAVED)

    private val _loadingStatus = MutableLiveData<Boolean>(false)
    val loadingStatus: LiveData<Boolean>
        get() = _loadingStatus

    private val _navigateToDetails = MutableLiveData<Asteroid>()
    val navigateToDetails: LiveData<Asteroid>
        get() = _navigateToDetails

    init {
        _navigateToDetails.value = null
        viewModelScope.launch {
            _loadingStatus.value = true
            repository.refreshAsteroid()
            repository.refreshImage()
            _loadingStatus.value = false
        }

    }

    val imageOfTheDay = repository.imageOfTheDay

    var asteroids: LiveData<List<Asteroid>?> = Transformations.switchMap(filterType) {
        when (it) {
            FilterType.TODAY -> repository.getFilteredAsteroid(it)
            FilterType.WEEK -> repository.getFilteredAsteroid(it)
            else -> repository.getFilteredAsteroid(it)
        }
    }

    fun displayDetails(asteroid: Asteroid) {
        _navigateToDetails.value = asteroid
    }

    fun displayDetailsCompleted() {
        _navigateToDetails.value = null
    }


}