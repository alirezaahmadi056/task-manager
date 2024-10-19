package info.alirezaahmadi.taskmanager.viewModel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.db.task.TaskItem
import info.alirezaahmadi.taskmanager.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val alarmManager: AlarmManager,
    private val alarmIntent: Intent
) : ViewModel() {

    val allItem = repository.getAllTaskItem()

     fun upsertTask(item: TaskItem) {
         viewModelScope.launch(Dispatchers.IO) { repository.upsertTask(item) }
    }

    fun getSingleTaskById(id: Int):Flow<TaskItem>  =  repository.getSingleTaskById(id)

    fun deleteTask(item: TaskItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(item)
        }
    }
    fun scheduleNotification(context: Context, triggerTime: Calendar, id: Int, title:String) {


        alarmIntent.putExtra("TASK_ID", id)
        alarmIntent.putExtra("TASK_TITLE",title)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime.timeInMillis,
            pendingIntent
        )
    }


    fun cancelNotification(context: Context, taskId: Int){

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId, // Unique request code, use task ID for simplicity
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }
    fun vibrate(context: Context){
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) { // Vibrator availability checking
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE)) // New vibrate method for API Level 26 or higher
            } else {
                vibrator.vibrate(200) // Vibrate method for below API Level 26
            }
        }
    }

}