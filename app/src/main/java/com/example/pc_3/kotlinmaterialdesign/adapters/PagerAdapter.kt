package com.example.pc_3.kotlinmaterialdesign.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by PC3 on 01/12/2017.
 */

class PagerAdapter(fm: FragmentManager, mFragmentList: List<Fragment>) : FragmentStatePagerAdapter(fm) {
    private var mFragmentList = mutableListOf<Fragment>()

    init {
        this.mFragmentList = mFragmentList as MutableList<Fragment>
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }
}