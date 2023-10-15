package com.example.composingradio.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composingradio.R
import com.example.composingradio.ui.screens.*
import com.example.composingradio.ui.viewmodels.NavViewModel
import com.example.composingradio.ui.viewmodels.SearchViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController


val screens = listOf(
    BottomBarScreen.Search,
    BottomBarScreen.Favourite,
    BottomBarScreen.History,
    BottomBarScreen.Recording,
    BottomBarScreen.Settings,
)


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomNavGraph(
    navController: NavHostController,
    navViewModel : NavViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel()
){

    val systemUiController = rememberSystemUiController()

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Search.route,
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        }
    ){

        composable(route = BottomBarScreen.Search.route){
            navViewModel.updateToolbarState(0)
            SearchScreen(searchViewModel)
            if(!isSystemInDarkTheme())
                systemUiController.setSystemBarsColor(colorResource(id = R.color.nav_bar_search_fragment))
        }
        composable(route = BottomBarScreen.Favourite.route){
            navViewModel.updateToolbarState(1)
            FavScreen()
            if(!isSystemInDarkTheme())
                systemUiController.setSystemBarsColor(colorResource(id = R.color.nav_bar_fav_fragment))
        }
        composable(route = BottomBarScreen.History.route){
            navViewModel.updateToolbarState(2)
            HistoryScreen()
            if(!isSystemInDarkTheme())
                systemUiController.setSystemBarsColor(colorResource(id = R.color.nav_bar_history_frag))
        }
        composable(route = BottomBarScreen.Recording.route){
            navViewModel.updateToolbarState(3)
            RecScreen()
            if(!isSystemInDarkTheme())
                systemUiController.setSystemBarsColor(colorResource(id = R.color.nav_bar_rec_frag))
        }
        composable(route = BottomBarScreen.Settings.route){
            navViewModel.updateToolbarState(4)
            SettingsScreen()
            if(!isSystemInDarkTheme())
                systemUiController.setSystemBarsColor(colorResource(id = R.color.nav_bar_settings_frag))
        }
    }
}


@Composable
fun BottomNav(

){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController)}
    ) {
        it
        BottomNavGraph(
            navController = navController
        )
    }
}

@Composable
fun BottomBar(navController : NavHostController){


    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination


    if(!isSystemInDarkTheme()){
        Image(
            painter = painterResource(id = R.drawable.bottom_nav_bg),
            contentDescription = "",
            modifier = Modifier.height(60.dp).fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )

        DrawBottomNav(
            height = 60.dp,
            currentDestination = currentDestination,
            navController = navController
        )


    } else {

        Column (modifier = Modifier.height(60.dp)) {


            Separator(0.dp)

            DrawBottomNav(
                height = 56.dp,
                currentDestination = currentDestination,
                navController = navController
            )

            Separator(40.dp)

        }
    }
}


@Composable
fun DrawBottomNav(
    height: Dp,
    currentDestination: NavDestination?,
    navController: NavHostController
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(if (isSystemInDarkTheme()) Color.Black else Color.Transparent),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        screens.forEach{ screen ->

            AddItem(screen = screen, currentDestination = currentDestination, navController = navController)

        }
    }
}



@Composable
fun RowScope.AddItem(
   screen: BottomBarScreen,
   currentDestination: NavDestination?,
   navController: NavHostController
){

    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    Box(modifier = Modifier
        .height(60.dp)
        .weight(1f)
        .noRippleClickable {
            navController.navigate(screen.route)
            {
                clearAll(navController)
            }
        },
        contentAlignment = Alignment.Center

    ) {

        if(isSystemInDarkTheme()){

            Text(
                text = screen.title,
                fontFamily = FontFamily(Font(R.font.carrois_gothic_sc)),
                color = if(selected) colorResource(id = R.color.color_non_interactive) else Color.LightGray,
                fontSize = if(selected) 18.sp else 14.sp
            )

        } else {

//            Icon(painter = painterResource(id = screen.icon), contentDescription = "",
//
//                modifier = Modifier
//                    .alpha(if(selected) 1f else 0.6f)
//                    .padding(0.dp, 0.dp, 0.dp, if (selected) 10.dp else 0.dp)
//                    .fillMaxSize(0.7f)
//                ,
//                tint = Color.White,
//
//
//                )

            Image(
                painter = painterResource(id = screen.icon),
                contentDescription = "",
                modifier = Modifier
                    .height(60.dp)
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = if (selected) 5.dp else 10.dp,
                        bottom = if (selected) 15.dp else 10.dp
                    ),
                contentScale = ContentScale.Crop,
                alpha = if(selected) 1f else 0.6f
            )
        }
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}


//@Composable
//@Preview
//fun BottomNavPreview() {
//    BottomNav()
//}

fun NavOptionsBuilder.clearAll(navController: NavHostController){
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive = true
    }
}



@Composable
fun Separator(
    padding : Dp
){

    Box(modifier = Modifier
        .height(2.dp)
        .padding(start = padding, end = padding)
        .fillMaxWidth()
        .background(
            Brush.linearGradient(
                listOf(
                    Color.Black,
                    Color(0xFFAA4D06),
                    Color.Black
                )
            )
        )
    )
}

