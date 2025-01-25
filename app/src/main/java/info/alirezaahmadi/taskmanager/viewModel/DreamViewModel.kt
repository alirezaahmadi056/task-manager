package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.db.dream.DreamItem
import info.alirezaahmadi.taskmanager.repository.DreamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DreamViewModel @Inject constructor(
    private val repository: DreamRepository
):ViewModel() {

     fun upsertDreamItem(dreamItem: DreamItem) {
         viewModelScope.launch(Dispatchers.IO) {
             repository.upsertDreamItem(dreamItem)
         }
    }
     fun deleteDreamByID(id: Int) {
        viewModelScope.launch(Dispatchers.IO) { repository.deleteDreamByID(id) }
    }
    fun getAllDreamItemDao() : Flow<List<DreamItem>> = repository.getAllDreamItemDao()
    fun getDreamById(id: Int): Flow<DreamItem?> = repository.getDreamById(id)
}