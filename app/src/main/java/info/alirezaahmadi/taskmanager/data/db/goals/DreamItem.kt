package info.alirezaahmadi.taskmanager.data.db.goals

import androidx.room.PrimaryKey

data class DreamItem(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title:String,
    val description:String,
    val imageUriList: List<String>,

)
