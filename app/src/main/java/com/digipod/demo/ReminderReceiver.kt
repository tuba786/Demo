package com.digipod.demo


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class ReminderReceiver : BroadcastReceiver() {
    private lateinit var mp: MediaPlayer

    companion object {
        const val ACTION_STOP_ALARM = "com.digipod.demo.STOP_ALARM"
    }

    override fun onReceive(context: Context, intent: Intent) {
        var action = intent.action
        if (action == ACTION_STOP_ALARM) {
            stopMediaPlayer()
            return
        }

        mp = MediaPlayer.create(context, R.raw.alarm_beep)
        mp.start()

        val name = intent.getStringExtra("name")
        val dose = intent.getStringExtra("dose")

        val stopIntent = Intent(context, ReminderReceiver::class.java).apply {
            action = ACTION_STOP_ALARM
        }
        val stopPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, "channel_id")
            .setContentTitle(context.getString(R.string.medication_reminder))
            .setContentText("$name $dose")
            .setSmallIcon(R.drawable.alarm)
            .setColor(ContextCompat.getColor(context, R.color.purple_500))
            .setVibrate(longArrayOf(0, 1000))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(
                R.drawable.ic_stop,
                context.getString(R.string.stop),
                stopPendingIntent
            )
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)
    }

    private fun stopMediaPlayer() {
        if (::mp.isInitialized && mp.isPlaying) {
            mp.stop()
            mp.release()
        }
    }
}
