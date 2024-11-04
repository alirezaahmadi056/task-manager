package info.alirezaahmadi.taskmanager.data.db.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun upsertTaskDao(item: TaskItem)

    @Query("select * from taskitem where type=:type")
    fun getAllTaskItem(type:String): Flow<List<TaskItem>>

    @Query("select * from taskitem where id =:id")
    fun getSingleTaskById(id: Int): Flow<TaskItem>

    @Delete
    suspend fun deleteTask(item: TaskItem)

}