package com.example.pc_3.kotlinmaterialdesign

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import com.example.pc_3.kotlinmaterialdesign.adapters.PagerAdapter
import com.example.pc_3.kotlinmaterialdesign.intents_animation.AnimationActivity
import com.example.pc_3.kotlinmaterialdesign.messages.MessagesFragment
import com.example.pc_3.kotlinmaterialdesign.recycler.RecyclerActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MessagesFragment.OnNotificationClicked {
    private var notificationCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppThemeLauncher)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        setupToolbar()
        setupNavView()
        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        val mFragmentList = mutableListOf(DialogsFragment(), PaletteFragment(), MessagesFragment())
        mPager.adapter = PagerAdapter(supportFragmentManager, mFragmentList)
    }

    private fun setupNavView() {
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.isDrawerIndicatorEnabled = false
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu)
        toggle.syncState()
        toggle.setToolbarNavigationClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        navView.menu.clear()
        navView.inflateMenu(R.menu.menu_main_drawer)
        navView.itemIconTintList = null
        navView.setNavigationItemSelectedListener(this)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = ""
    }

    private fun setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.dialogs))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.palette))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.messages))
        mPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mPager.currentItem = tab.position
                val colorFrom = (toolbar.background as ColorDrawable).color
                val colorTo = getColorFromTab(tab.position)
                val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
                colorAnimation.addUpdateListener { animator ->
                    val color = animator.animatedValue as Int
                    toolbar.setBackgroundColor(color)
                    tabLayout.setBackgroundColor(color)
                    window.statusBarColor = getColorFromWindow(tab.position)
                }
                colorAnimation.duration = 250
                colorAnimation.start()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawers()
        when (item.itemId) {
            android.R.id.home -> if (drawerLayout.isDrawerOpen(Gravity.START)) {
                drawerLayout.closeDrawer(Gravity.START)
            } else drawerLayout.openDrawer(Gravity.START)
            R.id.menu_components -> {
                startActivity<ComponentsActivity>()
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }
            R.id.menu_activity_animations -> {
                startActivity<AnimationActivity>()
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }
            R.id.menu_scrolling -> {
                startActivity<ScrollingActivity>()
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }
            R.id.menu_recycler -> {
                startActivity<RecyclerActivity>()
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        val item = menu.findItem(R.id.action_notifications)
        item.icon = convertLayoutToImage(this@MainActivity,
                notificationCounter,
                R.drawable.ic_notifications_white_24dp)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_notifications) return true
        return super.onOptionsItemSelected(item)
    }

    fun getColorFromTab(position: Int) = when (position) {
        0 -> ContextCompat.getColor(this, R.color.colorPrimary)
        1 -> ContextCompat.getColor(this, R.color.purple)
        2 -> ContextCompat.getColor(this, R.color.dark_gray)
        else -> ContextCompat.getColor(this, android.R.color.black)
    }

    fun getColorFromWindow(position: Int) = when (position) {
        0 -> ContextCompat.getColor(this, R.color.dark_blue)
        1 -> ContextCompat.getColor(this, R.color.dark_purple)
        2 -> ContextCompat.getColor(this, android.R.color.black)
        else -> ContextCompat.getColor(this, android.R.color.black)
    }

    override fun updateNotificationBadge(data: Int) {
        notificationCounter = data
        invalidateOptionsMenu()
    }
}