package info.alirezaahmadi.taskmanager.viewModel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.alarm.AlarmReceiver
import info.alirezaahmadi.taskmanager.data.db.routine.RoutineItem
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.util.TaskHelper
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmManager: AlarmManager
) : ViewModel() {


    fun setNotificationAlarm(context: Context, triggerTime: Long, id: Int, title: String) {
        if (triggerTime < System.currentTimeMillis()) return
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = Constants.ACTION_TASK_RECEIVER
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

    fun canselNotificationAlarm(context: Context, id: Int) {
        Log.i("1515", "id canselNotificationAlarm $id")

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = Constants.ACTION_TASK_RECEIVER
            putExtra("TASK_ID", id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    fun setWeeklyAlarms(context: Context, routineItem: RoutineItem) {
        val time = TaskHelper.convertMillisToTimeList(routineItem.triggerAlarmTime)

        routineItem.days.forEach { day ->
            val dayOfWeek = Constants.daysMap[day] ?: return@forEach
            val uniqueId = routineItem.id * 10 + dayOfWeek
            val calendar = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_WEEK, dayOfWeek)
                set(Calendar.HOUR_OF_DAY, time[0])
                set(Calendar.MINUTE, time[1])
                set(Calendar.SECOND, 0)

                if (timeInMillis < System.currentTimeMillis()) {
                    add(Calendar.WEEK_OF_YEAR, 1)
                }
            }

            val intent = Intent(context, AlarmReceiver::class.java).apply {
                action = Constants.ACTION_ROUTINE_RECEIVER
                putExtra("ROUTINE_ID", uniqueId)
                putExtra("ROUTINE_TITLE", routineItem.title)
            }


            val pendingIntent = PendingIntent.getBroadcast(
                context,
                uniqueId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )


            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent,
            )

        }
    }
    fun cancelWeeklyAlarms(context: Context, routineItem: RoutineItem) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        routineItem.days.forEach { day ->
            val dayOfWeek = Constants.daysMap[day] ?: return@forEach
            val uniqueId = routineItem.id * 10 + dayOfWeek

            val intent = Intent(context, AlarmReceiver::class.java).apply {
                action = Constants.ACTION_ROUTINE_RECEIVER
                putExtra("ROUTINE_ID", routineItem.id)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                uniqueId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            Log.i("1515","cancel alarm title :${routineItem.title} week:${day}")
            alarmManager.cancel(pendingIntent)
        }
    }

}