package com.example.composingradio.extensions

import com.example.composingradio.data.local.entities.RadioStation
import com.example.composingradio.data.remote.entities.RadioStationsItem

fun RadioStationsItem.toRadioStation() : RadioStation {
    val country = when (countrycode) {
        "US" -> "USA"
        "GB" -> "UK"
        "RU" -> "Russia"
        else -> country
    }


    return RadioStation(
        favicon = favicon,
        name = name,
        stationuuid = stationuuid,
        country = country,
        url = url_resolved,
        homepage = homepage,
        tags = tags,
        language = language,
        favouredAt = 0,
        state = state,
        bitrate = bitrate
    )

}