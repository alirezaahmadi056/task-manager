package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.model.about.AboutResponse
import info.alirezaahmadi.taskmanager.repository.AboutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val repository: AboutRepository
) : ViewModel() {
val loading =repository.loading
    val allData: StateFlow<AboutResponse> = repository.allData

    fun getAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAboutData()
        }
    }
}