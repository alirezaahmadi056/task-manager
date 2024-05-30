package ir.hoseinahmadi.taskmanager.data.db.task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskItem(
    @PrimaryKey(autoGenerate = true)
    val id :Int=0,
    val title :String,
    val subTask :List<Task>,
    val taskColor :Int)

data class Task(
    val title: String,
    val isCompleted :Boolean,
)
