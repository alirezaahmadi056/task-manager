package ir.hoseinahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hoseinahmadi.frenchpastry.data.dataStore.DataStoreRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DatStoreViewModel @Inject constructor(
    private val repository: DataStoreRepositoryImpl
) : ViewModel() {

    companion object {
        const val CHECKED_GRID_LIST ="CHECKED_GRID_LIST"
    }


    fun saveGridList(value: Boolean) {
        viewModelScope.launch {
            repository.putBoolean(CHECKED_GRID_LIST, value)
        }
    }

    fun getGridList(): Boolean = runBlocking {
        repository.getBoolean(CHECKED_GRID_LIST) ?:true
    }



}