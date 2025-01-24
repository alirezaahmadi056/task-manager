package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.repository.DreamRepository
import javax.inject.Inject

@HiltViewModel
class DreamViewModel @Inject constructor(
    private val repository: DreamRepository
):ViewModel() {

}