package info.alirezaahmadi.taskmanager.repository

import info.alirezaahmadi.taskmanager.data.db.routine.RoutineDao
import info.alirezaahmadi.taskmanager.data.db.routine.RoutineItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoutineRepository @Inject constructor(
    private val dao: RoutineDao
) {

    suspend fun upsertRoutine(item: RoutineItem) {
        dao.upsertRoutine(item)
    }

    suspend fun deletedById(itemId: Int) {
        dao.deletedById(itemId)
    }

    fun getAllRoutine(): Flow<List<RoutineItem>> = dao.getAllRoutine()

}