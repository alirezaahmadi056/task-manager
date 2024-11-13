package info.alirezaahmadi.taskmanager.data.db.routine

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {

    @Upsert
    suspend fun upsertRoutine(item: RoutineItem)

    @Query("delete  from routineitem where id=:itemId")
    suspend fun deletedById(itemId: Int)

    @Query("select * from routineitem")
    fun getAllRoutine():Flow<List<RoutineItem>>
}