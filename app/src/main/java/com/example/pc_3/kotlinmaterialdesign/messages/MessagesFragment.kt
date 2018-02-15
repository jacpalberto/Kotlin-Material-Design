package com.example.pc_3.kotlinmaterialdesign.messages

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_messages.*
import com.example.pc_3.kotlinmaterialdesign.*
import org.jetbrains.anko.sdk15.listeners.textChangedListener


class MessagesFragment : Fragment() {
    private lateinit var v: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        btnCustomToast.setOnClickListener { activity?.customLayoutToast(getMessageText()) }
        btnNotification.setOnClickListener { activity?.createNotification(getMessageText()) }
        btnCustomNotification.setOnClickListener { activity?.createCustomNotification(getMessageText()) }
        btnRichNotification.setOnClickListener { activity?.createRichNotification(getMessageText()) }
        btnPictureNotification.setOnClickListener { activity?.createBigPictureNotification(getMessageText()) }
        btnInboxNotification.setOnClickListener { activity?.createInboxNotification() }
        btnMessagingNotification.setOnClickListener { activity?.createMessagingNotification() }
        btnActionNotification.setOnClickListener { activity?.createActionNotification(getMessageText()) }
        btnProgressNotification.setOnClickListener { activity?.createProgressNotification() }
        btnCustomBigNotification.setOnClickListener { activity?.createCustomBigNotification(getMessageText()) }
        btnGroupNotification.setOnClickListener { activity?.createGroupingNotification() }
    }

    private fun getMessageText() = if (etMessage.text.toString().isNotEmpty())
        etMessage.text.toString()
    else getString(R.string.text_not_null)
}