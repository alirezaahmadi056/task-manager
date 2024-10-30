package info.alirezaahmadi.taskmanager.data.db.notes

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class NotesItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val body: String = "",
    val taskColor: Int = 1,
    val phone: String = "",
    val address: String = "",
    val uri: List<Uri>? = null,
    val createTime:String="",
    val createDate: String=""
)


