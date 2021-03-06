package com.example.pc_3.kotlinmaterialdesign.recycler

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.pc_3.kotlinmaterialdesign.R
import com.example.pc_3.kotlinmaterialdesign.recycler.models.Place
import kotlinx.android.synthetic.main.activity_recycler.*

class RecyclerActivity : AppCompatActivity() {
    private lateinit var menu: Menu
    private var isListView: Boolean = true
    private lateinit var staggeredLayoutManager: StaggeredGridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        init()
    }

    private fun init() {
        setupToolbar()
        initRvList()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.toolbar_recycler_title)
            elevation = 7.0f
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun initRvList() {
        list.adapter = TravelListAdapter(getTravelList()) { place, view ->
            val transitionIntent = Intent(this, DetailActivity::class.java).apply {
                putExtra("name", place.name)
                putExtra("res", place.resourceId)
            }
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *getPairList(view))
            ActivityCompat.startActivity(this, transitionIntent, options.toBundle())
        }
        staggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        list.layoutManager = staggeredLayoutManager
    }

    private fun getPairList(view: View): Array<out Pair<View, String>> {
        val navigationBar = findViewById<View>(android.R.id.navigationBarBackground)
        val statusBar = findViewById<View>(android.R.id.statusBarBackground)
        val placeImage = view.findViewById<ImageView>(R.id.placeImage)
        val placeNameHolder = view.findViewById<LinearLayout>(R.id.placeNameHolder)

        val pairList = mutableListOf(
                Pair(placeImage as View, "tImage"),
                Pair(placeNameHolder as View, "tNameHolder"),
                Pair(toolbar as View, "tActionBar"),
                Pair(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)).apply {
            if (navigationBar != null && statusBar != null)
                add(Pair(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME))
        }
        return pairList.toTypedArray()
    }

    private fun getTravelList() = mutableListOf(
            Place("Belgium", R.drawable.item_belgium),
            Place("Budapest", R.drawable.item_budapest),
            Place("Croatia", R.drawable.item_croatia),
            Place("Frankfurt", R.drawable.item_frankfurt),
            Place("Honk Kong", R.drawable.item_honk_kong),
            Place("London", R.drawable.item_london),
            Place("New York", R.drawable.item_new_york),
            Place("Paris", R.drawable.item_paris),
            Place("Rio de Janeiro", R.drawable.item_rio_de_janeiro),
            Place("Rome", R.drawable.item_rome),
            Place("Santorini", R.drawable.item_santorini),
            Place("Sidney", R.drawable.item_sydney),
            Place("Tokyo", R.drawable.item_tokyo),
            Place("Toronto", R.drawable.item_toronto),
            Place("Turkey", R.drawable.item_turkey),
            Place("Venice", R.drawable.item_venice))

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.recycler_menu, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_toggle) {
            toggle()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggle() {
        if (isListView) showGridView()
        else showListView()
    }

    private fun showListView() {
        staggeredLayoutManager.spanCount = 1
        val item = menu.findItem(R.id.action_toggle)
        item.setIcon(R.drawable.ic_action_grid)
        item.title = getString(R.string.show_as_grid)
        isListView = true
    }

    private fun showGridView() {
        staggeredLayoutManager.spanCount = 2
        val item = menu.findItem(R.id.action_toggle)
        item.setIcon(R.drawable.ic_action_list)
        item.title = getString(R.string.show_as_list)
        isListView = false
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
