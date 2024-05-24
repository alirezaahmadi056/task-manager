package ir.hoseinahmadi.taskmanager.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.hoseinahmadi.taskmanager.data.db.notes.NotesDao
import ir.hoseinahmadi.taskmanager.data.db.notes.NotesItem

@Database(entities = [NotesItem::class], version = 1,exportSchema =false)
abstract class DataBase:RoomDatabase() {

    abstract fun NotesDao():NotesDao

}