package info.alirezaahmadi.taskmanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import info.alirezaahmadi.taskmanager.data.db.curriculum.CurriculumDao
import info.alirezaahmadi.taskmanager.data.db.curriculum.CurriculumItem
import info.alirezaahmadi.taskmanager.data.db.dream.DreamItem
import info.alirezaahmadi.taskmanager.data.db.dream.DreamItemDao
import info.alirezaahmadi.taskmanager.data.db.exerciseProgram.ExerciseProgramDao
import info.alirezaahmadi.taskmanager.data.db.exerciseProgram.ExerciseProgramItem
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsItem
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsItemDao
import info.alirezaahmadi.taskmanager.data.db.medicine.MedicineDao
import info.alirezaahmadi.taskmanager.data.db.medicine.MedicineItem
import info.alirezaahmadi.taskmanager.data.db.notes.NotesDao
import info.alirezaahmadi.taskmanager.data.db.notes.NotesItem
import info.alirezaahmadi.taskmanager.data.db.routine.WeeklyRoutineDao
import info.alirezaahmadi.taskmanager.data.db.routine.RoutineItem
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinRoutineDao
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinRoutineItem
import info.alirezaahmadi.taskmanager.data.db.task.TaskDao
import info.alirezaahmadi.taskmanager.data.db.task.TaskItem

@Database(
    entities = [
        NotesItem::class,
        TaskItem::class,
        RoutineItem::class,
        SkinRoutineItem::class,
        ExerciseProgramItem::class,
        GoalsItem::class,
        MedicineItem::class,
        DreamItem::class,
        CurriculumItem::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(UriTypeConverter::class, SubTaskTypeConverter::class, DayUriConverter::class)
abstract class DataBase : RoomDatabase() {

    abstract fun NotesDao(): NotesDao
    abstract fun TaskDao(): TaskDao
    abstract fun WeeklyRoutineDao(): WeeklyRoutineDao
    abstract fun SkinRoutineDao(): SkinRoutineDao
    abstract fun ExerciseProgramDao(): ExerciseProgramDao
    abstract fun GoalsItemDao(): GoalsItemDao
    abstract fun MedicineDao(): MedicineDao
    abstract fun DreamItemDao(): DreamItemDao
    abstract fun CurriculumDao(): CurriculumDao
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
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS RoutineItem (
                id INTEGER PRIMARY KEY NOT NULL,
                title TEXT NOT NULL DEFAULT '',
                taskColor INTEGER NOT NULL DEFAULT 1,
                days TEXT NOT NULL DEFAULT '[]',
                triggerAlarmTime INTEGER NOT NULL DEFAULT 0,
                enableAlarm INTEGER NOT NULL DEFAULT 0,
                time TEXT NOT NULL DEFAULT ''   )
            """.trimIndent()
                )
            }
        }

    }


}