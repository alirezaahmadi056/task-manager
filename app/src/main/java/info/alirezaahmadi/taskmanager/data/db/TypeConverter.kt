package info.alirezaahmadi.taskmanager.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import info.alirezaahmadi.taskmanager.data.db.task.Task


class TypeConverter {

    @TypeConverter
    fun fromSubtaskList(subtaskList: List<Task>): String {
        val json = Gson().toJson(subtaskList)
        return json
    }

    @TypeConverter
    fun toSubtaskList(jsonString: String): List<Task> {
        val subtaskList =
            Gson().fromJson<List<Task>>(
                jsonString,
                object : TypeToken<List<Task>>() {}.type)
        return subtaskList
    }
}