package info.alirezaahmadi.taskmanager.navigation

sealed class Screen(val route: String) {

    data object NotesScreen : Screen("motes_Screen")
    data object TaskScreen : Screen("task_Screen")
    data object AddNotesScreen : Screen("AddNotesScreen")
    data object AddTaskScreen : Screen("AddTaskScreen")
    data object AboutMeScreen : Screen("AboutMeScreen")
    data object SearchScreen :Screen("SearchScreen")
    data object MainScreen:Screen("MainScreen")
}