package com.example.composingradio.utils

import androidx.compose.foundation.Image
import androidx.compose.material.BottomNavigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composingradio.R
import com.example.composingradio.ui.screens.*

const val SCREEN_SEARCH = "search window"
const val SCREEN_FAV = "fav window"
const val SCREEN_HISTORY = "history window"
const val SCREEN_REC = "rec window"
const val SCREEN_SETTINGS = "settings window"


@Composable
fun Navigation(navController : NavHostController){

    NavHost(navController = navController, startDestination = SCREEN_SEARCH){

//        composable(route = SCREEN_SEARCH){
//            SearchScreen()
//        }
        composable(route = SCREEN_FAV){
            FavScreen()
        }
        composable(route = SCREEN_HISTORY){
            HistoryScreen()
        }
        composable(route = SCREEN_REC){
            RecScreen()
        }
        composable(route = SCREEN_SETTINGS){
            SettingsScreen()
        }

    }
}

@Composable
fun BottomNavigationBar(
    items : List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    painter : Painter,
    onItemClick : (BottomNavItem) -> Unit
){

    val backStackEntry = navController.currentBackStackEntryAsState()



  BottomNavigation(
      modifier = modifier,
      backgroundColor = Color.Transparent,
      contentColor = Color.White


  ) {


//      items.forEach{ item ->
//
//          val selected = item.route == backStackEntry.value?.destination?.route
//
//          BottomNavigationItem(
//              selected = selected,
//              onClick = { onItemClick(item) },
//              selectedContentColor = Color.White,
//              unselectedContentColor = Color(0xB3FFFFFF),
//              icon = {
//
//                  Column(horizontalAlignment = CenterHorizontally) {
//
//                      Icon(
//                          imageVector = item.icon,
//                          contentDescription = null,
//                          modifier = modifier
//                              .padding(
//                                  0.dp, 0.dp, 0.dp,
//                                  if (selected) 10.dp else 0.dp
//                              )
//                              .fillMaxSize(0.8f)
//                      )
//
//
//                  }
//              }
//          )
//
//      }
  }
}


@Composable
fun BottomNavBG(){
    Image(
        painter = painterResource(id = R.drawable.bottom_nav_bg),
        contentDescription = ""
    )
}



data class BottomNavItem(
    val name : String,
    val route : String,
    val icon : ImageVector
)