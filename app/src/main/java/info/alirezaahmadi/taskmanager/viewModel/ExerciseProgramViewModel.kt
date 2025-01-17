package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.repository.ExerciseProgramRepository
import javax.inject.Inject

@HiltViewModel
class ExerciseProgramViewModel @Inject constructor(
    private val repository: ExerciseProgramRepository
):ViewModel() {

}