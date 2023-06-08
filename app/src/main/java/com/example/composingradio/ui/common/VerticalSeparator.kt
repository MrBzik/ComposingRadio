package com.example.composingradio.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SeparatorVertical(isPadding : Boolean = false){

    Box(modifier = Modifier
        .width(2.dp)
        .padding(top = if(isPadding) 40.dp else 0.dp,
                bottom = if(isPadding) 60.dp else 0.dp)
        .fillMaxHeight()
        .background(
            Brush.verticalGradient(
                listOf(
                    Color.Black,
                    Color(0xFFAA4D06),
                    Color.Black
                )
            )
        )
    )
}


@Composable
fun ShowVerticalSeparators(){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        SeparatorVertical(true)
        SeparatorVertical(true)
    }
}
