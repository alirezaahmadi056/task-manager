package info.alirezaahmadi.taskmanager.repository

import info.alirezaahmadi.taskmanager.data.db.exerciseProgram.ExerciseProgramDao
import info.alirezaahmadi.taskmanager.data.db.exerciseProgram.ExerciseProgramItem
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