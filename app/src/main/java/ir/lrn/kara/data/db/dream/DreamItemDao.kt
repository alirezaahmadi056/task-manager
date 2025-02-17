package ir.lrn.kara.data.db.dream

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DreamItemDao {

    @Upsert
    suspend fun upsertDreamItem(dreamItem: DreamItem)
    @Query("select * from DreamItem")
    fun getAllDreamItemDao():Flow<List<DreamItem>>
    @Query("select * from DreamItem where id=:id")
    fun getDreamById(id:Int):Flow<DreamItem?>
    @Query("delete from DreamItem where id=:id")
   suspend fun deleteDreamByID(id: Int)
}