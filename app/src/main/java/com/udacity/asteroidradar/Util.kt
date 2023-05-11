package com.udacity.asteroidradar

import com.udacity.asteroidradar.api.ImageResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getFormattedCurrentDate(): String{
    return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
}

fun newtworkToDomainModel(imageResponse: ImageResponse) : ImageDomain{
    return ImageDomain(
        url = imageResponse.url,
        media_type = checkMediaType(imageResponse.media_type),
        title = imageResponse.title
    )
}

private fun checkMediaType(mediaType: String) : String?{
    return if(mediaType == "image")
        mediaType
    else
        null
}