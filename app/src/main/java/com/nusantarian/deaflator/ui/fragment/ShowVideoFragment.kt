package com.nusantarian.deaflator.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.nusantarian.deaflator.R
import com.nusantarian.deaflator.databinding.FragmentShowVideoBinding

class ShowVideoFragment : Fragment() {

    private var _binding: FragmentShowVideoBinding? = null
    private val binding get() = _binding!!
    private val args: ShowVideoFragmentArgs by navArgs()
    private var videoPath = ""

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentShowVideoBinding.inflate(inflater, container, false)

        return binding.root
    }

    //Todo: This is just dummy data, need to change
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoPath = when (args.link) {
            "dinner" -> "android.resource://" + requireActivity().packageName + "/" + R.raw.dinner
            "come on" -> "android.resource://" + requireActivity().packageName + "/" + R.raw.come_on
            else -> "android.resource://" + requireActivity().packageName + "/" + R.raw.video
        }
        binding.vvSignData.setVideoURI(Uri.parse(videoPath))

        val mediaController = MediaController(activity)
        binding.vvSignData.setMediaController(mediaController)
        binding.vvSignData.start()
    }
}