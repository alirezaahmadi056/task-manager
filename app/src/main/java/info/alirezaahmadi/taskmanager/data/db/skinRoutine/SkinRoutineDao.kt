package info.alirezaahmadi.taskmanager.data.db.skinRoutine

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SkinRoutineDao {

    @Upsert
    suspend fun upsertSkinRoutine(skinRoutineItem: SkinRoutineItem)

    @Query("select * from skinroutineitem")
     fun getAllSkinRoutine():Flow<List<SkinRoutineItem>>

    @Query("delete from skinroutineitem where id=:itemId")
    suspend fun deletedSkinRoutine(itemId:Int)
}