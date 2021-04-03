package com.nusantarian.deaflator.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nusantarian.deaflator.ui.fragment.FeaturesContentFragment

class ScreenSlidePagerAdapter(
        fragment: Fragment,
        private val list: List<Array<String>>,
        private val page: Int
) :
    FragmentStateAdapter(fragment) {

    companion object {
        const val PASS_DATA = "PASS_DATA"
    }

    override fun getItemCount(): Int = page

    override fun createFragment(position: Int): Fragment {
        val fragment = FeaturesContentFragment()

        when (position) {
            0 ->
                fragment.arguments = Bundle().apply {
                    putStringArray(PASS_DATA, list[0])
                }
            1 ->
                fragment.arguments = Bundle().apply {
                    putStringArray(PASS_DATA, list[1])
                }
            2 ->
                fragment.arguments = Bundle().apply {
                    putStringArray(PASS_DATA, list[2])
                }
        }
        return fragment
    }
}