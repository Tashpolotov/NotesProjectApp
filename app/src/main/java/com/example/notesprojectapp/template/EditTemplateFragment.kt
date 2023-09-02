package com.example.notesprojectapp.template

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.domain.model.HomeNotesModel
import com.example.domain.model.model
import com.example.notesprojectapp.R
import com.example.notesprojectapp.databinding.FragmentEditNoteHomeBinding
import com.example.notesprojectapp.databinding.FragmentEditTemplateBinding
import com.example.notesprojectapp.viewmodel.NotesViewModel
import com.example.notesprojectapp.viewmodel.TempViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditTemplateFragment : Fragment() {

    private lateinit var binding: FragmentEditTemplateBinding
    private val viewModel by viewModels<TempViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditTemplateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtn()
        initDelete()

        val title = arguments?.getString("title")
        val content = arguments?.getString("content")
        binding.etWork.setText(title)
        binding.etDesc.setText(content)

        binding.imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }


    }

    private fun initDelete() {
        binding.imgDelete.setOnClickListener {
            val id = arguments?.getString("noteId")
            if (id != null) {
                viewModel.delete(id)
            }
            parentFragmentManager.popBackStack()
        }
    }

    private fun initBtn() {

        binding.imgAdd.setOnClickListener {
            val editedTitle = binding.etWork.text.toString()
            val editedContent = binding.etDesc.text.toString()
            Log.d("alala", "Edited Title: $editedTitle, Edited Content: $editedContent")

            val id = arguments?.getString("noteId")

            if (id != null) {
                val editedNote = model(id, "", "", "", "", editedTitle, editedContent, false, "" /* другие поля, если есть */)

                viewModel.update(id, editedNote) // Используйте новый метод для обновления заметки
            }

            parentFragmentManager.popBackStack()
        }
    }
}