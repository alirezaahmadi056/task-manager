package ir.hoseinahmadi.taskmanager.navigation

sealed class Screen(val route:String) {

    data object NotesScreen:Screen("motes_Screen")
    data object TaskScreen:Screen("task_Screen")
    data object AddNotesScreen:Screen("AddNotesScreen")

}