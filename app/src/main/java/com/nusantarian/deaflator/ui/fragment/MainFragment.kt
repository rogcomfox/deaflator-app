package com.nusantarian.deaflator.ui.fragment

import android.os.Bundle
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

class MainFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.icMore.setOnClickListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDropdownMenu()
    }

    private fun initDropdownMenu() {
        val items = listOf(getString(R.string.text_item_1), getString(R.string.text_item_2))
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_translation_item, items)
        (binding.tilDropdown.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (binding.tilDropdown.editText as? AutoCompleteTextView)?.setOnItemClickListener { parent, view, position, id ->

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
}