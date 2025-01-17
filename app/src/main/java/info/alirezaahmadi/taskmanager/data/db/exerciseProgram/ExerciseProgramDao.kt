package info.alirezaahmadi.taskmanager.data.db.exerciseProgram

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseProgramDao {
    @Upsert
    suspend fun upsertExerciseProgram(exerciseProgramItem: ExerciseProgramItem)

    @Query("select * from ExerciseProgramItem")
    fun getAllExerciseProgram(): Flow<List<ExerciseProgramItem>>

    @Query("delete from ExerciseProgramItem where id=:itemId")
    suspend fun deletedExerciseProgram(itemId:Int)

    @Query("select * from ExerciseProgramItem where id=:itemId")
    fun getExerciseProgram(itemId:Int): Flow<ExerciseProgramItem?>
}