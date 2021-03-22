package com.nusantarian.deaflator.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.nusantarian.deaflator.R
import com.nusantarian.deaflator.adapter.ScreenSlidePagerAdapter
import com.nusantarian.deaflator.databinding.FragmentFeaturesBinding

class FeaturesFragment : Fragment() {

    private var _binding: FragmentFeaturesBinding? = null
    private val binding get() = _binding!!
    private val pageNumbers = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFeaturesBinding.inflate(inflater, container, false)

        binding.btnSkip.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ScreenSlidePagerAdapter(this, getContents(), pageNumbers)
        binding.tutorialPage.adapter = adapter

        TabLayoutMediator(binding.intoTabLayout, binding.tutorialPage) { _, _ -> }.attach()
    }

    private fun getContents(): List<Array<String>> {
        val item1 = arrayOf("Page 1", getString(R.string.tv_title_1), getString(R.string.tv_info_1))
        val item2 = arrayOf("Page 2", getString(R.string.tv_title_2), getString(R.string.tv_info_2))
        val item3 = arrayOf("Page 3", getString(R.string.tv_title_3), getString(R.string.tv_info_3))

        return arrayListOf(item1, item2, item3)
    }
}