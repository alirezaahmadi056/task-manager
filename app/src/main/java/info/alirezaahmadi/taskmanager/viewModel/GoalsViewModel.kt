package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsItem
import info.alirezaahmadi.taskmanager.repository.GoalsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val repository: GoalsRepository
) : ViewModel() {
    fun upsertGoals(goalsItem: GoalsItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertGoals(goalsItem)
        }
    }
    fun getGoalsById(id: Int): Flow<GoalsItem?> = repository.getGoalsById(id)
    fun getAllGoals(): Flow<List<GoalsItem>> = repository.getAllGoals()
     fun deletedGoalsById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletedGoalsById(id)
        }
    }
}