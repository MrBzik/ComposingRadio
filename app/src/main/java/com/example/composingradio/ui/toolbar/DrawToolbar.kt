package com.example.composingradio.ui.toolbar



import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.hilt.navigation.compose.hiltViewModel

import com.example.composingradio.ui.viewmodels.NavViewModel

@Composable
fun DrawToolbarBG(navViewModel : NavViewModel = hiltViewModel()){
    val state by  navViewModel.toolbarState.observeAsState()

    when(state){

        0 -> {

            SearchToolbar()

        }
        else -> {


        }

    }


}