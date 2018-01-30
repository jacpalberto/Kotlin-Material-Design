package com.example.pc_3.kotlinmaterialdesign

import android.app.Activity
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.view.Gravity
import android.view.View
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

/**
* Created by Alberto Carrillo on 22/09/2017.
*/
var counter = 1

//ACTIVITY EXTENSIONS
inline fun <reified T : Any> Activity.launchActivity(
        requestCode: Int = -1,
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
        Intent(context, T::class.java)

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.customToast(message: String, randomTextColor: Boolean = false, randomBackgroundColor: Boolean = false,
                        randomPosition: Boolean = false) {
    val toast = Toast.makeText(this, "   $message   ", Toast.LENGTH_SHORT)
    val text = toast.view.findViewById(android.R.id.message) as TextView
    if (randomBackgroundColor) toast.view.setBackgroundColor(getRandomColor())
    if (randomTextColor) text.setTextColor(getRandomColor())
    text.textSize = 17.0F
    if (randomPosition) toast.setGravity(getRandomGravity(), 0, 0)
    toast.show()
}

fun View.customSnack(message: String, randomTextColor: Boolean = false, randomBackgroundColor: Boolean = false,
                     randomPosition: Boolean = false) {
    val snack = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    val textView = snack.view.findViewById(android.support.design.R.id.snackbar_text) as TextView
    if (randomBackgroundColor) snack.view.setBackgroundColor(getRandomColor())
    if (randomTextColor) {
        snack.setAction("DONE", {})
                .setActionTextColor(getRandomColor())
        textView.setTextColor(getRandomColor())
    } else snack.setAction("DONE", {})
    if (randomPosition) {
        val params = snack.view.layoutParams as CoordinatorLayout.LayoutParams
        params.gravity = getRandomGravity()
        snack.view.layoutParams = params
    }
    snack.show()
}

fun View.snack(message: String, action: String = "", length: Int = Snackbar.LENGTH_LONG,
               l: (View) -> Unit = {}) {
    val snack = Snackbar.make(this, message, length)
    if (action.isNotEmpty()) snack.setAction(action, l)
    snack.show()
}

fun Context.createNotification(message: String) {
    val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_feedback_24dp)
            .setVibrate(longArrayOf(1000, 1000))
            .setContentTitle(getString(R.string.last_message))
            .setContentText(message)
            .setTicker(message)
            .setAutoCancel(true)
            .setColor(getRandomColor())
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
    //.setContentIntent(pendingIntent)

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(counter++, notificationBuilder.build())
}

fun Context.createCustomNotification(message: String) {
    val contentView = RemoteViews(packageName, R.layout.notification_layout)
    with(contentView) {
        setImageViewResource(R.id.alertImageView, R.drawable.ic_feedback_24dp)
        setTextViewText(R.id.tvTitle, getString(R.string.last_message))
        setTextViewText(R.id.tvMessage, message)
    }
    val mBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_feedback_24dp)
            .setContent(contentView)

    val notification = mBuilder.build()
    notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL
    notification.defaults = notification.defaults or Notification.DEFAULT_SOUND
    notification.defaults = notification.defaults or Notification.DEFAULT_VIBRATE
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(counter++, notification)
}

fun getCurrentTime(): CharSequence? {
    val date = Date()
    val strDateFormat = "hh:mm:ss a"
    val dateFormat = SimpleDateFormat(strDateFormat, Locale.getDefault())
    return dateFormat.format(date)
}

fun getRandomColor() =
        when (Random().nextInt(11 - 0) + 0) {
            0 -> Color.BLACK
            1 -> Color.YELLOW
            2 -> Color.RED
            3 -> Color.BLUE
            4 -> Color.GRAY
            5 -> Color.DKGRAY
            6 -> Color.LTGRAY
            7 -> Color.WHITE
            8 -> Color.CYAN
            9 -> Color.MAGENTA
            else -> Color.TRANSPARENT
        }

fun getRandomGravity() =
        when (Random().nextInt(9 - 0) + 0) {
            0 -> Gravity.TOP or Gravity.START
            1 -> Gravity.TOP or Gravity.CENTER
            2 -> Gravity.TOP or Gravity.END
            3 -> Gravity.CENTER or Gravity.START
            4 -> Gravity.CENTER or Gravity.CENTER
            5 -> Gravity.CENTER or Gravity.END
            6 -> Gravity.BOTTOM or Gravity.START
            7 -> Gravity.BOTTOM or Gravity.CENTER
            else -> Gravity.BOTTOM or Gravity.END
        }