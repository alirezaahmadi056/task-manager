package info.alirezaahmadi.taskmanager.data.db

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import info.alirezaahmadi.taskmanager.data.db.task.Task


class SubTaskTypeConverter {

    @TypeConverter
    fun fromSubtaskList(subtaskList: List<Task>): String {
        val json = Gson().toJson(subtaskList)
        return json
    }

    @TypeConverter
    fun toSubtaskList(jsonString: String?): List<Task> {
        return try {
            Gson().fromJson(jsonString, object : TypeToken<List<Task>>() {}.type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

}

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

class DayUriConverter {
    @TypeConverter
    fun fromDayWeekList(dayList: List<String>): String {
        return Gson().toJson(dayList)
    }

    @TypeConverter
    fun toDayWeekList(json: String): List<String> {
        return try {
            Gson().fromJson(json, object : TypeToken<List<String>>() {}.type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}