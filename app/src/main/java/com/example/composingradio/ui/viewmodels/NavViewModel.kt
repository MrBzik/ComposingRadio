package com.example.composingradio.ui.viewmodels

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class NavViewModel : ViewModel() {

    val toolbarState : MutableLiveData<Int> = MutableLiveData(0)

    fun updateToolbarState(state : Int){

        toolbarState.value = state
    }


}