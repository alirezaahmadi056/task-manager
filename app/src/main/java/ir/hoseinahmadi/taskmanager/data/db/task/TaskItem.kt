package ir.hoseinahmadi.taskmanager.data.db.task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskItem(
    @PrimaryKey(autoGenerate = true)
    val id :Int=0,
    val title :String="",
    val subTask :List<Task> = emptyList(),
    val body :String ="",
    val taskColor :Int=1)

data class Task(
    var title: String="",
    var isCompleted :Boolean=false,
)
