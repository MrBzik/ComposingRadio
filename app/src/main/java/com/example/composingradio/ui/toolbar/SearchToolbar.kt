package com.example.composingradio.ui.toolbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.composingradio.R
import com.example.composingradio.ui.common.SeparatorVertical
import com.example.composingradio.ui.common.ShowVerticalSeparators
import com.example.composingradio.utils.TextStyles.interactiveTitle
import com.example.composingradio.utils.TextStyles.interactiveTitleOutline
import com.example.composingradio.ui.theme.NoRippleTheme



//val appBarState = remember {
//    MutableTransitionState(false).apply {
        // Start the animation immediately.
//        targetState = true
//    }
//}


//    AnimatedVisibility(visibleState = appBarState) {
//
//
//    }




@Composable
fun SearchToolbar(){


    Box(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)

    ) {


        if(isSystemInDarkTheme()){

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxSize()
            ){
                SeparatorVertical()
                SeparatorVertical()
            }


        } else {

            Image(
                painter = painterResource(id = R.drawable.toolbar_search_vector),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }


        Row(modifier = Modifier
            .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ){

            SearchTitle(text = "Tag", 1)
            SearchTitle(text = "Name", 2)
            SearchTitle(text = "Country", 3)

        }
    }
}



@Composable
fun RowScope.SearchTitle(text : String, logic : Int){

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val color = if (isPressed) Color.Yellow else Color.White


    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {

        TextButton(onClick = {
            when(logic){
                1 -> {

                }
                2 -> {

                }
                3 -> {

                }
            }
        },
            interactionSource = interactionSource,
            modifier = Modifier.weight(1f)
        ) {

            Box(

                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,

                ) {


                Text(text = text,
                    textAlign = TextAlign.Center,
                    style = interactiveTitleOutline,
                    color = Color.White,
                    maxLines = 1
                )

                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    style = interactiveTitle,
                    color = color,
                    maxLines = 1,

                    )
            }
        }
    }
}