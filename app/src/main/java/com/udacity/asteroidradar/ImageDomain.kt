package com.udacity.asteroidradar

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageDomain(
    @PrimaryKey
    val url: String,
    val media_type: String?,
    val title: String
)
