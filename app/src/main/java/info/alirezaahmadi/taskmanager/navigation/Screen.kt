package info.alirezaahmadi.taskmanager.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object NotesScreen : Screen
    @Serializable
    data object TaskScreen : Screen
    @Serializable
    data class AddNotesScreen(val id:Int?) : Screen
    @Serializable
    data class AddTaskScreen(val id:Int?=null,val lastId:Int?=null) : Screen
    @Serializable
    data object AboutMeScreen : Screen
    @Serializable
    data object SearchScreen : Screen
    @Serializable
    data object MainScreen : Screen
}