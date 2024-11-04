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
                // بررسی وجود ستون قبل از اضافه کردن آن
                if (!isColumnExists(db, "TaskItem", "createTime")) {
                    db.execSQL("ALTER TABLE TaskItem ADD COLUMN createTime TEXT NOT NULL DEFAULT ''")
                }
                if (!isColumnExists(db, "TaskItem", "completedTime")) {
                    db.execSQL("ALTER TABLE TaskItem ADD COLUMN completedTime TEXT NOT NULL DEFAULT ''")
                }
                if (!isColumnExists(db, "TaskItem", "triggerAlarmTime")) {
                    db.execSQL("ALTER TABLE TaskItem ADD COLUMN triggerAlarmTime INTEGER NOT NULL DEFAULT 0")
                }
                if (!isColumnExists(db, "TaskItem", "type")) {
                    db.execSQL("ALTER TABLE TaskItem ADD COLUMN type TEXT NOT NULL DEFAULT 'NORMAL'")
                }
            }

            // متدی برای بررسی وجود ستون در جدول
            private fun isColumnExists(
                database: SupportSQLiteDatabase,
                tableName: String,
                columnName: String
            ): Boolean {
                val cursor = database.query("PRAGMA table_info($tableName)")
                cursor.use {
                    while (it.moveToNext()) {
                        val existingColumnName = it.getString(it.getColumnIndexOrThrow("name"))
                        if (existingColumnName == columnName) {
                            return true
                        }
                    }
                }
                return false
            }
        }
    }


}