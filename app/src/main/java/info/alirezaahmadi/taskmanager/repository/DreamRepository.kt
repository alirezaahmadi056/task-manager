package info.alirezaahmadi.taskmanager.repository

import info.alirezaahmadi.taskmanager.data.db.dream.DreamItem
import info.alirezaahmadi.taskmanager.data.db.dream.DreamItemDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DreamRepository @Inject constructor(
    private val dao: DreamItemDao
) {

    suspend fun upsertDreamItem(dreamItem: DreamItem) {
        dao.upsertDreamItem(dreamItem)
    }
    suspend fun deleteDreamByID(id: Int) {
        dao.deleteDreamByID(id)
    }
    fun getAllDreamItemDao() :Flow<List<DreamItem>> = dao.getAllDreamItemDao()
    fun getDreamById(id: Int):Flow<DreamItem?> = dao.getDreamById(id)

}