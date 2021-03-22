package com.nusantarian.deaflator.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.nusantarian.deaflator.R
import com.nusantarian.deaflator.databinding.FragmentFeaturesContentBinding

class FeaturesContentFragment : Fragment() {

    private var _binding: FragmentFeaturesContentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFeaturesContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey("PASS_DATA") }?.apply {
            getImage(getStringArray("PASS_DATA")!![0])
            binding.tvTitle.text = getStringArray("PASS_DATA")!![1]
            binding.tvInfo.text = getStringArray("PASS_DATA")!![2]
        }
    }

    private fun getImage(page: String) {
        when (page) {
            "Page 1" ->
                binding.imgFeature.load(R.drawable.ic_translate_illustration)
            "Page 2" ->
                binding.imgFeature.load(R.drawable.ic_speech_illustration)
            "Page 3" ->
                binding.imgFeature.load(R.drawable.ic_avatar_illustration)
        }
    }
}