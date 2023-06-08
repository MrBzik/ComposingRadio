package com.example.composingradio.ui.navigation

import com.example.composingradio.R

const val SCREEN_SEARCH = "search window"
const val SCREEN_FAV = "fav window"
const val SCREEN_HISTORY = "history window"
const val SCREEN_REC = "rec window"
const val SCREEN_SETTINGS = "settings window"


sealed class BottomBarScreen(
    val route : String,
    val title : String,
    val icon : Int
) {

    object Search : BottomBarScreen(
        SCREEN_SEARCH,
        "Search",
        R.drawable.ic_search
    )
    object Favourite : BottomBarScreen(
        SCREEN_FAV,
        "Fav.",
        R.drawable.ic_favourite
    )
    object History : BottomBarScreen(
        SCREEN_HISTORY,
        "History",
        R.drawable.ic_history
    )
    object Recording : BottomBarScreen(
        SCREEN_REC,
        "Rec.",
        R.drawable.ic_recording
    )
    object Settings : BottomBarScreen(
        SCREEN_SETTINGS,
        "Options",
        R.drawable.ic_settings
    )


}