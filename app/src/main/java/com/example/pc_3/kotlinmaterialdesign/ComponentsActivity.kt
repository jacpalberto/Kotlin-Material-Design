package com.example.pc_3.kotlinmaterialdesign

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.card_progress_bar.*


/**
 * Created by PC-3 on 22/09/2017.
 */
class ComponentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_components)
        init()
    }

    private fun init() {
        setupToolBar()
        setupNavView()
    }

    private fun setupNavView() {

    }

    private fun setupToolBar() {

    }
}