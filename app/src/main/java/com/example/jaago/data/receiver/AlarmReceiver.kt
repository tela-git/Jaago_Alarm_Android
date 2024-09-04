package com.example.jaago.data.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.jaago.R

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
      showNotification(context)
}
    private fun showNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder = NotificationCompat
            .Builder(context, "alarm_channel_id")
            .setSmallIcon(R.drawable.alarm_icon)
            .setContentTitle("Jaago")
            .setContentText("Jaago Jaago")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel = NotificationChannel("alarm_channel_id","Alarm Notifications", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(101, notificationBuilder.build())
    }
}