package info.alirezaahmadi.taskmanager.data.alarm

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import info.alirezaahmadi.taskmanager.MainActivity
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.util.Constants.CHANNEL_ID
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val taskId = intent?.getIntExtra("TASK_ID", 0) ?: 0
        val taskTitle = intent?.getStringExtra("TASK_TITLE") ?: ""
        if (intent?.action==Constants.ACTION_ALARM_RECEIVER){
            context?.let { ctx->
                createFullNotification(ctx,taskTitle,taskId)
            }
        }
    }


}

fun createFullNotification(
    context: Context,
    title: String,
    id:Int
) {
    val intent = Intent(context, MainActivity::class.java).apply {
        action ="NNN"
        putExtra("TASK_ID", id)
    }
    val fullScreenPendingIntent = PendingIntent.getActivity(
        context, 0,
        intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val notificationBuilder =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_app)
            .setContentTitle("یاد آور")
            .setContentText(title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setContentIntent(fullScreenPendingIntent)
            .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notify(Random.nextInt(), notificationBuilder.build())
        }
    }
}