package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.db.dream.DreamItem
import info.alirezaahmadi.taskmanager.repository.DreamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DreamViewModel @Inject constructor(
    private val repository: DreamRepository
):ViewModel() {

    private val _allDream =MutableStateFlow<List<DreamItem>>(emptyList())
    val allDream:StateFlow<List<DreamItem>> =_allDream.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllDreamItemDao().collectLatest {
                _allDream.emit(it)
            }
        }

    }
     fun upsertDreamItem(dreamItem: DreamItem) {
         viewModelScope.launch(Dispatchers.IO) {
             repository.upsertDreamItem(dreamItem)
         }
    }
     fun deleteDreamByID(id: Int) {
        viewModelScope.launch(Dispatchers.IO) { repository.deleteDreamByID(id) }
    }
    fun getDreamById(id: Int): Flow<DreamItem?> = repository.getDreamById(id)
}