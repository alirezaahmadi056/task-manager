package info.alirezaahmadi.taskmanager.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import info.alirezaahmadi.taskmanager.data.db.DataBase
import info.alirezaahmadi.taskmanager.data.db.DataBase.Companion.MIGRATION_1_2
import info.alirezaahmadi.taskmanager.data.db.DataBase.Companion.MIGRATION_2_3
import info.alirezaahmadi.taskmanager.data.db.exerciseProgram.ExerciseProgramDao
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsItemDao
import info.alirezaahmadi.taskmanager.data.db.notes.NotesDao
import info.alirezaahmadi.taskmanager.data.db.routine.WeeklyRoutineDao
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinRoutineDao
import info.alirezaahmadi.taskmanager.data.db.task.TaskDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleDataBse {

    @Provides
    @Singleton
    fun provideDataBse(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context = context,
        klass = DataBase::class.java,
        name = "my_db"
    ).build()


    @Provides
    @Singleton
    fun provideNotesDao(dataBase: DataBase): NotesDao = dataBase.NotesDao()

    @Provides
    @Singleton
    fun provideTaskDao(dataBase: DataBase): TaskDao = dataBase.TaskDao()

    @Provides
    @Singleton
    fun provideWeeklyRoutineDao(dataBase: DataBase): WeeklyRoutineDao = dataBase.WeeklyRoutineDao()

    @Provides
    @Singleton
    fun provideSkinRoutineDao(dataBase: DataBase): SkinRoutineDao = dataBase.SkinRoutineDao()

    @Provides
    @Singleton
    fun provideExerciseProgramDao(dataBase: DataBase): ExerciseProgramDao =
        dataBase.ExerciseProgramDao()

    @Provides
    @Singleton
    fun provideGoalsDao(dataBase: DataBase): GoalsItemDao = dataBase.GoalsItemDao()

}