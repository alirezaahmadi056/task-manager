package info.alirezaahmadi.taskmanager.data.db.goals

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalsItemDao {

    @Upsert
    suspend fun upsertGoals(goalsItem: GoalsItem)

    @Query("SELECT * FROM GoalsItem WHERE id = :id")
     fun getGoalsById(id: Int): Flow<GoalsItem?>

    @Query("SELECT * FROM GoalsItem")
    fun getAllGoals(): Flow<List<GoalsItem>>

    @Query("DELETE FROM GoalsItem WHERE id = :id")
    fun deleteGoalsById(id: Int)
}