package ir.hoseinahmadi.taskmanager.ui.screen.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NoteAdd
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.hoseinahmadi.taskmanager.data.db.NotesItem
import ir.hoseinahmadi.taskmanager.navigation.Screen
import okhttp3.internal.addHeaderLenient

@Composable
fun NotesScreen(navHostController: NavHostController) {
    var gridItem by remember {
        mutableStateOf(true)
    }

    val taskList = listOf(
        NotesItem(
            1,
            "Do Laundry",
            "Wash and fold clothes\nadigyfaiufgiuafiafgieasfgiusagfyiudsagifgyaidfgdsyaifpgiudaf\nu",
            "10:00",
            "11:00",
            MaterialTheme.colorScheme.onPrimary
        ),
        NotesItem(
            2,
            "Clean Kitchen",
            "Wash dishes, wipe counters, and mop the floor\nbfdjfghudfd",
            "11:30",
            "12:30",
            MaterialTheme.colorScheme.error

        ),
        NotesItem(
            3,
            "Vacuum Living Room",
            "Clean carpets and furnituredsfjdfjoudffdfdfdsfdfdfdfdfdfd",
            "13:00",
            "14:00",
            MaterialTheme.colorScheme.onSecondary

        ),
        NotesItem(
            4,
            "Water Plants",
            "Water indoor and outdoor plants",
            "15:00",
            "16:00",
            MaterialTheme.colorScheme.onSecondary

        ),
        NotesItem(
            5,
            "Cook Dinner",
            "Prepare a meal for the family",
            "18:00",
            "19:00",
            MaterialTheme.colorScheme.onSecondary

        ),
        NotesItem(
            6,
            "Clean Bathrooms",
            "Clean sinks, toilets, showers, and tubs",
            "11:00",
            "15:00",
            MaterialTheme.colorScheme.onPrimary
        ),
        NotesItem(
            7,
            "Organize Closet",
            "Sort and fold clothes and arrange them in the closet",
            "11:00",
            "12:00",
            MaterialTheme.colorScheme.onPrimary

        ),
        NotesItem(
            8,
            "Dust Furniture",
            "Dust and polish tables, shelves, and other furniture\nyea8gdgdsgudg9df\nbeafyggdfgydgudfg9ud\ngydeafg8ydfgyudh9udf\ngdbidfgydsgfgdfsi\ngsyigdfgdf\ngngdsnoigdshou",
            "14:00",
            "15:00",
            MaterialTheme.colorScheme.error

        ),
        NotesItem(
            9,
            "Clean Windows",
            "Wash and wipe windows and mirrors",
            "16:30",
            "17:30",
            MaterialTheme.colorScheme.error

        ),
        NotesItem(
            10,
            "Take Out Trash",
            "Collect and dispose of garbage and recycling",
            "20:00",
            "21:00",
            MaterialTheme.colorScheme.error

        ),

        NotesItem(
            12,
            "سلام خویسیییییییی",
            "باطابا زابابابایبط باطباباباباباباببا",
            "20:00",
            "21:00",
            MaterialTheme.colorScheme.error

        ),

        NotesItem(
            12,
            "سلام خویسیییییییی این نوتبیبیب من اس",
            "باطابا زابابابایبط باطبابابیبیبیبیبیلساهسیاهیسلاهعیسلاعلیاسعلیاسعخهاسبلخاهیسلاخهباخهعللاباخهعببیبیبیبباباباباببا",
            "20:00",
            "21:00",
            MaterialTheme.colorScheme.onPrimary

        ),
    )

    val lazyState = rememberLazyStaggeredGridState()

    Scaffold(
        floatingActionButton = {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                ExtendedFloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    expanded = lazyState.canScrollForward,
                    text = {
                        Text(
                            text = "یادداشت",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    },
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Rounded.NoteAdd,
                            contentDescription = "",
                            tint =Color.White
                        )
                    },
                    onClick = { navHostController.navigate(Screen.AddNotesScreen.route) })
            }

        },
        floatingActionButtonPosition = FabPosition.Start
    ) {
        if (gridItem) {
            LazyVerticalStaggeredGrid(
                state = lazyState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 4.dp),
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(3.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalItemSpacing = 6.dp
            ) {
                items(taskList) { item ->
                    NotesItemCard(item = item)
                }
            }
        } else {
            LazyColumn {
                items(taskList) { item ->
                    NotesListItem(item)
                }
            }
        }

    }

}
