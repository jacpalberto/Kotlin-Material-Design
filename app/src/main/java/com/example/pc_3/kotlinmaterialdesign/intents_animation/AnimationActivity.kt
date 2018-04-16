package com.example.pc_3.kotlinmaterialdesign.intents_animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.transition.Explode
import android.view.View
import android.view.ViewAnimationUtils
import android.view.Window
import com.bumptech.glide.Glide
import com.example.pc_3.kotlinmaterialdesign.GlideCircleTransform
import com.example.pc_3.kotlinmaterialdesign.R
import kotlinx.android.synthetic.main.activity_animation.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.noAnimation
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.startActivity
import android.opengl.ETC1.getWidth
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.graphics.BitmapFactory
import android.graphics.Bitmap


class AnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS) //required for transitions
        setContentView(R.layout.activity_animation)
        init()
    }

    private fun init() {
        loadProfileImage()
        setupToolBar()
        btnToLeft.onClick { startTransitionBlankActivity(R.anim.slide_from_right, R.anim.slide_to_left) }
        btnToRight.onClick { startTransitionBlankActivity(R.anim.slide_from_left, R.anim.slide_to_right) }
        btnFade.onClick { startTransitionBlankActivity(R.anim.fade_in, R.anim.fade_out) }
        btnInstant.onClick { startActivity(intentFor<BlankActivity>().noAnimation()) }
        btnRegular.onClick { startActivity<BlankActivity>() }
        btnToDown.onClick { startTransitionBlankActivity(R.anim.slide_from_top, R.anim.slide_to_down) }
        btnExplode.onClick {
            window.exitTransition = Explode()
            val options = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            startActivity(Intent(this, BlankActivity::class.java), options)
        }
        cardProfile.onClick {
            val transitionIntent = Intent(this, BlankActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *getTransitionPairList())
            ActivityCompat.startActivity(this, transitionIntent, options.toBundle())
        }
        fillScreenButton.onClick {
            animateButtonWidth()
            fadeOutTextShowProgressBar()
            nextAction()
        }
    }

    private fun getTransitionPairList(): Array<out Pair<View, String>> {
        val navigationBar = findViewById<View>(android.R.id.navigationBarBackground)
        val statusBar = findViewById<View>(android.R.id.statusBarBackground)
        val pairList = mutableListOf(
                Pair(ivProfile as View, "tImage"),
                Pair(tvProfileName as View, "tProfileName"),
                Pair(tvProfileDescription as View, "tProfileDescription"),
                Pair(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)).apply {
            if (navigationBar != null && statusBar != null)
                add(Pair(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME))
        }
        return pairList.toTypedArray()
    }

    private fun loadProfileImage() {
        Glide.with(this)
                .load(R.drawable.img_bebe_small)
                .transform(GlideCircleTransform(this))
                .into(ivProfile)
    }

    private fun loadProfileImageWithCircularDrawable() {
        val bitmapBitmap = BitmapFactory.decodeResource(resources, R.drawable.img_bebe_small)
        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmapBitmap).apply {
            cornerRadius = bitmapBitmap.width.toFloat()
            isCircular = true
        }
        ivProfile.setImageDrawable(circularBitmapDrawable)
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
        }, 1300)
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
        }, 700)
    }

    private fun setupToolBar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.toolbar_animation_title)
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

    private fun startTransitionBlankActivity(animStart: Int, animEnd: Int) {
        startActivity<BlankActivity>()
        overridePendingTransition(animStart, animEnd)
    }

    private fun getFabWidth() = resources.getDimension(R.dimen.fab_size)
}