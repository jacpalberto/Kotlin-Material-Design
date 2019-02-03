package com.example.pc_3.kotlinmaterialdesign.intents_animation

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.pc_3.kotlinmaterialdesign.GlideCircleTransform
import com.example.pc_3.kotlinmaterialdesign.R
import com.example.pc_3.kotlinmaterialdesign.ScreenshotDetectionActivity
import com.example.pc_3.kotlinmaterialdesign.messages.toast
import kotlinx.android.synthetic.main.activity_blank.*


class BlankActivity : ScreenshotDetectionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blank)
        init()
    }

    private fun init() {
        loadProfileImage()
        setupToolBar()
    }

    private fun loadProfileImage() {
        Glide.with(this)
                .load(R.drawable.img_bebe_small)
                .transform(GlideCircleTransform(this))
                .into(ivProfile)
    }

    private fun setupToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.toolbar_blank_title)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onScreenCaptured(path: String?) {
        path?.let { toast("Screenshot has been taken") }
    }

    override fun onScreenCapturedWithDeniedPermission() {
        toast("Please grant read external storage permission for screenshot detection")
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