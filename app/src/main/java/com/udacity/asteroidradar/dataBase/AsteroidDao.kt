package com.udacity.asteroidradar.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.ImageDomain

@Dao
interface AsteroidDao {

    @Insert(onConflict = REPLACE)
     fun insertAll(asterois : List<Asteroid>)

     @Query("SELECT * from asteroid ORDER BY closeApproachDate asc")
     fun getAllAsteroid() : LiveData<List<Asteroid>>

     @Query("SELECT * from asteroid where closeApproachDate = :startDate ORDER BY closeApproachDate asc")
     fun getTodayAsteroid(startDate : String) : LiveData<List<Asteroid>>

     @Query("SELECT * from asteroid where closeApproachDate > :startDate ORDER BY closeApproachDate asc")
     fun getWeekAsteroid(startDate : String) : LiveData<List<Asteroid>>

     @Query("Delete from Asteroid")
     fun deleteAll()

     @Insert(onConflict = REPLACE)
     fun insertImage(imageDomain: ImageDomain)

     @Query("Select * from ImageDomain")
     fun getImage() : LiveData<ImageDomain>

     @Query("Delete From ImageDomain")
     fun deleteAllImages()

}