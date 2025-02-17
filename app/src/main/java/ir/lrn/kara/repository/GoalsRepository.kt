package ir.lrn.kara.repository

import ir.lrn.kara.data.db.goals.GoalsItem
import ir.lrn.kara.data.db.goals.GoalsItemDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoalsRepository @Inject constructor(
    private val dao: GoalsItemDao
) {
    suspend fun upsertGoals(goalsItem: GoalsItem) = dao.upsertGoals(goalsItem)
    fun getGoalsById(id: Int): Flow<GoalsItem?> = dao.getGoalsById(id)
    fun getAllGoals(): Flow<List<GoalsItem>> = dao.getAllGoals()
  suspend  fun deletedGoalsById(id: Int) = dao.deleteGoalsById(id)
}
