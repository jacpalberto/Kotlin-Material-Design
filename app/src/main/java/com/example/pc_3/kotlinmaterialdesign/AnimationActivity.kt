package com.example.pc_3.kotlinmaterialdesign

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_animation.*
import kotlinx.android.synthetic.main.toolbar.*

class AnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        init()
    }

    private fun init() {
        setupToolBar()
        btnToLeft.setOnClickListener {
            launchActivity<BlankActivity> { }
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }
        btnToRight.setOnClickListener {
            launchActivity<BlankActivity> { }
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }
        btnFade.setOnClickListener {
            launchActivity<BlankActivity> { }
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        btnInstant.setOnClickListener {
            launchActivity<BlankActivity> { }
            overridePendingTransition(0, 0)
        }
        btnRegular.setOnClickListener {
            launchActivity<BlankActivity> { }
        }
        btnToDown.setOnClickListener {
            launchActivity<BlankActivity> { }
            overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_down)
        }
    }

    private fun setupToolBar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = "Animation Activity"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
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
