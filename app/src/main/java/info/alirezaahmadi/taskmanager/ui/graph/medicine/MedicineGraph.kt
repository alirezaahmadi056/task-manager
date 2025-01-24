package info.alirezaahmadi.taskmanager.ui.graph.medicine

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.viewModel.MedicineViewModel

fun NavGraphBuilder.medicineGraph(
    navHostController: NavHostController,
    medicineViewModel: MedicineViewModel
) {
    navigation<Screen.MedicineGraph>(startDestination = Screen.MedicineScreen) {
        composable<Screen.MedicineScreen> {
            MedicineScreen(
                navHostController = navHostController,
                medicineViewModel = medicineViewModel
            )
        }
    }
}