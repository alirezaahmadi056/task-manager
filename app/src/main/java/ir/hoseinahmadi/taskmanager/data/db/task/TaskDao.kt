package ir.hoseinahmadi.taskmanager.data.db.task

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun upsertTaskDao(item: TaskItem)

    @Query("select * from taskitem")
    fun getAllTaskItem():Flow<List<TaskItem>>

}