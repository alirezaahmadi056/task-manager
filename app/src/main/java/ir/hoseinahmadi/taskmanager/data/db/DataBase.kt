package ir.hoseinahmadi.taskmanager.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.hoseinahmadi.taskmanager.data.db.notes.NotesDao
import ir.hoseinahmadi.taskmanager.data.db.notes.NotesItem
import ir.hoseinahmadi.taskmanager.data.db.notes.UriTypeConverter

@Database(entities = [NotesItem::class], version = 1,exportSchema =false)
@TypeConverters(UriTypeConverter::class) // اضافه کردن مبدل نوع
abstract class DataBase:RoomDatabase() {

    abstract fun NotesDao():NotesDao

}