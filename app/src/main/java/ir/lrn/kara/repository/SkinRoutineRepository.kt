package ir.lrn.kara.repository

import ir.lrn.kara.data.db.skinRoutine.SkinRoutineDao
import ir.lrn.kara.data.db.skinRoutine.SkinRoutineItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SkinRoutineRepository @Inject constructor(
    private val skinRoutineDao: SkinRoutineDao
) {

    suspend fun upsertSkinRoutine(skinRoutineItem: SkinRoutineItem) {
        skinRoutineDao.upsertSkinRoutine(skinRoutineItem)
    }

    fun getAllSkinRoutine(): Flow<List<SkinRoutineItem>> = skinRoutineDao.getAllSkinRoutine()
    suspend fun deletedSkinRoutine(id: Int) {
        skinRoutineDao.deletedSkinRoutine(id)
    }

    fun getSkinRoutine(itemId: Int): Flow<SkinRoutineItem?> = skinRoutineDao.getSkinRoutine(itemId)

}