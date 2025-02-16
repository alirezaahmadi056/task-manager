package info.alirezaahmadi.taskmanager.navigation

import info.alirezaahmadi.taskmanager.data.db.goals.GoalsTimeFrame
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
    data class AddSkinRoutineScreen(
        val id: Int = 0,
        val time:String?=null,
        val day:String?=null
    ) : Screen

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
    data class AddGoalsScreen(
        val id: Int = 0,
        val timeFrame: String = GoalsTimeFrame.SHORT.name
    )

    @Serializable
    data class GoalsDetail(val id: Int = 0)

    @Serializable
    data class MovieBookDetailScreen(val id: Int, val type: String)

    //medicine
    @Serializable
    data object MedicineGraph : Screen

    @Serializable
    data object MedicineScreen : Screen

    @Serializable
    data class AddMedicineScreen(
        val id: Int = 0,val day:String?=null
    )

    //Dream
    @Serializable
    data object DreamGraph : Screen

    @Serializable
    data object DreamScreen : Screen

    @Serializable
    data class AddDreamsScreen(val id: Int = 0) : Screen

    @Serializable
    data class DreamDetailScreen(val id: Int = 0) : Screen

    @Serializable
    data class MedicineDetailScreen(val id: Int = 0) : Screen

    //Curriculum
    @Serializable
    data object CurriculumGraph : Screen

    @Serializable
    data object CurriculumScreen : Screen

    @Serializable
    data class AddCurriculumScreen(val id: Int? = null,val day:String?=null) : Screen
}