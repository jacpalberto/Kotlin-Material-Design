package com.example.pc_3.kotlinmaterialdesign

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.Window
import kotlinx.android.synthetic.main.activity_animation.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk15.listeners.onClick
import android.app.ActivityOptions
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.transition.Explode

class AnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS) //required for transitions
        setContentView(R.layout.activity_animation)
        init()
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun init() {
        setupToolBar()
        btnToLeft.onClick {
            startActivity<BlankActivity>()
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }
        btnToRight.onClick {
            startActivity<BlankActivity>()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }
        btnFade.onClick {
            startActivity<BlankActivity>()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        btnInstant.onClick {
            startActivity(intentFor<BlankActivity>().noAnimation())
        }
        btnRegular.onClick {
            startActivity<BlankActivity>()
        }
        btnToDown.onClick {
            startActivity<BlankActivity>()
            overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_down)
        }
        btnExplode.onClick {
            window.exitTransition = Explode()
            startActivity(Intent(this, BlankActivity::class.java),
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
        fillScreenButton.onClick {
            animateButtonWidth()
            fadeOutTextShowProgressBar()
            nextAction()
        }
    }

    private fun animateButtonWidth() {
        val anim = ValueAnimator.ofInt(fillScreenButton.measuredWidth, getFabWidth().toInt())
        anim.addUpdateListener { valueAnimator ->
            val layoutParams = fillScreenButton.layoutParams
            layoutParams.width = valueAnimator.animatedValue as Int
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
                }).start()
    }

    private fun showProgressDialog() {
        progressBar.alpha = 1f
        progressBar.indeterminateDrawable
                .setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_IN)
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
        reveal.duration = 1000
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
            startActivity<BlankActivity>()
            overridePendingTransition(0, 0)
        }, 500)
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