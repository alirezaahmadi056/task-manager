package info.alirezaahmadi.taskmanager.data.alarm

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import info.alirezaahmadi.taskmanager.MainActivity
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.util.Constants
import java.util.Calendar
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("1212", "receiver")

        when (intent?.action) {
            Constants.ACTION_ROUTINE_RECEIVER -> {
                val routineId = intent.getIntExtra("ROUTINE_ID", 0) ?: 0
                val routineTitle = intent.getStringExtra("ROUTINE_TITLE") ?: ""
                context?.let { ctx ->
                    createFullNotification(
                        context = ctx,
                        title = routineTitle,
                        id = routineId,
                        notificationAction = Constants.ACTION_ROUTINE_RECEIVER
                    )
                }
            }

            Constants.ACTION_TASK_RECEIVER -> {
                val taskId = intent.getIntExtra("TASK_ID", 0)
                val taskTitle = intent.getStringExtra("TASK_TITLE") ?: ""
                context?.let { ctx ->
                    createFullNotification(
                        context = ctx,
                        title = taskTitle,
                        id = taskId,
                        notificationAction = Constants.ACTION_TASK_RECEIVER
                    )
                }
            }
        }
    }


}

fun createFullNotification(
    context: Context,
    title: String,
    id: Int,
    notificationAction: String,
) {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) return


    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val calendar = Calendar.getInstance().apply {
        add(Calendar.WEEK_OF_YEAR, 1)
    }
    val nextAlarmId = (id * 3) + Random.nextInt(1, 10)

    val intent = Intent(context, MainActivity::class.java).apply {
        action = notificationAction
        putExtra("TASK_ID", id)
    }
    val fullScreenPendingIntent = PendingIntent.getActivity(
        context, 0,
        intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    /* val customView = RemoteViews(context.packageName, R.layout.custom_notification)
     customView.setTextViewText(R.id.notification_title, text)
     customView.setTextViewText(R.id.notification_message, title)*/

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        nextAlarmId.toInt(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notificationBuilder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_app)
        .setContentTitle(if (notificationAction == Constants.ACTION_ROUTINE_RECEIVER) "یادآوری روتین" else "یادآوری وظیفه")
        .setContentText(title)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_ALARM)
        .setContentIntent(fullScreenPendingIntent)
        .setAutoCancel(true)


    with(NotificationManagerCompat.from(context)) {
        notify(id, notificationBuilder.build())
    }
    if (notificationAction == Constants.ACTION_ROUTINE_RECEIVER) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

}

