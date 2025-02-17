package ir.lrn.kara.data.db.dream

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DreamItem(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title:String,
    val description:String,
    val imageUriList: List<String>,
    val coverUri:String,
    val isCompleted:Boolean=false,
)
