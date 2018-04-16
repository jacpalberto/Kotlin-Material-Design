package com.example.pc_3.kotlinmaterialdesign.recycler

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Animatable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.transition.Transition
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.LinearLayout
import com.example.pc_3.kotlinmaterialdesign.R
import com.example.pc_3.kotlinmaterialdesign.recycler.models.Place
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {
    private var defaultColor: Int = 0
    private var isEditTextVisible = false
    private lateinit var place: Place

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
    }

    private fun init() {
        setupToolbar()
        setupValues()
        loadPlace()
        windowTransition()
        getPhoto()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.toolbar_recycler_title)
        actionBar?.elevation = 7.0f
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupValues() {
        place = Place(intent.getStringExtra("name"), intent.getIntExtra("res", 0))
        defaultColor = ContextCompat.getColor(this, R.color.gray)
        addButton.setOnClickListener {
            if (!isEditTextVisible) {
                revealEditText(revealView)
                morphDrawable(R.drawable.ic_morph)

            } else {
                //addToDo(todoText.text.toString())
                //toDoAdapter.notifyDataSetChanged()
                hideEditText(revealView)
                morphDrawable(R.drawable.ic_morph_reverse)
            }
        }
    }

    private fun morphDrawable(drawable: Int) {
        addButton.setImageResource(drawable)
        val anim = addButton.drawable as Animatable
        anim.start()
    }

    private fun loadPlace() {
        placeTitle.text = place.name
        placeImage.setImageResource(place.resourceId)
    }

    private fun windowTransition() {
        window.enterTransition.addListener(object : Transition.TransitionListener {
            override fun onTransitionResume(transition: Transition) {}
            override fun onTransitionPause(transition: Transition) {}
            override fun onTransitionCancel(transition: Transition) {}
            override fun onTransitionStart(transition: Transition) {}
            override fun onTransitionEnd(transition: Transition) {
                addButton.animate().alpha(1.0f)
                window.enterTransition.removeListener(this)
            }
        })
    }

    private fun getPhoto() {
        val photo = BitmapFactory.decodeResource(resources, place.resourceId)
        colorize(photo)
    }

    private fun colorize(photo: Bitmap) {
        val palette = Palette.from(photo).generate()
        applyPalette(palette)
    }

    private fun applyPalette(palette: Palette) {
        window.setBackgroundDrawable(ColorDrawable(palette.getDarkMutedColor(defaultColor)))
        placeNameHolder.setBackgroundColor(palette.getMutedColor(defaultColor))
        revealView.setBackgroundColor(palette.getLightVibrantColor(defaultColor))
    }

    private fun revealEditText(view: LinearLayout) {
        val cx = view.right - 30
        val cy = view.bottom - 60
        val finalRadius = Math.max(view.width, view.height)
        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius.toFloat())
        view.visibility = View.VISIBLE
        isEditTextVisible = true
        anim.start()
    }

    private fun hideEditText(view: LinearLayout) {
        val cx = view.right - 30
        val cy = view.bottom - 60
        val initialRadius = view.width
        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius.toFloat(), 0f)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view.visibility = View.INVISIBLE
            }
        })
        isEditTextVisible = false
        anim.start()
    }

    override fun onBackPressed() {
        val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
        alphaAnimation.duration = 100
        addButton.startAnimation(alphaAnimation)
        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                addButton.visibility = View.GONE
                finishAfterTransition()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
