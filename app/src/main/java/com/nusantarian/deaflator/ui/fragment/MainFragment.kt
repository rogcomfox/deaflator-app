package com.nusantarian.deaflator.ui.fragment

import android.media.MediaRecorder
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nusantarian.deaflator.R
import com.nusantarian.deaflator.databinding.FragmentMainBinding
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var isRecording = false
    private lateinit var recorder: MediaRecorder
    private var recordFile = ""

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.icMore.setOnClickListener(this)
        binding.tvData.setOnClickListener(this)
        binding.fabList.setOnClickListener(this)
        binding.fabStop.setOnClickListener(this)
        binding.fabRecord.setOnClickListener(this)

        //TODO: change this if data exist & backend ready
        binding.btnProcess.setOnClickListener {
            binding.tvSignOutput.visibility = View.GONE
            binding.tvData.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDropdownMenu()
        changeButtonState()
    }

    private fun changeButtonState() {
        binding.etTextInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.btnProcess.isEnabled = s.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable) {
                binding.btnProcess.isEnabled = s.isNotEmpty()
            }

        })
    }

    private fun initDropdownMenu() {
        val items = listOf(getString(R.string.text_item_1), getString(R.string.text_item_2))
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_translation_item, items)
        (binding.tilDropdown.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (binding.tilDropdown.editText as? AutoCompleteTextView)?.setOnItemClickListener { _, _, position, _ ->

            if (position == 0) {
                binding.tvChooseMode.visibility = View.GONE
                binding.cardTextSign.visibility = View.VISIBLE
                binding.cardSpeechSign.visibility = View.GONE
            } else {
                binding.tvChooseMode.visibility = View.GONE
                binding.cardTextSign.visibility = View.GONE
                binding.cardSpeechSign.visibility = View.VISIBLE
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ic_more ->
                raisePopup()
            R.id.tv_data ->
                findNavController()
                        .navigate(MainFragmentDirections.actionMainFragmentToShowVideoFragment())
            R.id.fab_list ->
                findNavController()
                        .navigate(MainFragmentDirections.actionMainFragmentToListRecordingsFragment())
            R.id.fab_record -> {
                if (isRecording) {
                    stopRecording()
                    binding.fabRecord.isEnabled = true
                    binding.fabStop.isEnabled = false
                    isRecording = false
                } else {
                    startRecording()
                    binding.fabRecord.isEnabled = false
                    binding.fabStop.isEnabled = true
                    isRecording = true
                }
            }
            R.id.fab_stop -> {
                if (isRecording) {
                    stopRecording()
                    binding.fabRecord.isEnabled = true
                    binding.fabStop.isEnabled = false
                    isRecording = false
                } else {
                    startRecording()
                    binding.fabRecord.isEnabled = false
                    binding.fabStop.isEnabled = true
                    isRecording = true
                }
            }
        }
    }

    private fun stopRecording() {
        binding.recordTimer.stop()
        binding.tvLabel.text = requireActivity().resources.getString(R.string.text_stop_record, recordFile)
        recorder.stop()
        recorder.release()
    }

    private fun startRecording() {
        binding.recordTimer.base = SystemClock.elapsedRealtime()
        binding.recordTimer.start()

        val recordPath = requireActivity().getExternalFilesDir("/")!!.absolutePath
        val sdf = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.ROOT)
        val date = Date()
        recordFile = "${sdf.format(date)}.mp3"

        binding.tvLabel.text = requireActivity().resources.getString(R.string.text_start_record, recordFile)

        recorder = MediaRecorder()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder.setOutputFile("$recordPath/$recordFile")
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        try {
            recorder.prepare()
        } catch (e: IOException) {
            Toast.makeText(context, e.printStackTrace().toString(), Toast.LENGTH_SHORT).show()
        }

        recorder.start()
    }

    private fun raisePopup() {
        val popup = PopupMenu(requireContext(), binding.icMore)
        popup.menuInflater.inflate(R.menu.popup_menu_main, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nav_settings -> {
                    findNavController()
                            .navigate(MainFragmentDirections.actionMainFragmentToSettingsFragment())
                    return@setOnMenuItemClickListener true
                }
                R.id.nav_features -> {
                    findNavController()
                            .navigate(MainFragmentDirections.actionMainFragmentToFeaturesFragment())
                    return@setOnMenuItemClickListener true
                }
                R.id.nav_about -> {
                    Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener false
                }
            }
        }
        popup.show()
    }
}