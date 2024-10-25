package info.alirezaahmadi.taskmanager.viewModel

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.alarm.AlarmReceiver
import info.alirezaahmadi.taskmanager.util.Constants
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmManager: AlarmManager
) : ViewModel() {


    @SuppressLint("NewApi")
    fun setNotificationAlarm(context: Context, triggerTime: Long, id: Int, title: String) {
//        if (triggerTime < System.currentTimeMillis()) return
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = Constants.ACTION_ALARM_RECEIVER
            putExtra("TASK_ID", id)
            putExtra("TASK_TITLE", title)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent,
        )
    }

}