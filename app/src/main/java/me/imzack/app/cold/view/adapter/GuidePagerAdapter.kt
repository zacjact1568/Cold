package me.imzack.app.cold.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class GuidePagerAdapter(fm: FragmentManager, private val fragmentList: List<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = fragmentList[position]

    override fun getCount() = fragmentList.size
}