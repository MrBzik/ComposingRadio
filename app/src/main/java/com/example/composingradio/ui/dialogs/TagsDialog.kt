package com.example.composingradio.ui.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composingradio.domain.TagWithGenre
import com.example.composingradio.domain.TagWithGenre.Genre
import com.example.composingradio.ui.viewmodels.DialogsViewModel
import com.example.composingradio.ui.viewmodels.SearchViewModel
import com.example.composingradio.utils.DialogCalls
import com.example.composingradio.utils.TAG_BY_CLASSIC
import com.example.composingradio.utils.TAG_BY_EXPERIMENTAL
import com.example.composingradio.utils.TAG_BY_GENRE
import com.example.composingradio.utils.TAG_BY_MINDFUL
import com.example.composingradio.utils.TAG_BY_ORIGIN
import com.example.composingradio.utils.TAG_BY_OTHER
import com.example.composingradio.utils.TAG_BY_PERIOD
import com.example.composingradio.utils.TAG_BY_RELIGION
import com.example.composingradio.utils.TAG_BY_SPECIAL
import com.example.composingradio.utils.TAG_BY_SUB_GENRE
import com.example.composingradio.utils.TAG_BY_TALK
import com.example.composingradio.utils.tagsListByGenre
import com.example.composingradio.utils.tagsListByOrigin
import com.example.composingradio.utils.tagsListByPeriod
import com.example.composingradio.utils.tagsListBySubGenre
import com.example.composingradio.utils.tagsListByTalk
import com.example.composingradio.utils.tagsListClassics
import com.example.composingradio.utils.tagsListExperimental
import com.example.composingradio.utils.tagsListMindful
import com.example.composingradio.utils.tagsListOther
import com.example.composingradio.utils.tagsListReligion
import com.example.composingradio.utils.tagsListSpecial
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

val listOfTags = mutableListOf<Genre>().apply {
    
    add(Genre(
        genre = TAG_BY_PERIOD,
        tags = tagsListByPeriod)
    )
    add(Genre(
        genre = TAG_BY_SPECIAL,
        tags = tagsListSpecial)
    )
    add(Genre(
        genre = TAG_BY_GENRE,
        tags = tagsListByGenre)
    )
    add(Genre(
        genre = TAG_BY_SUB_GENRE,
        tags = tagsListBySubGenre)
    )
    add(Genre(
        genre = TAG_BY_CLASSIC,
        tags = tagsListClassics)
    )
    add(Genre(
        genre = TAG_BY_MINDFUL,
        tags = tagsListMindful)
    )
    add(Genre(
        genre = TAG_BY_EXPERIMENTAL,
        tags = tagsListExperimental)
    )
    add(Genre(
        genre = TAG_BY_TALK,
        tags = tagsListByTalk)
    )
    add(Genre(
        genre = TAG_BY_RELIGION,
        tags = tagsListReligion)
    )
    add(Genre(
        genre = TAG_BY_ORIGIN,
        tags = tagsListByOrigin)
    )
    add(Genre(
        genre = TAG_BY_OTHER,
        tags = tagsListOther)
    )
}

@OptIn(ExperimentalFoundationApi::class, FlowPreview::class)
@Composable
fun TagsDialog(
//    searchViewModel: SearchViewModel = hiltViewModel()
    dialogsViewModel: DialogsViewModel = hiltViewModel()
){

    val tagsState = remember{
        mutableStateOf(TagsState(listOfTags.toList(), System.currentTimeMillis()))
    }

    val searchField = remember {
        mutableStateOf("")
    }


    val lazyState = rememberLazyListState()

    var position by remember {
        mutableIntStateOf(0)
    }


    val enterAnim = expandVertically(expandFrom = Alignment.Top) + fadeIn()

    val exitAnim = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()


    LaunchedEffect(lazyState){

        snapshotFlow {
            lazyState.firstVisibleItemIndex
        }
            .debounce(500L)
            .collectLatest { index ->
                if(searchField.value.isBlank())
                    position = index
            }
    }


    LaunchedEffect(key1 = searchField.value){

        if(searchField.value.isBlank()){
            lazyState.scrollToItem(position)
        }
    }


    Column (modifier = Modifier
        .fillMaxSize()
        .padding(top = 40.dp, bottom = 40.dp, start = 20.dp, end = 20.dp)
        .background(Color.White)
        .border(width = 2.dp, color = Color.Blue)

    ) {


        TextField(state = searchField)


        LazyColumn(Modifier.weight(1f), contentPadding = PaddingValues(10.dp), state = lazyState){


            tagsState.value.genres.forEachIndexed { index, genre ->


                stickyHeader {

                    GenreHeader(
                        isVisible = searchField.value.isBlank(),
                        genre = genre.genre,
                        enter = enterAnim,
                        exit = exitAnim
                    ) {
                        listOfTags[index].isOpen = !listOfTags[index].isOpen
                        tagsState.value = TagsState(listOfTags.toList(), System.currentTimeMillis())
                    }

                }
                items(genre.tags){tag ->

                    TagItem(
                        isVisible = genre.isOpen && searchField.value.isBlank()
                                || tag.tag.contains(searchField.value) && searchField.value.isNotBlank(),
                        tag = tag,
                        enter = enterAnim,
                        exit = exitAnim
                    )
                }
            }
        }


        Buttons(onBack = {dialogsViewModel.updateDialogsState(DialogCalls.NO_DIALOG)},
            onRestore = {dialogsViewModel.updateDialogsState(DialogCalls.NO_DIALOG)})

    }
}

@Composable
fun Buttons(
    onBack: () -> Unit,
    onRestore : () -> Unit
){

    Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {

        TextButton(onClick = { onBack() }) {
            Text(text = "Back")
        }

        TextButton(onClick = {onRestore()}) {
            Text(text = "Clear selection")
        }

    }

}

@Composable
fun TagItem(
    isVisible: Boolean,
    tag : TagWithGenre.Tag,
    enter : EnterTransition,
    exit : ExitTransition
){

    AnimatedVisibility(visible = isVisible,
        enter = enter,
        exit = exit
    ) {

        Column {
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = tag.tag)
                Text(text = tag.count.toString())
            }

            Spacer(modifier = Modifier.height(10.dp))

            Divider(color = Color.LightGray, modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}


@Composable
fun GenreHeader(
    genre: String,
    isVisible : Boolean,
    enter : EnterTransition,
    exit : ExitTransition,
    onClick : () -> Unit
){

    AnimatedVisibility(visible = isVisible,
        enter = enter,
        exit = exit
    ){

        Surface (modifier = Modifier.fillMaxWidth()) {

            Column {
                Spacer(modifier = Modifier.height(10.dp))

                Text( text = genre, fontSize = 20.sp, modifier = Modifier
                    .clickable {
                        onClick()
                    }
                    .fillMaxWidth(), textAlign = TextAlign.Center)

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun TextField(
    state : MutableState<String>
){
    TextField(value = state.value, onValueChange = {
        state.value = it
    }, modifier = Modifier.fillMaxWidth(), singleLine = true, label = {
        Text(text = "Search tag")
    })
}



data class TagsState(val genres : List<Genre>, val change: Long)
