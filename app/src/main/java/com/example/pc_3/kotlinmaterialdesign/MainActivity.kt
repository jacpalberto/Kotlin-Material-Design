package com.example.pc_3.kotlinmaterialdesign

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_and_toolbar.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        setupToolBar()
        setupNavView()
        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        val mFragmentList = mutableListOf(DialogsFragment(), WidgetFragment())
        mPager.adapter = PagerAdapter(supportFragmentManager, mFragmentList)
    }

    private fun setupNavView() {
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.isDrawerIndicatorEnabled = false
        toggle.setHomeAsUpIndicator(android.R.drawable.ic_menu_help)
        toggle.syncState()
        toggle.setToolbarNavigationClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        navView.menu.clear()
        navView.inflateMenu(R.menu.menu_main_drawer)
        navView.itemIconTintList = null
        navView.setNavigationItemSelectedListener(this)
    }

    private fun setupToolBar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = "Kotlin Material Design"
    }

    private fun setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.dialogs))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.widgets))
        tabLayout.tabGravity = TabLayout.GRAVITY_CENTER
        mPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> if (drawerLayout.isDrawerOpen(Gravity.START)) {
                drawerLayout.closeDrawer(Gravity.START)
            } else drawerLayout.openDrawer(Gravity.START)
            R.id.menu_components -> launchActivity<ComponentsActivity> { }
        }
        drawerLayout.closeDrawers()
        return true
    }
}

