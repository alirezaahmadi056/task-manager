package info.alirezaahmadi.taskmanager.data.alarm

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import info.alirezaahmadi.taskmanager.MainActivity
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.util.Constants.CHANNEL_ID

class AlarmReceiver() : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getIntExtra("TASK_ID", 0)
        val taskTitle = intent.getStringExtra("TASK_TITLE")
        val channelId = "alarm_id"

        // go to the app
        val myIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, taskId, myIntent,
            Intent.FILL_IN_ACTION or PendingIntent.FLAG_IMMUTABLE
        )

        //vibrate pattern
        val vibrate = longArrayOf(0, 100, 200, 300)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.clock_five)
            .setContentTitle("h")
            .setContentText(taskTitle)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
//            .setSound(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.sound_1))
            .setOnlyAlertOnce(true)
            .setVibrate(vibrate)

        with(NotificationManagerCompat.from(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Check for permission on Android 13 and above
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    notify(taskId, builder.build()) // Use task ID to have unique notifications
                }
            } else {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.SET_ALARM
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    notify(taskId, builder.build()) // Use task ID to have unique notifications
                }
            }

        }
    }
}