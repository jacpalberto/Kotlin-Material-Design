package com.example.pc_3.kotlinmaterialdesign.messages

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_messages.*
import com.example.pc_3.kotlinmaterialdesign.*
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.sdk15.listeners.textChangedListener


class MessagesFragment : Fragment() {
    private val notificationList = mutableListOf<String>()
    private lateinit var v: View
    private var listener: OnNotificationClicked? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        listener = activity as OnNotificationClicked //Reference to Main Activity
        v = inflater.inflate(R.layout.fragment_messages, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        etMessage.textChangedListener {
            onTextChanged { text, start, before, count ->
                text?.length?.let { if (it > 12) etMessage.error = ("$text must be lower than 12 characters") }
            }
        }
    }

    private fun init() {
        btnToast.setOnClickListener {
            activity?.customToast(getMessageText(),
                    checkRandomTextColor.isChecked,
                    checkRandomBackground.isChecked,
                    checkRandomPosition.isChecked)
        }
        btnSnackbar.setOnClickListener {
            v.customSnack(getMessageText(),
                    checkRandomTextColor.isChecked,
                    checkRandomBackground.isChecked,
                    checkRandomPosition.isChecked)
        }
        btnCustomToast.onClick { activity?.customLayoutToast(getMessageText()) }
        btnNotification.onClick { notify { activity?.createNotification(getMessageText()) } }
        btnCustomNotification.onClick { notify { activity?.createCustomNotification(getMessageText()) } }
        btnRichNotification.onClick { notify { activity?.createRichNotification(getMessageText()) } }
        btnPictureNotification.onClick { notify { activity?.createBigPictureNotification(getMessageText()) } }
        btnInboxNotification.onClick { notify { activity?.createInboxNotification() } }
        btnMessagingNotification.onClick { notify { activity?.createMessagingNotification() } }
        btnActionNotification.onClick { notify { activity?.createActionNotification(getMessageText()) } }
        btnProgressNotification.onClick { notify { activity?.createProgressNotification() } }
        btnCustomBigNotification.onClick { notify { activity?.createCustomBigNotification(getMessageText()) } }
        btnGroupNotification.onClick { notify { activity?.createGroupingNotification() } }
    }

    private fun notify(function: () -> Unit?) {
        notificationList.add(getMessageText())
        listener?.updateNotificationBadge(notificationList.size)
        function()
    }

    private fun getMessageText() = if (etMessage.text.toString().isNotEmpty())
        etMessage.text.toString()
    else getString(R.string.text_not_null)

    interface OnNotificationClicked {
        fun updateNotificationBadge(data: Int)
    }
}