package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.dataBase.AsteroidRoom
import retrofit2.HttpException

class RefreshDataWork(appContext: Context, params:WorkerParameters) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWork"
    }

    override suspend fun doWork(): Result {
       val dataBase = AsteroidRoom.getInstance(applicationContext).asteroidDao
        val repository = AsteroidRepository(dataBase)

        return try {
            repository.deleteAllAsteroid()
            repository.deleteImages()
            repository.refreshAsteroid()
            repository.refreshImage()
            Result.success()
        }catch (e : HttpException){
            Result.retry()
        }
    }
}