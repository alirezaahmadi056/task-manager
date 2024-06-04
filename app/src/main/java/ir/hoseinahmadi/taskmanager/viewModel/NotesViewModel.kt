package ir.hoseinahmadi.taskmanager.viewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hoseinahmadi.taskmanager.data.db.notes.NotesItem
import ir.hoseinahmadi.taskmanager.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    val allNotesItem: Flow<List<NotesItem>> = repository.getAllNoteItem()

    val singleNotesItem: MutableStateFlow<NotesItem> = MutableStateFlow(NotesItem())

    fun upsertNotesItem(item: NotesItem) {
        viewModelScope.launch(Dispatchers.IO) { repository.upsertNotesItem(item) }
    }

    fun getNotesItem(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getItemById(id).collectLatest {
                singleNotesItem.emit(it)
            }
        }
    }

    fun deleteTask(item: NotesItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(item)
        }
    }
}


