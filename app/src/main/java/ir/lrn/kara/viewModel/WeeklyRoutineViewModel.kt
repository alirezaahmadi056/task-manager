package ir.lrn.kara.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.lrn.kara.data.db.routine.RoutineItem
import ir.lrn.kara.repository.WeeklyRoutineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeeklyRoutineViewModel @Inject constructor(
    private val repository: WeeklyRoutineRepository
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

}