package info.alirezaahmadi.taskmanager.data.db.notes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Upsert
    suspend fun upsertDao(item: NotesItem)

    @Query("select * from notesitem")
    fun allNotesItem(): Flow<List<NotesItem>>

    @Query("select * from notesitem where id=:id")
    fun getItemById(id: Int): Flow<NotesItem>

    @Delete
    suspend fun deleteTask(item: NotesItem)


}