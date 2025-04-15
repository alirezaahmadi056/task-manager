package ir.lrn.kara.ui.component

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ir.lrn.kara.util.Constants
import ir.lrn.kara.viewModel.DatStoreViewModel

@Composable
fun AppConfig(datStoreViewModel: DatStoreViewModel = hiltViewModel()) {
    getDataStore(datStoreViewModel)
}

fun getDataStore(datStoreViewModel: DatStoreViewModel) {
    Constants.firstDataSet = datStoreViewModel.getEnabledRoutes()
    Constants.GRIDLIST = datStoreViewModel.getGridList()
    Constants.SORT_TASK = datStoreViewModel.getTaskSort()
    Constants.SORT_NOTE = datStoreViewModel.getNoteSort()
    Constants.isThemDark = datStoreViewModel.getDarkThem()
    Constants.ROUTINE_SORT = datStoreViewModel.getRoutineSort()
}