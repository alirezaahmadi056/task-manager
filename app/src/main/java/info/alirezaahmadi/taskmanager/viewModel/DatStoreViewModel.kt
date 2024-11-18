package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.dataStore.DataStoreRepositoryImpl
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DatStoreViewModel @Inject constructor(
    private val repository: DataStoreRepositoryImpl
) : ViewModel() {

    companion object {
        const val CHECKED_GRID_LIST = "CHECKED_GRID_LIST"
        const val IS_THEME_DARK = "IS_THEME_DARK"
        const val NOTE_SORT = "NOTE_SORT"
        const val TASK_SORT = "TASK_SORT"
        const val ROUTINE_SORT = "ROUTINE_SORT"
    }


    fun saveGridList(value: Boolean) {
        viewModelScope.launch {
            repository.putBoolean(CHECKED_GRID_LIST, value)
        }
    }

    fun saveDarkThem(value: Boolean) {
        viewModelScope.launch {
            repository.putBoolean(IS_THEME_DARK, value)
        }
    }

    fun saveNoteSort(value: Int) {
        viewModelScope.launch {
            repository.putInt(NOTE_SORT, value)
        }
    }
    fun saveTaskSort(value: Int) {
        viewModelScope.launch {
            repository.putInt(TASK_SORT, value)
        }
    }
    fun saveRoutineSort(value: Int) {
        viewModelScope.launch {
            repository.putInt(ROUTINE_SORT, value)
        }
    }

    fun getGridList(): Boolean = runBlocking {
        repository.getBoolean(CHECKED_GRID_LIST) ?: true
    }

    fun getDarkThem(): Boolean = runBlocking {
        repository.getBoolean(IS_THEME_DARK) ?: false
    }

    fun getNoteSort(): Int = runBlocking { repository.getInt(NOTE_SORT) ?: 0 }
    fun getTaskSort(): Int = runBlocking { repository.getInt(TASK_SORT) ?: 0 }
    fun getRoutineSort(): Int = runBlocking { repository.getInt(ROUTINE_SORT) ?: 4 }


}