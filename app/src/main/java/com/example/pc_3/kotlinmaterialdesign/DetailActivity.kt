package com.example.pc_3.kotlinmaterialdesign

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Animatable
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.transition.Transition
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.toolbar_main.*
import android.graphics.drawable.AnimatedVectorDrawable



class DetailActivity : AppCompatActivity() {
    private val inputManager: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
    private var defaultColor: Int = 0
    private var isEditTextVisible: Boolean = false
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
        actionBar?.title = "Around the world"
        actionBar?.elevation = 7.0f
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupValues() {
        place = Place(intent.getStringExtra("name"), intent.getIntExtra("res", 0))
        defaultColor = ContextCompat.getColor(this, R.color.gray)
        isEditTextVisible = false
        addButton.setOnClickListener {
            if (!isEditTextVisible) {
                revealEditText(revealView)
                todoText.requestFocus()
                inputManager.showSoftInput(todoText, InputMethodManager.SHOW_IMPLICIT)
                val d = getDrawable(R.drawable.icn_morph) as AnimatedVectorDrawable // Insert your AnimatedVectorDrawable resource identifier
                addButton.setImageDrawable(d)
                d.start()
               /* addButton.setImageResource(R.drawable.icn_morph)
                val animatable = addButton.drawable as Animatable
                animatable.start()*/
            } else {
                //addToDo(todoText.text.toString())
                //toDoAdapter.notifyDataSetChanged()
                inputManager.hideSoftInputFromWindow(todoText.windowToken, 0)
                //hideEditText(revealView)
                addButton.setImageResource(R.drawable.icn_morph_reverse)
                val animatable = addButton.drawable as Animatable
                animatable.start()
            }
        }
    }

    private fun loadPlace() {
        placeTitle.text = place.name
        placeImage.setImageResource(place.resourceId)
    }

    private fun windowTransition() {
        window.enterTransition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition) {
                addButton.animate().alpha(1.0f)
                window.enterTransition.removeListener(this)
            }

            override fun onTransitionResume(transition: Transition) {}
            override fun onTransitionPause(transition: Transition) {}
            override fun onTransitionCancel(transition: Transition) {}
            override fun onTransitionStart(transition: Transition) {}
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

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }
}
