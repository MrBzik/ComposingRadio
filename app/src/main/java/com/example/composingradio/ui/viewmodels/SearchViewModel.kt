package com.example.composingradio.ui.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.composingradio.data.local.entities.RadioStation
import com.example.composingradio.data.remote.entities.RadioStations
import com.example.composingradio.datasource.RadioSearchDataSource
import com.example.composingradio.datasource.StationsPageLoader
import com.example.composingradio.exoplayer.RadioSource
import com.example.composingradio.extensions.toRadioStation
import com.example.composingradio.utils.Constants.BITRATE_0
import com.example.composingradio.utils.Constants.BITRATE_MAX
import com.example.composingradio.utils.Constants.IS_NAME_EXACT
import com.example.composingradio.utils.Constants.IS_SEARCH_FILTER_LANGUAGE
import com.example.composingradio.utils.Constants.IS_TAG_EXACT
import com.example.composingradio.utils.Constants.ORDER_POP
import com.example.composingradio.utils.Constants.ORDER_TREND
import com.example.composingradio.utils.Constants.ORDER_VOTES
import com.example.composingradio.utils.Constants.PAGE_SIZE
import com.example.composingradio.utils.Constants.SEARCH_FULL_COUNTRY_NAME
import com.example.composingradio.utils.Constants.SEARCH_PREF_COUNTRY
import com.example.composingradio.utils.Constants.SEARCH_PREF_MAX_BIT
import com.example.composingradio.utils.Constants.SEARCH_PREF_MIN_BIT
import com.example.composingradio.utils.Constants.SEARCH_PREF_NAME
import com.example.composingradio.utils.Constants.SEARCH_PREF_ORDER
import com.example.composingradio.utils.Constants.SEARCH_PREF_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    app : Application,
    val radioSource: RadioSource
) : AndroidViewModel(app) {


    var lastScrollIndex = 0


    val searchPreferences = app.getSharedPreferences("SearchPref", Context.MODE_PRIVATE)




    var lastSearchCountry = searchPreferences.getString(SEARCH_PREF_COUNTRY, "") ?: ""
    var lastSearchName = searchPreferences.getString(SEARCH_PREF_NAME, "")?: ""
    var lastSearchTag = searchPreferences.getString(SEARCH_PREF_TAG, "")?: ""
    var searchFullCountryName = searchPreferences.getString(SEARCH_FULL_COUNTRY_NAME, "")?: ""


    val searchParamTag : MutableLiveData<String> = MutableLiveData(lastSearchTag)
    val searchParamName : MutableLiveData<String> = MutableLiveData(lastSearchName)
    val searchParamCountry : MutableLiveData<String> = MutableLiveData(lastSearchCountry)


    var isTagExact = searchPreferences.getBoolean(IS_TAG_EXACT, false)
    var isNameExact = searchPreferences.getBoolean(IS_NAME_EXACT, false)
    var wasTagExact = isTagExact
    var wasNameExact = isNameExact

    var oldSearchOrder = searchPreferences.getString(SEARCH_PREF_ORDER, ORDER_POP) ?: ORDER_POP
    var newSearchOrder = oldSearchOrder

    var minBitrateOld = searchPreferences.getInt(SEARCH_PREF_MIN_BIT, BITRATE_0)
    var minBitrateNew = minBitrateOld

    var maxBitrateOld = searchPreferences.getInt(SEARCH_PREF_MAX_BIT, BITRATE_MAX)
    var maxBitrateNew = maxBitrateOld

    var isSearchFilterLanguage = searchPreferences.getBoolean(IS_SEARCH_FILTER_LANGUAGE, false)
    var wasSearchFilterLanguage = isSearchFilterLanguage


    val searchLoadingState : MutableLiveData<Boolean> = MutableLiveData()
    var searchJob : Job = SupervisorJob()
    var noResultDetection : MutableLiveData<Boolean> = MutableLiveData()

    var isNewSearch = true

    private suspend fun searchWithNewParams(

        limit : Int, offset : Int) : List<RadioStation> {

        searchLoadingState.postValue(true)

        val calcOffset = limit * offset

//                var isReversedOrder = true


        val orderSetting = when(newSearchOrder){
            ORDER_VOTES -> "votes"
            ORDER_POP -> "clickcount"
            ORDER_TREND -> "clicktrend"
            else -> "random"
        }

        val lang = if(isSearchFilterLanguage) Locale.getDefault().isO3Language
        else ""

        var response : RadioStations? = null
        var listOfStations = emptyList<RadioStation>()

        searchJob = viewModelScope.launch {
            while(true){

//                   Log.d("CHECKTAGS", "search is looping")

                response = radioSource.getRadioStationsSource(
                    offset = calcOffset,
                    pageSize = limit,
                    country = lastSearchCountry,
                    language = lang,
                    tag = lastSearchTag,
                    isTagExact = isTagExact,
                    name = lastSearchName,
                    isNameExact = isNameExact,
                    order = orderSetting,
                    minBit = minBitrateNew,
                    maxBit = maxBitrateNew,

                    )

                if(response == null) {

//                    hasInternetConnection.postValue(false)

                    delay(1000)
                }

                else {

//                    hasInternetConnection.postValue(true)

                    if(isNewSearch && response?.size == 0){
                        noResultDetection.postValue(true)
                    } else {
                        noResultDetection.postValue(false)
                    }

                    listOfStations = response?.let {

                        it.map { station ->

                            station.toRadioStation()
                        }
                    } ?: emptyList()



//                    while(!RadioServiceConnection.isConnected){
//                        Log.d("CHECKTAGS", "not connected")
//                        delay(50)
//                    }
//
//                    radioServiceConnection.sendCommand(COMMAND_NEW_SEARCH,
//                        bundleOf(Pair(IS_NEW_SEARCH, isNewSearch))
//                    )

                    isNewSearch = false

                    searchLoadingState.postValue(false)

                    break
                }
            }
        }

        searchJob.join()

        return listOfStations

    }


    var searchList = flowOf<PagingData<RadioStation>>()

    val searchBy = MutableLiveData(true)


    init {
        searchBy.observeForever {

            searchList = searchStationsPaging().cachedIn(viewModelScope)

        }
    }




     fun searchStationsPaging(): Flow<PagingData<RadioStation>> {
        val loader : StationsPageLoader = { pageIndex, pageSize ->
            searchWithNewParams(pageSize, pageIndex)
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                RadioSearchDataSource(loader, PAGE_SIZE)
            }
        ).flow
    }


    fun initiateNewSearch() : Boolean {


        if(
            lastSearchName == searchParamName.value &&
            lastSearchTag == searchParamTag.value &&
            lastSearchCountry == searchParamCountry.value &&
            wasTagExact == isTagExact &&
            wasNameExact == isNameExact &&
            newSearchOrder == oldSearchOrder &&
            minBitrateNew == minBitrateOld &&
            maxBitrateNew == maxBitrateOld &&
            isSearchFilterLanguage == wasSearchFilterLanguage

        )
            return false

        return true.also {
            isNewSearch = true
            lastSearchName = searchParamName.value ?: ""
            lastSearchTag = searchParamTag.value ?: ""
            lastSearchCountry = searchParamCountry.value ?: ""
            wasTagExact = isTagExact
            wasNameExact = isNameExact
            oldSearchOrder = newSearchOrder
            minBitrateOld = minBitrateNew
            maxBitrateOld = maxBitrateNew
            wasSearchFilterLanguage = isSearchFilterLanguage

            if(searchJob.isActive){
                searchJob.cancel()
            }



        }
    }



}