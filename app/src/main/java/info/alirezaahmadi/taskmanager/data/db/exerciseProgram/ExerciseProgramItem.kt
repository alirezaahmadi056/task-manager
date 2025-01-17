package info.alirezaahmadi.taskmanager.data.db.exerciseProgram

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseProgramItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val name: String,
    val description: String,
    val deyWeek:List<String>,
    val setNumber:Int,
    val repetitionSetNumber:Int,
    val disability:Boolean,
    val dropdown:Boolean,
    val time :Int,
)