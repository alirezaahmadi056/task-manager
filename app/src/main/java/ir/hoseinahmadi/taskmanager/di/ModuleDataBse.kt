package ir.hoseinahmadi.taskmanager.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.hoseinahmadi.taskmanager.data.db.DataBase
import ir.hoseinahmadi.taskmanager.data.db.notes.NotesDao
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
    ).build()


    @Provides
    @Singleton
    fun provideNotesDao(dataBase: DataBase) =dataBase.NotesDao()

}