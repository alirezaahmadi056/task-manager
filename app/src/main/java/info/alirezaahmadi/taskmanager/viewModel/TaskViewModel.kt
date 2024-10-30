package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.db.task.TaskItem
import info.alirezaahmadi.taskmanager.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
) : ViewModel() {

    val allItem = repository.getAllTaskItem()

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