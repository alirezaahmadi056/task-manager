package ir.lrn.kara.ui.graph.medicine

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import ir.lrn.kara.navigation.Screen
import ir.lrn.kara.ui.graph.medicine.addMedicine.AddMedicineScreen
import ir.lrn.kara.ui.graph.medicine.medicineDetail.MedicineDetailScreen
import ir.lrn.kara.viewModel.MedicineViewModel

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
                day = args.day,
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