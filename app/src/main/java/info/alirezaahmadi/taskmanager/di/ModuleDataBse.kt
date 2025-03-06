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
import info.alirezaahmadi.taskmanager.data.db.notes.NotesDao
import info.alirezaahmadi.taskmanager.data.db.routine.RoutineDao
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
        name = "بیحبیتنخ"
    ).addMigrations(MIGRATION_1_2).addMigrations(MIGRATION_2_3).build()


    @Provides
    @Singleton
    fun provideNotesDao(dataBase: DataBase): NotesDao = dataBase.NotesDao()

    @Provides
    @Singleton
    fun provideTaskDao(dataBase: DataBase): TaskDao = dataBase.TaskDao()

    @Provides
    @Singleton
    fun provideRoutineDao(dataBase: DataBase): RoutineDao = dataBase.RoutineDao()


}