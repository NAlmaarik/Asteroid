package com.udacity.asteroidradar

import android.util.Log
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Constants.API_QUERY_DATE_FORMAT
import com.udacity.asteroidradar.Constants.DEFAULT_END_DATE_DAYS
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.dataBase.AsteroidDao
import com.udacity.asteroidradar.dataBase.AsteroidRoom
import com.udacity.asteroidradar.main.FilterType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AsteroidRepository(val dataBase : AsteroidDao) {

    val imageOfTheDay : LiveData<ImageDomain> = dataBase.getImage()

    suspend fun refreshAsteroid(){
        val currentDate = getFormattedCurrentDate()
        withContext(Dispatchers.IO){
        val jsonResult = AsteroidApi.retrofitService.getAllAsteroid(currentDate,BuildConfig.API_KEY)
        dataBase.insertAll(parseAsteroidsJsonResult(JSONObject(jsonResult)))
        }
    }

     fun getFilteredAsteroid(filterType: FilterType) : LiveData<List<Asteroid>>{
        return when(filterType){
            FilterType.TODAY -> dataBase.getTodayAsteroid(getFormattedCurrentDate())
            FilterType.WEEK -> dataBase.getWeekAsteroid(getFormattedCurrentDate())
            else -> dataBase.getAllAsteroid()
        }
    }

    suspend fun deleteAllAsteroid(){
        dataBase.deleteAll()
    }


    suspend fun refreshImage(){
        withContext(Dispatchers.IO) {
            val result = AsteroidApi.retrofitService.getImageOfTheDay(BuildConfig.API_KEY)
            dataBase.insertImage(newtworkToDomainModel(result))
        }
    }

    suspend fun deleteImages(){
        dataBase.deleteAllImages()
    }
}