package com.example.composingradio.utils

object Constants {

    const val BASE_RADIO_URL1 = "https://de1.api.radio-browser.info"

    const val BASE_RADIO_URL2 = "https://nl1.api.radio-browser.info"

    const val BASE_RADIO_URL3 = "https://at1.api.radio-browser.info"

    const val API_RADIO_SEARCH_URL = "/json/stations/search"

    const val BASE_RADIO_URL_PREF = "radio base url pref"

    val listOfUrls = listOf(BASE_RADIO_URL1, BASE_RADIO_URL2, BASE_RADIO_URL3)

    const val PAGE_SIZE = 10




    // Search preferences

    const val SEARCH_PREF_TAG = "search preferences tag"
    const val SEARCH_PREF_NAME = "search preferences name"
    const val SEARCH_PREF_COUNTRY = "search preferences country"
    const val SEARCH_FULL_COUNTRY_NAME = "full country name for no result message"
    const val SEARCH_PREF_ORDER = "search preference for order"
    const val SEARCH_PREF_MIN_BIT = "search pref for minimum bitrate"
    const val SEARCH_PREF_MAX_BIT = "search pref for maximum bitrate"
    const val IS_TAG_EXACT = "is tag exact"
    const val IS_NAME_EXACT = "is name exact"
    const val IS_SEARCH_FILTER_LANGUAGE = "is to filter by system language"

    const val ORDER_VOTES = "Top voted"
    const val ORDER_POP = "Most popular"
    const val ORDER_TREND = "Trending now"
    const val ORDER_RANDOM = "Random order"

    const val BITRATE_0 = 0
    const val BITRATE_24 = 24
    const val BITRATE_32 = 32
    const val BITRATE_64 = 64
    const val BITRATE_96 = 96
    const val BITRATE_128 = 128
    const val BITRATE_192 = 192
    const val BITRATE_256 = 256
    const val BITRATE_320 = 320
    const val BITRATE_MAX = 1000000






}