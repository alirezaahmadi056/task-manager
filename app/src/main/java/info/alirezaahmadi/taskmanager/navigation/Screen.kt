package info.alirezaahmadi.taskmanager.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    //first
    @Serializable
    data object FirstGraph : Screen
    @Serializable
    data object FirstScreen:Screen
    @Serializable
    data object AboutMeScreen : Screen

    //routine
    @Serializable
    data object WeeklyRoutineGraph:Screen
    @Serializable
    data object WeeklyRoutineScreen:Screen

    //duties
    @Serializable
    data object DutiesGraph:Screen
    @Serializable
    data object DutiesScreen : Screen
    @Serializable
    data class AddNotesScreen(val id:Int?=null) : Screen
    @Serializable
    data class AddTaskScreen(val id:Int?=null,val lastId:Int?=null) : Screen
    @Serializable
    data object SearchScreen : Screen
}