package com.example.notesprojectapp.fragments.settingfragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.notesprojectapp.R
import com.example.notesprojectapp.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private var themeChangeListener: ThemeChangeListener? = null

    interface ThemeChangeListener {
        fun onThemeChanged(selectedTheme: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.tvTheme.setOnClickListener {
            showThemeSelectionDialog()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ThemeChangeListener) {
            themeChangeListener = context
        }
    }

    private fun showThemeSelectionDialog() {
        val themes = arrayOf("Light", "Dark")

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Theme")
            .setSingleChoiceItems(themes, -1) { dialog, which ->
                val selectedTheme = themes[which]
                themeChangeListener?.onThemeChanged(selectedTheme)
                dialog.dismiss()
            }

        builder.create().show()
    }
}