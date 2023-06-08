package com.example.composingradio.utils

import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.composingradio.R

object TextStyles {

    @OptIn(ExperimentalTextApi::class)
    val interactiveTitleOutline : TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.aldrich)),
        drawStyle = Stroke(
            miter = 10f,
            width = 1f,
            join = StrokeJoin.Round
        )
    )

    val interactiveTitle : TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.aldrich)),

        )

}