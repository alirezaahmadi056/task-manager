package ir.lrn.kara.repository

import ir.lrn.kara.data.db.exerciseProgram.ExerciseProgramDao
import ir.lrn.kara.data.db.exerciseProgram.ExerciseProgramItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseProgramRepository @Inject constructor(
    private val exerciseProgramDao: ExerciseProgramDao
){

    suspend fun upsertExercise(item: ExerciseProgramItem) { exerciseProgramDao.upsertExerciseProgram(item) }

    suspend fun deletedById(itemId: Int) { exerciseProgramDao.deletedExerciseProgram(itemId) }
    fun getAllExercise(): Flow<List<ExerciseProgramItem>> = exerciseProgramDao.getAllExerciseProgram()
    fun getSkinRoutine(itemId: Int): Flow<ExerciseProgramItem?> = exerciseProgramDao.getExerciseProgram(itemId)

}