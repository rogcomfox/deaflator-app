package com.nusantarian.deaflator.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
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
import java.util.*


class MainFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var speech: SpeechRecognizer

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        speechToText()

        binding.icMore.setOnClickListener(this)
        binding.tvData.setOnClickListener(this)
        binding.tvData1.setOnClickListener(this)
        binding.btnProcess.setOnClickListener {
            binding.tvSignOutput.visibility = View.GONE
            binding.tvData.visibility = View.VISIBLE
        }

        return binding.root
    }

    //convert speech to text
    private fun speechToText() {
        speech = SpeechRecognizer.createSpeechRecognizer(activity)
        val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        speech.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
            }

            override fun onBeginningOfSpeech() {
                binding.tvResultSpeech.text = ""
                binding.tvResultSpeech.hint = "Listening....."
            }

            override fun onRmsChanged(rmsdB: Float) {
            }

            override fun onBufferReceived(buffer: ByteArray?) {
            }

            override fun onEndOfSpeech() {
            }

            override fun onError(error: Int) {
            }

            override fun onResults(results: Bundle) {
                binding.imgRecord.setImageResource(R.drawable.ic_mic_black)
                val data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                binding.tvResultSpeech.text = data?.get(0)
                binding.tvData1.visibility = View.VISIBLE
                binding.tvSignOutput1.visibility = View.GONE
            }

            override fun onPartialResults(partialResults: Bundle?) {
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
            }
        })

        binding.imgRecord.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                speech.stopListening()
            }
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                binding.imgRecord.setImageResource(R.drawable.ic_mic_red)
                speech.startListening(speechIntent)
            }
            false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDropdownMenu()
        changeButtonState()
    }

    private fun changeButtonState() {
        binding.etTextInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

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
            R.id.ic_more -> raisePopup()
            R.id.tv_data ->
                findNavController()
                        .navigate(MainFragmentDirections.actionMainFragmentToShowVideoFragment())
            R.id.tv_data_1 ->
                findNavController()
                        .navigate(MainFragmentDirections.actionMainFragmentToShowVideoFragment())
        }
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

    override fun onDestroy() {
        super.onDestroy()
        speech.destroy()
    }
}