package ir.lrn.kara.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ir.lrn.kara.data.db.curriculum.CurriculumDao
import ir.lrn.kara.data.db.curriculum.CurriculumItem
import ir.lrn.kara.data.db.dream.DreamItem
import ir.lrn.kara.data.db.dream.DreamItemDao
import ir.lrn.kara.data.db.exerciseProgram.ExerciseProgramDao
import ir.lrn.kara.data.db.exerciseProgram.ExerciseProgramItem
import ir.lrn.kara.data.db.goals.GoalsItem
import ir.lrn.kara.data.db.goals.GoalsItemDao
import ir.lrn.kara.data.db.medicine.MedicineDao
import ir.lrn.kara.data.db.medicine.MedicineItem
import ir.lrn.kara.data.db.notes.NotesDao
import ir.lrn.kara.data.db.notes.NotesItem
import ir.lrn.kara.data.db.routine.WeeklyRoutineDao
import ir.lrn.kara.data.db.routine.RoutineItem
import ir.lrn.kara.data.db.skinRoutine.SkinRoutineDao
import ir.lrn.kara.data.db.skinRoutine.SkinRoutineItem
import ir.lrn.kara.data.db.task.TaskDao
import ir.lrn.kara.data.db.task.TaskItem

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