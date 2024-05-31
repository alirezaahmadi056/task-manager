package ir.hoseinahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hoseinahmadi.taskmanager.data.db.task.TaskItem
import ir.hoseinahmadi.taskmanager.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    val allItem = repository.getAllTaskItem()

     fun upsertTask(item: TaskItem) {
         viewModelScope.launch(Dispatchers.IO) { repository.upsertTask(item) }
    }

    fun getSingleTaskById(id: Int):Flow<TaskItem>  =  repository.getSingleTaskById(id)

}