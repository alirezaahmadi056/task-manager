package info.alirezaahmadi.taskmanager.ui.graph.medicine

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.graph.medicine.addMedicine.AddMedicineScreen
import info.alirezaahmadi.taskmanager.ui.graph.medicine.medicineDetail.MedicineDetailScreen
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
        composable<Screen.AddMedicineScreen> {
            val args =it.toRoute<Screen.AddMedicineScreen>()
            AddMedicineScreen(
                navHostController = navHostController,
                id = args.id,
                medicineViewModel = medicineViewModel
            )
        }
        composable<Screen.MedicineDetailScreen> {
            val args =it.toRoute<Screen.MedicineDetailScreen>()
            MedicineDetailScreen(
                navHostController = navHostController,
                id = args.id,
                medicineViewModel = medicineViewModel
            )
        }
    }
}