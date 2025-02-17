package ir.lrn.kara.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.lrn.kara.data.db.goals.GoalsItem
import ir.lrn.kara.data.db.goals.GoalsTimeFrame
import ir.lrn.kara.repository.GoalsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val repository: GoalsRepository
) : ViewModel() {

    private val _shortTermGoals = MutableStateFlow<List<GoalsItem>>(emptyList())
    val shortTermGoals: StateFlow<List<GoalsItem>> = _shortTermGoals.asStateFlow()

    private val _mediumTermGoals = MutableStateFlow<List<GoalsItem>>(emptyList())
    val mediumTermGoals: StateFlow<List<GoalsItem>> = _mediumTermGoals.asStateFlow()

    private val _longTermGoals = MutableStateFlow<List<GoalsItem>>(emptyList())
    val longTermGoals: StateFlow<List<GoalsItem>> = _longTermGoals.asStateFlow()

    private val _allTermGoals = MutableStateFlow<List<GoalsItem>>(emptyList())
    val allTermGoals: StateFlow<List<GoalsItem>> = _allTermGoals.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllGoals().collectLatest { goals ->
                _allTermGoals.emit(goals)
                _shortTermGoals.emit(goals.filter { it.timeFrame == GoalsTimeFrame.SHORT.name })
                _mediumTermGoals.emit(goals.filter { it.timeFrame == GoalsTimeFrame.MEDIUM.name })
                _longTermGoals.emit(goals.filter { it.timeFrame == GoalsTimeFrame.LONG.name })
            }
        }
    }

    fun upsertGoals(goalsItem: GoalsItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertGoals(goalsItem)
        }
    }

    fun getGoalsById(id: Int): Flow<GoalsItem?> = repository.getGoalsById(id)
    fun deletedGoalsById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletedGoalsById(id)
        }
    }
}