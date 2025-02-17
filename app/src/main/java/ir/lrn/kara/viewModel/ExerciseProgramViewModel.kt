package ir.lrn.kara.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.lrn.kara.data.db.exerciseProgram.ExerciseProgramItem
import ir.lrn.kara.repository.ExerciseProgramRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseProgramViewModel @Inject constructor(
    private val repository: ExerciseProgramRepository
) : ViewModel() {
    private val _allExerciseProgram = MutableStateFlow<List<ExerciseProgramItem>>(emptyList())
    val allExerciseProgram: StateFlow<List<ExerciseProgramItem>> = _allExerciseProgram.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllExercise().collectLatest {
                _allExerciseProgram.emit(it)
            }
        }
    }

    fun upsertExerciseProgram(exerciseItem: ExerciseProgramItem) {
        viewModelScope.launch(Dispatchers.IO) { repository.upsertExercise(exerciseItem) }
    }


    fun deletedExerciseProgram(id: Int) {
        viewModelScope.launch { repository.deletedById(id) }
    }

    fun getExerciseProgram(itemId: Int): Flow<ExerciseProgramItem?> =
        repository.getSkinRoutine(itemId)
}