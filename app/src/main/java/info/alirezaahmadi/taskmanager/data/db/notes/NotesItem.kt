package info.alirezaahmadi.taskmanager.data.db.notes

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter


class UriTypeConverter {
    @TypeConverter
    fun fromUri(uriList: List<Uri>?): String? {
        return uriList?.joinToString(separator = ",") { it.toString() }
    }

    @TypeConverter
    fun toUri(uriString: String?): List<Uri>? {
        return uriString?.split(",")?.map { Uri.parse(it) }
    }
}

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
    val createDate: String = "",
    val createTime: String = "",
)


