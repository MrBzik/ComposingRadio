package com.example.composingradio

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.hilt.navigation.compose.hiltViewModel

import com.example.composingradio.ui.common.ShowVerticalSeparators


import com.example.composingradio.ui.navigation.BottomNav


import com.example.composingradio.ui.theme.ComposingRadioTheme
import com.example.composingradio.ui.toolbar.DrawToolbarBG
import com.example.composingradio.ui.viewmodels.SearchViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ComposingRadioTheme {

                BottomNav()

                if(isSystemInDarkTheme())
                ShowVerticalSeparators()

                DrawToolbarBG()
            }
        }
    }
}



