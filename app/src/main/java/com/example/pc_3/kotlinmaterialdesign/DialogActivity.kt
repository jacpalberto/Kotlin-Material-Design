package com.example.pc_3.kotlinmaterialdesign

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_dialog.*
import org.jetbrains.anko.sdk15.listeners.onClick

class DialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        init()
    }

    private fun init() {
        shadowedLayout.onClick { onBackPressed() }
        closeButton.onClick { onBackPressed() }
        window.statusBarColor = ContextCompat.getColor(this, R.color.shadow)
    }

    override fun onBackPressed() {
        closeButton.visibility = View.GONE
        super.onBackPressed()
    }
}
