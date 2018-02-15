package com.example.pc_3.kotlinmaterialdesign

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pc_3.kotlinmaterialdesign.messages.snack
import kotlinx.android.synthetic.main.fragment_dialogs.*
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.okButton
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.support.v4.*
import java.text.DateFormat
import java.util.*


class DialogsFragment : Fragment() {
    private lateinit var v: View
    private val calendar by lazy { Calendar.getInstance() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_dialogs, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initAnkoDialogs()
        initRegularDialogs()
    }

    private fun initAnkoDialogs() {
        btnSimpleDialog.onClick {
            alert(R.string.main_dialog_simple_title) {
                okButton { toast("Yay!") }
            }.show()
        }

        btnButtonsDialog.onClick {
            alert(message = R.string.lorem, title = R.string.main_dialog_simple_title) {
                okButton {}
                cancelButton { }
                neutralPressed(getString(R.string.neutral)) {}
            }.show()
        }

        btnSimpleChoiceDialog.onClick {
            val singleChoiceItems = resources.getStringArray(R.array.dialog_choice_array)
            selector(getString(R.string.main_dialog_single_choice), singleChoiceItems.toList()) { _, i ->
                v.snack(singleChoiceItems[i] + " picked")
            }
        }

        btnProgressDialog.onClick {
            indeterminateProgressDialog(R.string.main_dialog_progress_title).show()
        }

        btnProgressBarDialog.onClick {
            progressDialog(message = R.string.main_dialog_progress_title, title = R.string.app_name).apply {
                max = 100
                setCancelable(false)
                setOnDismissListener { v.snack("Completed") }
                doAsync {
                    (0..100).forEach {
                        incrementProgressBy(1)
                        if (it == 100) dismiss()
                        try {
                            Thread.sleep(25)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }
            }.show()
        }
    }

    private fun initRegularDialogs() {
        btnDatePickerDialog.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener
            { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.time)
                btnDatePickerDialog.text = date
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        btnMultiChoiceDialog.setOnClickListener {
            val multiChoiceItems = resources.getStringArray(R.array.dialog_choice_array)
            val checkedItems = booleanArrayOf(false, false, false, false, false)
            AlertDialog.Builder(context!!)
                    .setTitle(getString(R.string.main_dialog_multi_choice))
                    .setMultiChoiceItems(multiChoiceItems, checkedItems, null)
                    .setPositiveButton(getString(R.string.ok), null)
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show()
        }

        btnTimePickerDialog.setOnClickListener {
            val timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener
            { _, i, i1 ->
                calendar.set(Calendar.HOUR_OF_DAY, i)
                calendar.set(Calendar.MINUTE, i1)
                val time = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.time)
                btnTimePickerDialog.text = time
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
            timePickerDialog.show()
        }

        btnBottomDialog.setOnClickListener {
            val mBottomSheetDialog = BottomSheetDialog(context!!)
            val dialogView = activity?.layoutInflater?.inflate(R.layout.dialog_bottom_sheet, null)
            val btnBottomDialogOk = dialogView?.findViewById<View>(R.id.btn_dialog_bottom_sheet_ok)
            val btnBottomDialogCancel = dialogView?.findViewById<View>(R.id.btn_dialog_bottom_sheet_cancel)
            mBottomSheetDialog.setContentView(dialogView)
            btnBottomDialogOk?.setOnClickListener({ mBottomSheetDialog.dismiss() })
            btnBottomDialogCancel?.setOnClickListener({ mBottomSheetDialog.dismiss() })
            mBottomSheetDialog.show()
        }

        btnFullScreenDialog.setOnClickListener {
            val fullscreenDialog = Dialog(context, R.style.DialogFullscreen)
            fullscreenDialog.setContentView(R.layout.dialog_fullscreen)
            val ivClose = fullscreenDialog.findViewById<View>(R.id.img_dialog_fullscreen_close)
            ivClose.setOnClickListener({ fullscreenDialog.dismiss() })
            fullscreenDialog.show()
        }
        /*btnSimpleDialog.setOnClickListener {
            AlertDialog.Builder(context)
                    .setMessage(getString(R.string.main_dialog_simple_title))
                    .setPositiveButton(getString(R.string.ok), null)
                    .show()
        }
        btnButtonsDialog.setOnClickListener {
            AlertDialog.Builder(context)
                    .setTitle(getString(R.string.main_dialog_simple_title))
                    .setMessage(getString(R.string.lorem))
                    .setPositiveButton(getString(R.string.ok), null)
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setNeutralButton(getString(R.string.neutral), null)
                    .show()
        }
        btnSimpleChoiceDialog.setOnClickListener {
            val singleChoiceItems = resources.getStringArray(R.array.dialog_choice_array)
            val itemSelected = 0
            AlertDialog.Builder(context)
                    .setTitle(getString(R.string.main_dialog_single_choice))
                    .setSingleChoiceItems(singleChoiceItems, itemSelected) { dialogInterface, i ->
                        dialogInterface.dismiss()
                        v.snack(singleChoiceItems[i] + " picked")
                    }
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show()
        }
        btnProgressDialog.setOnClickListener {
            val progressDialog = ProgressDialog(context)
            progressDialog.setMessage(getString(R.string.main_dialog_progress_title))
            progressDialog.show()
        }
        btnProgressBarDialog.setOnClickListener {
            val horizontalProgressDialog = ProgressDialog(context)
            horizontalProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            horizontalProgressDialog.setMessage(getString(R.string.main_dialog_progress_title))
            horizontalProgressDialog.setCancelable(false)
            horizontalProgressDialog.max = 100
            horizontalProgressDialog.show()

            Thread().run {
                (0..100).forEach {
                    horizontalProgressDialog.progress = it
                    if (it == 100) horizontalProgressDialog.dismiss()
                    try {
                        Thread.sleep(35)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            horizontalProgressDialog.setOnDismissListener { v.snack("Completed") }
        }   */
    }
}