package info.alirezaahmadi.taskmanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import info.alirezaahmadi.taskmanager.data.db.notes.NotesDao
import info.alirezaahmadi.taskmanager.data.db.notes.NotesItem
import info.alirezaahmadi.taskmanager.data.db.task.TaskDao
import info.alirezaahmadi.taskmanager.data.db.task.TaskItem

@Database(entities = [NotesItem::class, TaskItem::class], version = 2, exportSchema = false)
@TypeConverters(UriTypeConverter::class, SubTaskTypeConverter::class)
abstract class DataBase : RoomDatabase() {

    abstract fun NotesDao(): NotesDao
    abstract fun TaskDao(): TaskDao


    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("DROP TABLE IF EXISTS TaskItem")

                db.execSQL(
                    """
                CREATE TABLE IF NOT EXISTS TaskItem (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    title TEXT NOT NULL,
                    subTask TEXT NOT NULL DEFAULT '[]',
                    body TEXT NOT NULL DEFAULT '',
                    taskColor INTEGER NOT NULL DEFAULT 1,
                    createTime TEXT NOT NULL DEFAULT '',
                    completedTime TEXT NOT NULL DEFAULT '',
                    triggerAlarmTime INTEGER NOT NULL DEFAULT 0,
                    type TEXT NOT NULL DEFAULT 'NORMAL'
                )
                """.trimIndent()
                )
            }
        }
    }


}