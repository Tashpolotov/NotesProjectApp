package com.example.notesprojectapp.fragments.creatnewframent

import android.graphics.Rect
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.model.TemplateNotesModel
import com.example.notesprojectapp.R
import com.example.notesprojectapp.databinding.FragmentCreateNewBinding
import com.example.notesprojectapp.fragments.settingfragment.SettingFragment
import com.example.notesprojectapp.template.TemplateFragment
import com.example.notesprojectapp.viewmodel.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@Suppress("UNREACHABLE_CODE")
@AndroidEntryPoint
class CreateNewFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewBinding
    private val viewModel by viewModels<NotesViewModel>()
    private var isKeyboardOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupKeyboardListener()
        setupClickListeners()
        binding.imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }


        }

    private fun setupKeyboardListener() {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val rect = android.graphics.Rect()
                    binding.root.getWindowVisibleDisplayFrame(rect)
                    val screenHeight = binding.root.height
                    val keypadHeight = screenHeight - rect.bottom
                    val isKeyboardCurrentlyOpen = keypadHeight > screenHeight * 0.15

                    if (isKeyboardCurrentlyOpen && !isKeyboardOpen) {
                        binding.imgTemplate.visibility = View.GONE
                    } else if (!isKeyboardCurrentlyOpen && isKeyboardOpen) {
                        binding.imgTemplate.visibility = View.VISIBLE
                    }

                    isKeyboardOpen = isKeyboardCurrentlyOpen
                }
            }
        )
    }

    private fun setupClickListeners() {
        binding.imgTemplate.setOnClickListener {
            val id = System.currentTimeMillis().toString()
            val textWorkList = binding.etPlaner.text.toString()
            val textNotes = binding.etText.text.toString()

            if (textWorkList.isEmpty() || textNotes.isEmpty()) {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                val currentDateTime = Calendar.getInstance().time
                val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val day = dateFormatter.format(currentDateTime)

                val newTemplateNotes = TemplateNotesModel(id, 0, "", textWorkList)
                viewModel.addTemplateNotes(id, newTemplateNotes)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fr_container, TemplateFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}