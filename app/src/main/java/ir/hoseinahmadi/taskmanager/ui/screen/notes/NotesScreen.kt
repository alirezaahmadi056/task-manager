package ir.hoseinahmadi.taskmanager.ui.screen.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.hoseinahmadi.taskmanager.data.db.NotesItem
import ir.hoseinahmadi.taskmanager.data.db.TaskColor

@Composable
fun NotesScreen(){
    val taskList = listOf(
        NotesItem(
            1,
            "Do Laundry",
            "Wash and fold clothes\nadigyfaiufgiuafiafgieasfgiusagfyiudsagifgyaidfgdsyaifpgiudaf\nu",
            "10:00",
            "11:00",
            TaskColor.RED
        ),
        NotesItem(
            2,
            "Clean Kitchen",
            "Wash dishes, wipe counters, and mop the floor\nbfdjfghudfd",
            "11:30",
            "12:30",
            TaskColor.ORANGE

        ),
        NotesItem(
            3,
            "Vacuum Living Room",
            "Clean carpets and furnituredsfjdfjoudffdfdfdsfdfdfdfdfdfd",
            "13:00",
            "14:00",
            TaskColor.GREEN

        ),
        NotesItem(
            4,
            "Water Plants",
            "Water indoor and outdoor plants",
            "15:00",
            "16:00",
            TaskColor.GREEN

        ),
       NotesItem(
            5,
            "Cook Dinner",
            "Prepare a meal for the family",
            "18:00",
            "19:00",
            TaskColor.GREEN

        ),
        NotesItem(
            6,
            "Clean Bathrooms",
            "Clean sinks, toilets, showers, and tubs",
            "11:00",
            "15:00",
            TaskColor.ORANGE
        ),
     NotesItem(
            7,
            "Organize Closet",
            "Sort and fold clothes and arrange them in the closet",
            "11:00",
            "12:00",
            TaskColor.RED

        ),
        NotesItem(
            8,
            "Dust Furniture",
            "Dust and polish tables, shelves, and other furniture\nyea8gdgdsgudg9df\nbeafyggdfgydgudfg9ud\ngydeafg8ydfgyudh9udf\ngdbidfgydsgfgdfsi\ngsyigdfgdf\ngngdsnoigdshou",
            "14:00",
            "15:00",
            TaskColor.RED

        ),
      NotesItem(
            9,
            "Clean Windows",
            "Wash and wipe windows and mirrors",
            "16:30",
            "17:30",
            TaskColor.RED

        ),
       NotesItem(
            10,
            "Take Out Trash",
            "Collect and dispose of garbage and recycling",
            "20:00",
            "21:00",
            TaskColor.ORANGE

        ),

      NotesItem(
            12,
            "سلام خویسیییییییی",
            "باطابا زابابابایبط باطباباباباباباببا",
            "20:00",
            "21:00",
            TaskColor.ORANGE

        ),

       NotesItem(
            12,
            "سلام خویسیییییییی",
            "باطابا زابابابایبط باطباباباباباباببا",
            "20:00",
            "21:00",
            TaskColor.ORANGE

        ),

        )

    val lazyState = rememberLazyStaggeredGridState()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = lazyState.canScrollForward,
                text = {
                    Text(text = "یادداشت", style = MaterialTheme.typography.bodyLarge) },
                icon = { Icon(Icons.Rounded.AddCircle, contentDescription ="" ) },
                onClick = { /*TODO*/ })

        }
    ) {
        LazyVerticalStaggeredGrid(
            state =lazyState ,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 4.dp),
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(3.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalItemSpacing = 5.dp
        ) {
            items(taskList){
                NotesItemCard(item = it)
            }
        }
    }

}