package me.chetan.life

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(ctx: Context?, intent: Intent?) {
        val type=intent?.getStringExtra("Type")
        val taskName=intent?.getStringExtra("TaskName")
        when(type){
            "SmallTaskRelative" -> {
                sendNotification(ctx!!, "Reminder for $taskName")
            }
        }
    }

    fun sendNotification(ctx: Context, text: String){
        val notificationManager=ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            "Reminders",
            "Reminders",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Channel for exact app reminders"
        }
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(ctx, "Reminders")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("App Reminder")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(0, notification)
    }

}