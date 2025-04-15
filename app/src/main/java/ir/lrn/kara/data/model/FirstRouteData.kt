package ir.lrn.kara.data.model

import androidx.annotation.StringRes
import ir.lrn.kara.R
import ir.lrn.kara.navigation.Screen

data class FirstRouteData(
    val id: Int,
    @StringRes val nameRes: Int,
    val image: Int,
    val route: Screen
)
 {
    companion object {
        val fullFirstData = listOf(
            FirstRouteData(
                id = 1,
                nameRes = R.string.duties_and_notes,
                image = R.drawable.duties_image,
                route = Screen.DutiesGraph
            ),
            FirstRouteData(
                id = 2,
                nameRes = R.string.weekly_routine,
                image = R.drawable.weekly_routine_image,
                route = Screen.WeeklyRoutineGraph
            ),
            FirstRouteData(
                id = 3,
                nameRes = R.string.skin_routine,
                image = R.drawable.skin_routine_image,
                route = Screen.SkinRoutineGraph
            ),
            FirstRouteData(
                id = 4,
                nameRes = R.string.exercise_program,
                image = R.drawable.ecericies_image,
                route = Screen.ExerciseProgramGraph
            ),
            FirstRouteData(
                id = 5,
                nameRes = R.string.my_goals,
                image = R.drawable.goals_image,
                route = Screen.GoalsGraph
            ),
            FirstRouteData(
                id = 6,
                nameRes = R.string.medicine_program,
                image = R.drawable.medicine_image,
                route = Screen.MedicineGraph
            ),
            FirstRouteData(
                id = 7,
                nameRes = R.string.my_dreams,
                image = R.drawable.dream_image,
                route = Screen.DreamGraph
            ),
            FirstRouteData(
                id = 8,
                nameRes = R.string.curriculum_planning,
                image = R.drawable.lesson_image,
                route = Screen.CurriculumGraph
            )
        )
    }
}
