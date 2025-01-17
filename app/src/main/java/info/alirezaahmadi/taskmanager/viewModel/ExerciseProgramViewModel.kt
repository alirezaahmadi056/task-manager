package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.db.exerciseProgram.ExerciseProgramItem
import info.alirezaahmadi.taskmanager.repository.ExerciseProgramRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseProgramViewModel @Inject constructor(
    private val repository: ExerciseProgramRepository
) : ViewModel() {
    fun upsertExerciseProgram(exerciseItem: ExerciseProgramItem) {
        viewModelScope.launch(Dispatchers.IO) { repository.upsertExercise(exerciseItem) }
    }
    fun getAllExerciseProgram(): Flow<List<ExerciseProgramItem>> = repository.getAllExercise()
    fun deletedExerciseProgram(id: Int) { viewModelScope.launch { repository.deletedById(id) } }

    fun getExerciseProgram(itemId: Int): Flow<ExerciseProgramItem?> = repository.getSkinRoutine(itemId)
}