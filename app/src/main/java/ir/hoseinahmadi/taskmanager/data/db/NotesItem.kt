package ir.hoseinahmadi.taskmanager.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotesItem(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title: String,
    val body: String? = null,
    val startTime: String,
    val endTime: String
)
