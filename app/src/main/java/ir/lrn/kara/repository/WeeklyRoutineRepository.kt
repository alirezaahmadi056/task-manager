package ir.lrn.kara.repository

import ir.lrn.kara.data.db.routine.WeeklyRoutineDao
import ir.lrn.kara.data.db.routine.RoutineItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeeklyRoutineRepository @Inject constructor(
    private val dao: WeeklyRoutineDao
) {

    suspend fun upsertRoutine(item: RoutineItem) {
        dao.upsertRoutine(item)
    }

    suspend fun deletedById(itemId: Int) {
        dao.deletedById(itemId)
    }

    fun getAllRoutine(): Flow<List<RoutineItem>> = dao.getAllRoutine()

}