package com.example.pc_3.kotlinmaterialdesign

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewAnimationUtils
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
        fillScreenButton.setOnClickListener {
            animateButtonWidth()
            fadeOutTextShowProgressBar()
            nextAction()
        }
    }

    private fun animateButtonWidth() {
        val anim = ValueAnimator.ofInt(fillScreenButton.measuredWidth, getFabWidth().toInt())
        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = fillScreenButton.layoutParams
            layoutParams.width = value
            fillScreenButton.requestLayout()
        }
        anim.duration = 250
        anim.start()
    }

    private fun fadeOutTextShowProgressBar() {
        buttonTitleTextView.animate().alpha(0f)
                .setDuration(250)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        showProgressDialog()
                    }
                })
                .start()
    }

    private fun showProgressDialog() {
        progressBar.alpha = 1f
        progressBar.indeterminateDrawable
                .setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN)
        progressBar.visibility = View.VISIBLE
    }

    private fun nextAction() {
        Handler().postDelayed({
            revealButton()
            fadeOutProgressDialog()
            delayedStartNextActivity()
        }, 2000)
    }

    private fun revealButton() {
        fillScreenButton.elevation = 0f
        revealView.visibility = View.VISIBLE

        val cx = revealView.width
        val cy = revealView.height
        val x = (getFabWidth() / 2 + fillScreenButton.x).toInt()
        val y = (getFabWidth() / 2 + fillScreenButton.y).toInt()
        val finalRadius = Math.max(cx, cy) * 1.2f
        val reveal = ViewAnimationUtils
                .createCircularReveal(revealView, x, y, getFabWidth(), finalRadius)
        reveal.duration = 350
        reveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                reset(animation)
            }

            private fun reset(animation: Animator) {
                super.onAnimationEnd(animation)
                revealView.visibility = View.INVISIBLE
                buttonTitleTextView.visibility = View.VISIBLE
                buttonTitleTextView.alpha = 1f
                fillScreenButton.elevation = 4f
                val layoutParams = fillScreenButton.layoutParams
                layoutParams.width = (resources.displayMetrics.density * 300).toInt()
                fillScreenButton.requestLayout()
            }
        })
        reveal.start()
    }

    private fun fadeOutProgressDialog() {
        progressBar.animate().alpha(0f).setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {}).start()
    }

    private fun delayedStartNextActivity() {
        Handler().postDelayed({
            launchActivity<BlankActivity> { }
            overridePendingTransition(0, 0)
        }, 50)
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

    private fun getFabWidth() = resources.getDimension(R.dimen.fab_size)
}