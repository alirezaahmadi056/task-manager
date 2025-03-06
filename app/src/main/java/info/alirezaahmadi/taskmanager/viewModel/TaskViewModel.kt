package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.db.task.TaskItem
import info.alirezaahmadi.taskmanager.data.db.task.TaskItemType
import info.alirezaahmadi.taskmanager.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
) : ViewModel() {

    private val _allNormalTask = MutableStateFlow<List<TaskItem>>(emptyList())
    val allNormalTask:StateFlow<List<TaskItem>> = _allNormalTask.asStateFlow()

    private val _allFastTask = MutableStateFlow<List<TaskItem>>(emptyList())
    val allFastTask: StateFlow<List<TaskItem>> = _allFastTask.asStateFlow()

    fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllTaskItem(TaskItemType.NORMAL.name).collectLatest { normal ->
                _allNormalTask.emit(normal)
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllTaskItem(TaskItemType.FAST.name).collectLatest {fast->
                _allFastTask.emit(fast)
            }

        }
    }

    fun upsertTask(item: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) { repository.upsertTask(item) }
    }

    fun getSingleTaskById(id: Int): Flow<TaskItem> = repository.getSingleTaskById(id)

    fun deleteTask(item: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(item)
        }
    }


}