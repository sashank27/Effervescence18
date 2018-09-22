package org.effervescence.app18.utils

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import org.effervescence.app18.R

class MyNotificationManager(private val mContext: Context) {

    companion object {
        const val NOTIFICATION_ID = 101
    }

    fun showNotification(title: String, notification: String, intent: Intent) {

        val pendingIntent = PendingIntent.getActivity(mContext, NOTIFICATION_ID,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder = NotificationCompat.Builder(mContext, NOTIFICATION_ID.toString())
        val mNotification = notificationBuilder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(notification)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

        mNotification.flags = Notification.FLAG_AUTO_CANCEL

        NotificationManagerCompat.from(mContext).notify(NOTIFICATION_ID, mNotification)
    }
}