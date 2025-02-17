package ir.lrn.kara.data.db.curriculum

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurriculumItem(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title:String,
    val description:String,
    val startTime:String,
    val endTime:String,
    val dayWeek:List<String>
)