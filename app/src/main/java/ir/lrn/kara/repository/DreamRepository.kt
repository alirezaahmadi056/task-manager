package ir.lrn.kara.repository

import ir.lrn.kara.data.db.dream.DreamItem
import ir.lrn.kara.data.db.dream.DreamItemDao
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