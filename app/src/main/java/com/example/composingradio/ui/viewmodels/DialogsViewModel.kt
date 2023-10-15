package com.example.composingradio.ui.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.composingradio.utils.DialogCalls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DialogsViewModel : ViewModel() {

    private val _dialogsState = MutableStateFlow(DialogCalls.NO_DIALOG)

    val dialogsState = _dialogsState.asStateFlow()

    fun updateDialogsState(state : DialogCalls){
        _dialogsState.value = state
    }

}