package ir.hoseinahmadi.taskmanager.repository

import ir.hoseinahmadi.taskmanager.data.db.notes.NotesItem
import ir.hoseinahmadi.taskmanager.data.db.task.TaskDao
import ir.hoseinahmadi.taskmanager.data.db.task.TaskItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val dao: TaskDao
) {

    suspend fun upsertTask(item: TaskItem) {
        dao.upsertTaskDao(item)
    }

    fun getAllTaskItem(): Flow<List<TaskItem>> = dao.getAllTaskItem()

    fun getSingleTaskById(id: Int) :Flow<TaskItem> = dao.getSingleTaskById(id)

}