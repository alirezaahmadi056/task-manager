package ir.hoseinahmadi.taskmanager.ui.screen.notes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NotesScreen(){
    val taskList = listOf(
        ir.hoseinahmadi.taskmanager.data.db.NotesItem(
            1,
            "Do Laundry",
            "Wash and fold clothes",
            "10:00",
            "11:00"
        ),
        ir.hoseinahmadi.taskmanager.data.db.NotesItem(
            2,
            "Clean Kitchen",
            "Wash dishes, wipe counters, and mop the floor",
            "11:30",
            "12:30"
        ),
        ir.hoseinahmadi.taskmanager.data.db.NotesItem(
            3,
            "Vacuum Living Room",
            "Clean carpets and furniture",
            "13:00",
            "14:00"
        ),
        ir.hoseinahmadi.taskmanager.data.db.NotesItem(
            4,
            "Water Plants",
            "Water indoor and outdoor plants",
            "15:00",
            "16:00"
        ),
        ir.hoseinahmadi.taskmanager.data.db.NotesItem(
            5,
            "Cook Dinner",
            "Prepare a meal for the family",
            "18:00",
            "19:00"
        ),
        ir.hoseinahmadi.taskmanager.data.db.NotesItem(
            6,
            "Clean Bathrooms",
            "Clean sinks, toilets, showers, and tubs",
            "11:00",
            "15:00"
        ),
        ir.hoseinahmadi.taskmanager.data.db.NotesItem(
            7,
            "Organize Closet",
            "Sort and fold clothes and arrange them in the closet",
            "11:00",
            "12:00"
        ),
        ir.hoseinahmadi.taskmanager.data.db.NotesItem(
            8,
            "Dust Furniture",
            "Dust and polish tables, shelves, and other furniture",
            "14:00",
            "15:00"
        ),
        ir.hoseinahmadi.taskmanager.data.db.NotesItem(
            9,
            "Clean Windows",
            "Wash and wipe windows and mirrors",
            "16:30",
            "17:30"
        ),
        ir.hoseinahmadi.taskmanager.data.db.NotesItem(
            10,
            "Take Out Trash",
            "Collect and dispose of garbage and recycling",
            "20:00",
            "21:00"
        ),

        ir.hoseinahmadi.taskmanager.data.db.NotesItem(
            12,
            "سلام خویسیییییییی",
            "باطابا زابابابایبط باطباباباباباباببا",
            "20:00",
            "21:00"
        ),

        ir.hoseinahmadi.taskmanager.data.db.NotesItem(
            12,
            "سلام خویسیییییییی",
            "باطابا زابابابایبط باطباباباباباباببا",
            "20:00",
            "21:00"
        ),



        )
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp,
            bottom = 16.dp
        ),
        modifier = Modifier.fillMaxSize()
    ) {
        items(taskList){
            NotesItem(item = it)
        }
    }
}