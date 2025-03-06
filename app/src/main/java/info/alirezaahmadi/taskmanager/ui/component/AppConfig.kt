package info.alirezaahmadi.taskmanager.ui.component

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.viewModel.DatStoreViewModel

@Composable
fun AppConfig(datStoreViewModel: DatStoreViewModel = hiltViewModel()) {
    getDataStore(datStoreViewModel)
}

fun getDataStore(datStoreViewModel: DatStoreViewModel) {
    Constants.GRIDLIST = datStoreViewModel.getGridList()
    Constants.SORT_TASK = datStoreViewModel.getTaskSort()
    Constants.SORT_NOTE = datStoreViewModel.getNoteSort()
    Constants.isThemDark = datStoreViewModel.getDarkThem()
    Constants.ROUTINE_SORT = datStoreViewModel.getRoutineSort()
}