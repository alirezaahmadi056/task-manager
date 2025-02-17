package ir.lrn.kara.repository

import ir.lrn.kara.data.db.task.TaskDao
import ir.lrn.kara.data.db.task.TaskItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val dao: TaskDao
) {

    suspend fun upsertTask(item: TaskItem) {
        dao.upsertTaskDao(item)
    }

    fun getAllTaskItem(type:String): Flow<List<TaskItem>> = dao.getAllTaskItem(type)

    fun getSingleTaskById(id: Int): Flow<TaskItem> = dao.getSingleTaskById(id)

    suspend fun deleteTask(item: TaskItem) = dao.deleteTask(item)


}