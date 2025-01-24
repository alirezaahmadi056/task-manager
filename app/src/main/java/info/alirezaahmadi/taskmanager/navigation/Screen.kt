package info.alirezaahmadi.taskmanager.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    //first
    @Serializable
    data object FirstGraph : Screen

    @Serializable
    data object FirstScreen : Screen

    @Serializable
    data object AboutMeScreen : Screen

    //routine
    @Serializable
    data object WeeklyRoutineGraph : Screen

    @Serializable
    data object WeeklyRoutineScreen : Screen

    //duties
    @Serializable
    data object DutiesGraph : Screen

    @Serializable
    data object DutiesScreen : Screen

    @Serializable
    data class AddNotesScreen(val id: Int? = null) : Screen

    @Serializable
    data class AddTaskScreen(val id: Int? = null, val lastId: Int? = null) : Screen

    @Serializable
    data object SearchScreen : Screen

    //skin routine
    @Serializable
    data object SkinRoutineGraph : Screen

    @Serializable
    data object SkinRoutineScreen : Screen

    @Serializable
    data class AddSkinRoutineScreen(val id: Int = 0) : Screen

    //exercise program
    @Serializable
    data object ExerciseProgramGraph : Screen

    @Serializable
    data object ExerciseProgramScreen : Screen

    @Serializable
    data class AddExerciseProgramScreen(val id: Int? = null) : Screen

    @Serializable
    data class StartExerciseProgramScreen(val day: String) : Screen

    @Serializable
    data class CompletedExerciseScreen(
        val dayName: String,
        val time: Long,
        val exerciseList: List<String>
    ) : Screen

    //goals
    @Serializable
    data object GoalsGraph : Screen

    @Serializable
    data object GoalsScreen : Screen

    @Serializable
    data class GoalsFullScreen(val pageIndex: Int)
    @Serializable
    data class AddGoalsScreen(val id:Int=0)
    @Serializable
    data class GoalsDetail(val id:Int=0)
}