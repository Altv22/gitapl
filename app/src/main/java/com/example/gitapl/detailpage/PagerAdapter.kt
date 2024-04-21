package com.example.gitapl.detailpage

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.gitapl.R

class PagerAdapter(private val mCTx: Context, fm: FragmentManager, data: Bundle) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle: Bundle
    init {
            fragmentBundle = data
    }
    @StringRes
    private val TAB_TITLES = arrayOf(R.string.tab1, R.string.tab2)

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        val fragment = when (position) {
            0 -> follower_frag()
            1 -> following_frag()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
        fragment.arguments = this.fragmentBundle
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mCTx.resources.getString(TAB_TITLES[position])
    }
}
