package ir.lrn.kara.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.lrn.kara.data.dataStore.DataStoreRepositoryImpl
import ir.lrn.kara.data.model.FirstRouteData
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
        const val ENABLED_ROUTE_IDS = "ENABLED_ROUTE_IDS"
        const val SHOW_PAGER ="SHOW_PAGER"
    }
    fun saveShowPager(value: Boolean) {
        viewModelScope.launch {
            repository.putBoolean(SHOW_PAGER, value)
        }
    }
    fun getShowPager(): Boolean = runBlocking {
        repository.getBoolean(SHOW_PAGER) == true
    }

    fun saveEnabledRoutes(ids: Set<Int>) {
        viewModelScope.launch {
            val stringIds = ids.map { it.toString() }.toSet()
            repository.putStringSet(ENABLED_ROUTE_IDS, stringIds)
        }
    }
    fun getEnabledRoutes(): Set<Int> = runBlocking {
        val defaultIds = FirstRouteData.fullFirstData.map { it.id }.toSet()
        val stringSet = repository.getStringSet(ENABLED_ROUTE_IDS)
        stringSet?.map { it.toInt() }?.toSet() ?: defaultIds
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