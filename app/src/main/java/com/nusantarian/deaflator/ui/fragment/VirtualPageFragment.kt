package com.nusantarian.deaflator.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nusantarian.deaflator.databinding.FragmentVirtualPageBinding


class VirtualPageFragment : Fragment() {

    private var _binding: FragmentVirtualPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVirtualPageBinding.inflate(inflater, container, false)
        return binding.root
    }
}