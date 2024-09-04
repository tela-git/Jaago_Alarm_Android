package com.example.jaago

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.jaago.data.receiver.AlarmReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun setAlarm(triggerTime: Long) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, AlarmReceiver::class.java)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)

        } catch (e: SecurityException) {
            Log.e("AlarmHelper", "SecurityException: ${e.message}")
        } catch (e: Exception) {
            Log.e("AlarmHelper", "Exception: ${e.message}")
        }
    }
}
