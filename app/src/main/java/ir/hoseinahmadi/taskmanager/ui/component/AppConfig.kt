package ir.hoseinahmadi.taskmanager.ui.component

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ir.hoseinahmadi.taskmanager.util.Constants
import ir.hoseinahmadi.taskmanager.viewModel.DatStoreViewModel

@Composable
fun AppConfig(datStoreViewModel: DatStoreViewModel = hiltViewModel()) {
    getDataStore(datStoreViewModel)
}

fun getDataStore(datStoreViewModel: DatStoreViewModel) {
    Constants.GRIDLIST = datStoreViewModel.getGridList()
}