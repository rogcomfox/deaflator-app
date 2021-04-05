package com.nusantarian.deaflator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nusantarian.deaflator.databinding.LayoutListRecordingsBinding
import com.nusantarian.deaflator.ui.fragment.ListRecordingsFragmentDirections
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ListRecordingsAdapter(private val allFiles: Array<File>) :
        RecyclerView.Adapter<ListRecordingsAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutListRecordingsBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
                LayoutListRecordingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(allFiles[position]) {
                val sdf = SimpleDateFormat("dd-MM-yyyy HH-mm-ss", Locale.ROOT)
                        .format(lastModified())
                binding.tvTitleRecording.text = name
                binding.tvTime.text = sdf

                holder.itemView.setOnClickListener { view ->
                    view.findNavController()
                            .navigate(ListRecordingsFragmentDirections.actionListRecordingsFragmentToShowVideoFragment("dummy link"))
                }
            }
        }
    }

    override fun getItemCount(): Int = allFiles.size
}