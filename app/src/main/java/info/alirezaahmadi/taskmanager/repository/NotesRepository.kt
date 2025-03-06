package info.alirezaahmadi.taskmanager.repository

import info.alirezaahmadi.taskmanager.data.db.notes.NotesDao
import info.alirezaahmadi.taskmanager.data.db.notes.NotesItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val dao: NotesDao
) {
    suspend fun upsertNotesItem(item: NotesItem) {
        dao.upsertDao(item)
    }

    fun getAllNoteItem(): Flow<List<NotesItem>> = dao.allNotesItem()

    fun getItemById(id: Int):Flow<NotesItem> = dao.getItemById(id)
    suspend fun deleteTask(item: NotesItem){
        dao.deleteTask(item)
    }

}