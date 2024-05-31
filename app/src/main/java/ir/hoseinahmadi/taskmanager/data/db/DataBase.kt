package ir.hoseinahmadi.taskmanager.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.hoseinahmadi.taskmanager.data.db.notes.NotesDao
import ir.hoseinahmadi.taskmanager.data.db.notes.NotesItem
import ir.hoseinahmadi.taskmanager.data.db.notes.UriTypeConverter
import ir.hoseinahmadi.taskmanager.data.db.task.TaskDao
import ir.hoseinahmadi.taskmanager.data.db.task.TaskItem

@Database(entities = [NotesItem::class,TaskItem::class], version = 1,exportSchema =false)
@TypeConverters(UriTypeConverter::class,TypeConverter::class)
abstract class DataBase:RoomDatabase() {

    abstract fun NotesDao():NotesDao
    abstract fun TaskDao():TaskDao

}