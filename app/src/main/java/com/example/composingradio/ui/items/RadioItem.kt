package com.example.composingradio.ui.items

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.*
import coil.request.ImageRequest
import coil.size.Size
import com.example.composingradio.R
import com.example.composingradio.data.local.entities.RadioStation
import com.example.composingradio.utils.RandomColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

val colors = RandomColors()


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun RadioItem(
    station : RadioStation
){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
    ){

        val state = if(station.state.isNullOrBlank()) "" else "${station.state}, "
        
        Column(modifier = Modifier
            .padding(start = 73.dp, end = 8.dp)
            .height(75.dp),
        verticalArrangement = Arrangement.Center
            ) {
            Text(text = station.name?: "",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.carrois_gothic_sc)),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
                )
            Text(text = state + station.country,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.aldrich)),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,

                )

        }

        if(!isSystemInDarkTheme()){

            Divider(
                modifier = Modifier.padding(top = 73.dp),
                thickness = 2.dp,
                color = Color(0x14000000)
            )
        }

        val char = station.name?.first {
            it.isLetter()
        }.toString().uppercase()

        if(station.favicon.isNullOrBlank()){
            DrawLetter(char = char, placeholderColor = colors.getColor())
        }

        else {

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(station.favicon)
                    .dispatcher(Dispatchers.IO)
                    .crossfade(600)
                    .size(Size.ORIGINAL)
                    .build())

//            if(painter.state is AsyncImagePainter.State.Loading ||
//                painter.state is AsyncImagePainter.State.Error
//            ){
//                DrawLetter(char = char, placeholderColor = placeholderColor)
//            }

            if(painter.state is AsyncImagePainter.State.Success){
                Image(painter = painter, contentDescription = "",  modifier = Modifier
                    .padding(top = 5.dp)
                    .size(65.dp)
                )
            } else {
                DrawLetter(char = char, placeholderColor = colors.getColor())
            }
        }


//        AsyncImage(
//            modifier = Modifier
//                .size(65.dp)
//                .padding(top = 5.dp),
//            model = ImageRequest.Builder(LocalContext.current)
//            .data(station.favicon)
//            .crossfade(true)
//            .build()
//            ,
//            contentDescription = "")



    }
}


@Composable
fun DrawLetter(char : String, placeholderColor: Int){

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(top = 5.dp)
            .size(65.dp)
            .background(Color(placeholderColor))
    ) {

        Text(text = char.ifBlank { "X" },
            fontSize = 40.sp,
            fontFamily = FontFamily(Font(R.font.ranchers)),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
@Preview
fun ShowItem(){

    RadioItem(station = RadioStation(
        "", "",
        "Some station name that can take up some quiet of space and what u gonan do about it", "USA", "", "", "", "",
        0L, "Michigan", 250
    ))
}