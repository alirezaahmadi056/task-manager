package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.db.routine.RoutineItem
import info.alirezaahmadi.taskmanager.repository.RoutineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val repository: RoutineRepository
) : ViewModel() {

    fun upsertRoutine(item: RoutineItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertRoutine(item)
        }
    }

    fun deletedById(itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletedById(itemId)

        }

    }

    fun getAllRoutine(): Flow<List<RoutineItem>> = repository.getAllRoutine()

    fun getRoutine(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllRoutine().collectLatest { result->

            }
        }
    }
}