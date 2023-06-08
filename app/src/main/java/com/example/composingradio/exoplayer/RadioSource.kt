package com.example.composingradio.exoplayer

import android.content.SharedPreferences
import com.example.composingradio.data.remote.RadioApi
import com.example.composingradio.data.remote.entities.RadioStations
import com.example.composingradio.utils.Constants.API_RADIO_SEARCH_URL
import com.example.composingradio.utils.Constants.BASE_RADIO_URL3
import com.example.composingradio.utils.Constants.BASE_RADIO_URL_PREF
import com.example.composingradio.utils.Constants.listOfUrls
import javax.inject.Inject

class RadioSource @Inject constructor(
    private val radioApi: RadioApi,
    private val validBaseUrlPref : SharedPreferences
) {

    private var validBaseUrl = validBaseUrlPref.getString(BASE_RADIO_URL_PREF, BASE_RADIO_URL3)

    var stationsFromLastSearch: RadioStations? = RadioStations()


    private var initialBaseUrlIndex = listOfUrls.indexOf(validBaseUrl)

    private var currentUrlIndex = 0


    suspend fun getRadioStationsSource(
        country: String = "", language : String = "",
        tag: String = "", isTagExact : Boolean,
        name: String = "", isNameExact : Boolean, order : String,
        minBit : Int, maxBit : Int,
        offset: Int = 0, pageSize: Int

    ): RadioStations? {

        val tagExact = if(tag.isBlank()) false else isTagExact
        val nameExact = if(name.isBlank()) false else isNameExact

        val response = try {

            if(country != "") {
                radioApi.searchRadio(
                    country = country, language = language,
                    tag = tag, tagExact = tagExact,
                    name = name, nameExact = nameExact,
                    sortBy = order,
                    bitrateMin = minBit, bitrateMax = maxBit,
                    offset = offset, limit = pageSize,
                    url = "${validBaseUrl}$API_RADIO_SEARCH_URL"
                )
            } else {
                radioApi.searchRadioWithoutCountry(
                    language = language,
                    tag = tag, tagExact = tagExact,
                    name = name, nameExact = nameExact,
                    sortBy = order,
                    bitrateMin = minBit, bitrateMax = maxBit,
                    offset = offset, limit = pageSize,
                    url = "${validBaseUrl}$API_RADIO_SEARCH_URL"
                )
            }

        } catch (e : Exception){
            null
        }
        if(response == null) {

            for(i in currentUrlIndex until listOfUrls.size){

                if(i == initialBaseUrlIndex) {
                    currentUrlIndex++
                }
                else {
                    currentUrlIndex ++
                    validBaseUrl = listOfUrls[i]
                    return getRadioStationsSource(
                        country = country, language = language,
                        tag = tag, isTagExact = isTagExact,
                        name = name, isNameExact = isNameExact,
                        order = order,
                        minBit = minBit, maxBit = maxBit,
                        offset = offset,
                        pageSize = pageSize)
                }
            }
        }

        if(currentUrlIndex > 0){
            validBaseUrlPref.edit().putString(BASE_RADIO_URL_PREF, listOfUrls[currentUrlIndex-1]).apply()
            currentUrlIndex = 0
        }

        stationsFromLastSearch = response?.body()
        return response?.body()

    }



}