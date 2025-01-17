package info.alirezaahmadi.taskmanager.repository

import info.alirezaahmadi.taskmanager.data.db.exerciseProgram.ExerciseProgramDao
import javax.inject.Inject

class ExerciseProgramRepository @Inject constructor(
    private val exerciseProgramDao: ExerciseProgramDao
){

}