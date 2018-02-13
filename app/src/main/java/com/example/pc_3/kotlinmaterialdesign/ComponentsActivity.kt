package com.example.pc_3.kotlinmaterialdesign

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager


/**
 * Created by Alberto Carrillo on 22/09/2017.
 */
class ComponentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        setContentView(R.layout.activity_components)
        init()
    }

    private fun init() {
    }
}