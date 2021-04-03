package com.nusantarian.deaflator.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nusantarian.deaflator.adapter.ListRecordingsAdapter
import com.nusantarian.deaflator.databinding.FragmentListRecordingsBinding
import java.io.File

class ListRecordingsFragment : Fragment() {

    private var _binding: FragmentListRecordingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentListRecordingsBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val path = requireActivity().getExternalFilesDir("/")!!.absolutePath
        val directory = File(path)
        val files = directory.listFiles()
        val adapter = ListRecordingsAdapter(files)

        binding.rvRecordings.adapter = adapter

        if (adapter.itemCount == 0) {
            binding.tvNoRecordings.visibility = View.VISIBLE
            binding.rvRecordings.visibility = View.GONE
        } else {
            binding.tvNoRecordings.visibility = View.GONE
            binding.rvRecordings.visibility = View.VISIBLE
        }
    }
}