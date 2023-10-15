package com.example.composingradio.ui.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composingradio.ui.viewmodels.DialogsViewModel
import com.example.composingradio.utils.DialogCalls

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DialogsCaller(
    dialogsViewModel: DialogsViewModel = hiltViewModel()
){

    val state = dialogsViewModel.dialogsState.collectAsState()


    if(state.value != DialogCalls.NO_DIALOG)
        Box (modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = {dialogsViewModel.updateDialogsState(DialogCalls.NO_DIALOG)})
            .background(Color.Black.copy(alpha = 0.3f)))


    AnimatedVisibility(
        visible = state.value != DialogCalls.NO_DIALOG,
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut()
    ) {

        when(state.value){

          DialogCalls.TAGS_DIALOG -> {

              TagsDialog()

            }

            else -> {}
        }
    }

}