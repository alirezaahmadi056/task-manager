package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.repository.GoalsRepository
import javax.inject.Inject
@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val repository: GoalsRepository
):ViewModel() {
}