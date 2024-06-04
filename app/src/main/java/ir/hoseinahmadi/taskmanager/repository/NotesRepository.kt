package ir.hoseinahmadi.taskmanager.repository

import ir.hoseinahmadi.taskmanager.data.db.notes.NotesDao
import ir.hoseinahmadi.taskmanager.data.db.notes.NotesItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val dao: NotesDao
) {
    suspend fun upsertNotesItem(item: NotesItem) {
        dao.upsertDao(item)
    }

    fun getAllNoteItem(): Flow<List<NotesItem>> = dao.allNotesItem()

    fun getItemById(id: Int) = dao.getItemById(id)
    suspend fun deleteTask(item: NotesItem){
        dao.deleteTask(item)
    }

}