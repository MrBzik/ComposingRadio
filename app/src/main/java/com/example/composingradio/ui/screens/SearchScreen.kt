package com.example.composingradio.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.composingradio.ui.items.RadioItem
import com.example.composingradio.ui.viewmodels.SearchViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce


@OptIn(FlowPreview::class)
@Composable
fun SearchScreen(
    searchViewModel : SearchViewModel
){

    var isToPlayInitialAnim = true

    val savedIndex = searchViewModel.lastScrollIndex

    val stations = searchViewModel.searchList.collectAsLazyPagingItems()

    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = savedIndex
    )

    LaunchedEffect(lazyListState){

        snapshotFlow {
            lazyListState.firstVisibleItemIndex
        }
            .debounce(500L)
            .collectLatest { index ->
                searchViewModel.lastScrollIndex = index
                if( index != savedIndex) isToPlayInitialAnim = false
            }
    }


    Box(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, top = 60.dp, bottom = 60.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
        ) {


        LazyColumn(
            state = lazyListState,
            modifier = Modifier.testTag("search_list").fillMaxSize()
        ){

            items(
                count = stations.itemCount,
                key = stations.itemKey {
                    it.stationuuid
                }
            ){ index ->

                stations[index]?.let { station ->

                    val state = remember {
                        MutableTransitionState(false).apply {
                            // Start the animation immediately.
                            targetState = true
                        }
                    }

                    AnimatedVisibility(visibleState = state,
                        enter =
                        if(lazyListState.firstVisibleItemIndex == savedIndex
                            && isToPlayInitialAnim
                            || lazyListState.firstVisibleItemIndex == 0){
                            fadeIn(
                                animationSpec = tween(250, index*50)
                            ) + slideInVertically(
                                initialOffsetY = { value ->
                                    -value / 2
                                },
                                animationSpec = tween(250, index *50)
                            )

                        } else {
                            fadeIn(
                                animationSpec = tween(250)
                            )
                        }
                    ) {
                        RadioItem(station = station)
                    }
                }
            }
        }
    }
}





