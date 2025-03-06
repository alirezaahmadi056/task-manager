package info.alirezaahmadi.taskmanager.data.db.routine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoutineItem(
    @PrimaryKey
    val id: Int=0,
    val title: String="",
    val taskColor: Int=1,
    val days: List<String> = emptyList(),
    val triggerAlarmTime: Long=0L,
    val enableAlarm:Boolean=false,
    val time:String=""
)