package com.example.pc_3.kotlinmaterialdesign.messages

import android.app.Notification
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import com.example.pc_3.kotlinmaterialdesign.R
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Alberto Carrillo on 22/09/2017.
 */
var inboxStyle = NotificationCompat.InboxStyle()
const val NOTIFICATION_ID = 237
private var counter = 1
private var value = 0

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

fun Context.customLayoutToast(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
    val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val view = inflater.inflate(R.layout.toast_layout, null)
    val text = view.findViewById(R.id.text) as TextView
    text.text = message
    view.setBackgroundResource(R.drawable.fill_animation_button)
    toast.view = view
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

fun Context.createRichNotification(message: String) {
    val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_feedback_24dp)
            .setVibrate(longArrayOf(1000, 1000))
            .setContentTitle(getString(R.string.last_message))
            .setContentText(message)
            .setTicker(message)
            .setAutoCancel(true)
            .setColor(getRandomColor())
            .setStyle(NotificationCompat.BigTextStyle().bigText(getString(R.string.lorem_paragraph)))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(counter++, notificationBuilder.build())
}

fun Context.createBigPictureNotification(message: String) {
    val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_feedback_24dp)
            .setVibrate(longArrayOf(1000, 1000))
            .setContentTitle(getString(R.string.your_picture))
            .setContentText(message)
            .setTicker(message)
            .setAutoCancel(true)
            .setColor(getRandomColor())
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(
                    parseDrawableToBitmap(resources, R.drawable.img_bgnd)))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(counter++, notificationBuilder.build())
}

fun Context.createInboxNotification() {
    val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_feedback_24dp)
            .setVibrate(longArrayOf(1000, 1000))
            .setContentTitle(getString(R.string.new_inboxs, "5"))
            .setContentText("+5 more")
            .setAutoCancel(true)
            .setColor(getRandomColor())
            .setStyle(NotificationCompat.InboxStyle()
                    .addLine("Alberto says...")
                    .addLine("Raul says...")
                    .addLine("Carlos says...")
                    .setSummaryText("+2 more"))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(counter++, notificationBuilder.build())
}

fun Context.createMessagingNotification() {
    val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Only works on Android Nougat")
            .setContentText("Only works on Android Nougat")
            .setStyle(NotificationCompat.MessagingStyle("Me")
                    .setConversationTitle("Example Playground Chat")
                    .addMessage("Lorem", 1, null) // null means device's user
                    .addMessage("ipsum", 2, "Bot")
                    .addMessage("dolor", 3, "Bot")
                    .addMessage("sit amet", 4, null))
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(counter++, notificationBuilder.build())
}

fun Context.createActionNotification(message: String) {
    val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(getString(R.string.action_notification))
            .setContentText(message)
            .setAutoCancel(false)
            .setColor(getRandomColor())
            .setVibrate(longArrayOf(1000, 1000))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .addAction(android.R.drawable.ic_menu_info_details, "Details", null)
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(counter++, notificationBuilder.build())
}

fun Context.createProgressNotification() {
    val id = counter++
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(getString(R.string.progress_notification))
            .setContentText("Downloading...")
            .setAutoCancel(false)
            .setColor(getRandomColor())
    Thread(Runnable {
        for (i in 0..100 step 5) {
            notificationBuilder.setProgress(100, i, false)
            notificationManager.notify(id, notificationBuilder.build())
            try {
                Thread.sleep(100L)
            } catch (e: InterruptedException) {
                Log.d(TAG, "sleep failure")
            }
        }
        notificationBuilder.setContentText("Download complete")
                .setProgress(0, 0, false)
        notificationManager.notify(id, notificationBuilder.build())
    }
    ).start()
}

fun Context.createCustomBigNotification(message: String) {
    val contentView = RemoteViews(packageName, R.layout.notification_layout)
    with(contentView) {
        setImageViewResource(R.id.alertImageView, R.drawable.ic_feedback_24dp)
        setTextViewText(R.id.tvTitle, getString(R.string.last_message))
        setTextViewText(R.id.tvMessage, message)
    }
    val bigContentView = RemoteViews(packageName, R.layout.big_notification_layout)
    with(bigContentView) {
        setImageViewResource(R.id.alertImageView, R.drawable.ic_feedback_24dp)
        setTextViewText(R.id.tvTitle, getString(R.string.last_message))
        setTextViewText(R.id.tvMessage, message)
        setTextViewText(R.id.tvTime, getCurrentTime())
    }
    val mBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_feedback_24dp)
            .setContent(contentView)
            .setCustomBigContentView(bigContentView)

    val notification = mBuilder.build()
    notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL
    notification.defaults = notification.defaults or Notification.DEFAULT_SOUND
    notification.defaults = notification.defaults or Notification.DEFAULT_VIBRATE
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(counter++, notification)
}

fun Context.createGroupingNotification() {
    val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_share_24dp)
    inboxStyle.setBigContentTitle("Enter Content Text")
            .addLine("hi events " + value)
            .setSummaryText("+2 more")
    val notificationBuilder = NotificationCompat.Builder(this)
            .setContentTitle("Lanes")
            .setContentText("Notification from Lanes" + value)
            .setSmallIcon(R.drawable.ic_share_24dp)
            .setLargeIcon(bitmap)
            .setAutoCancel(true)
            .setStyle(inboxStyle)
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify("App Name", NOTIFICATION_ID, notificationBuilder.build())
    value++
}

fun parseDrawableToBitmap(res: Resources, drawable: Int): Bitmap? = BitmapFactory.decodeResource(res, drawable)

fun getCurrentTime(): CharSequence? {
    val date = Date()
    val strDateFormat = "hh:mm:ss a"
    val dateFormat = SimpleDateFormat(strDateFormat, Locale.getDefault())
    return dateFormat.format(date)
}

fun getRandomColor() = when (Random().nextInt(11 - 0) + 0) {
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


fun getRandomGravity() = when (Random().nextInt(9 - 0) + 0) {
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
