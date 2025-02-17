package ir.lrn.kara.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.lrn.kara.data.db.DataBase
import ir.lrn.kara.data.db.dream.DreamItemDao
import ir.lrn.kara.data.db.exerciseProgram.ExerciseProgramDao
import ir.lrn.kara.data.db.goals.GoalsItemDao
import ir.lrn.kara.data.db.medicine.MedicineDao
import ir.lrn.kara.data.db.notes.NotesDao
import ir.lrn.kara.data.db.routine.WeeklyRoutineDao
import ir.lrn.kara.data.db.skinRoutine.SkinRoutineDao
import ir.lrn.kara.data.db.task.TaskDao
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

    @Provides
    @Singleton
    fun provideMedicineDao(dataBase: DataBase): MedicineDao = dataBase.MedicineDao()

    @Provides
    @Singleton
    fun provideDreamDao(dataBase: DataBase): DreamItemDao = dataBase.DreamItemDao()

    @Provides
    @Singleton
    fun provideCurriculumDao(dataBase: DataBase) = dataBase.CurriculumDao()

}