package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.db.notes.NotesItem
import info.alirezaahmadi.taskmanager.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    val allNotesItem: Flow<List<NotesItem>> = repository.getAllNoteItem()


    fun upsertNotesItem(item: NotesItem) {
        viewModelScope.launch(Dispatchers.IO) { repository.upsertNotesItem(item) }
    }

    fun getNotesItem(id: Int):Flow<NotesItem> =repository.getItemById(id)


    fun deleteTask(item: NotesItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(item)
        }
    }
}


