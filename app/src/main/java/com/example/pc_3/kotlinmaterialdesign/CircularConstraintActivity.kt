package com.example.pc_3.kotlinmaterialdesign

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_circular_constraint.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.sdk15.listeners.onSeekBarChangeListener

/**
 * Created by Alberto Carrillo on 4/16/18.
 */

class CircularConstraintActivity : AppCompatActivity() {
    private var textViewCreatedCounter = 0
    private var circularRadius = 80
    private var textViewList = mutableListOf<TextView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circular_constraint)
        init()
    }

    private fun init() {
        setupToolBar()
        createTextViewFab.onClick { createTextView() }
        radioSeekBar.onSeekBarChangeListener {
            onProgressChanged { _, i, _ ->
                circularRadius = 80 + i
                if (textViewList.isNotEmpty()) updateTextViewRadius()
            }
        }
    }

    private fun updateTextViewRadius() {
        textViewList.forEach {
            val l = it.layoutParams as ConstraintLayout.LayoutParams
            l.circleRadius = dip(circularRadius)
            it.layoutParams = l
        }
    }

    private fun createTextView() {
        if (textViewCreatedCounter <= 9) {
            val tv = TextView(this).apply {
                val lp = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT)
                        .apply {
                            circleRadius = dip(circularRadius)
                            circleConstraint = R.id.icon_badge
                        }
                layoutParams = lp
                text = "  ${textViewCreatedCounter++}  "
                gravity = Gravity.CENTER
                textSize = 25.toFloat()
                setBackgroundResource(R.drawable.custom_badge)
                setTextColor(ContextCompat.getColor(this@CircularConstraintActivity, android.R.color.white))
            }
            textViewList.add(tv)
            circularLayout.addView(tv)
        }
        calculateAngles()
    }

    private fun calculateAngles() {
        val angle = if (textViewCreatedCounter - 1 > 0) 360 / (textViewCreatedCounter) else 0
        textViewList.forEachIndexed { i, tv ->
            val l = tv.layoutParams as ConstraintLayout.LayoutParams
            l.circleAngle = angle.toFloat() * i
            tv.layoutParams = l
        }
    }

    private fun setupToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.circular_constraint)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
