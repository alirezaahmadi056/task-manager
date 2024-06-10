package ir.hoseinahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hoseinahmadi.taskmanager.data.model.AboutResponse
import ir.hoseinahmadi.taskmanager.repository.AboutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val repository: AboutRepository
) : ViewModel() {

    val allData: StateFlow<AboutResponse> = repository.allData

    fun getAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAboutData()
        }
    }
}