package com.example.pc_3.kotlinmaterialdesign.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by PC3 on 01/12/2017.
 */

class PagerAdapter(fm: FragmentManager, private val mFragmentList: List<Fragment>)
    : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int) = mFragmentList[position]

    override fun getCount() = mFragmentList.size
}