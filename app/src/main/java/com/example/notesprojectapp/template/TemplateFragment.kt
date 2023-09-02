package com.example.notesprojectapp.template

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.domain.model.TemplateNotesModel
import com.example.notesprojectapp.R
import com.example.notesprojectapp.databinding.FragmentCreateNewBinding
import com.example.notesprojectapp.databinding.FragmentTemplateBinding
import com.example.notesprojectapp.fragments.creatnewframent.CreateNewFragment
import com.example.notesprojectapp.fragments.homefragment.HomeFragment
import com.example.notesprojectapp.viewmodel.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TemplateFragment : Fragment() {

    private lateinit var binding: FragmentTemplateBinding
    private val adapter = TemplateAdapter(this::onClick)
    private val viewModel by viewModels<NotesViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTemplateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initBtn()

    }

    private fun initBtn() {
        binding.imgBack.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fr_container, HomeFragment())
                .addToBackStack(null)
                .commit()

        }

        binding.imgAdd.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fr_container, CreateNewFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.isNullOrEmpty()){
                    viewModel.refreshTemplatesNotes(id = String())
                } else{
                    viewModel.searchTemp(p0.toString())
                }
            }

        })
    }

    private fun onClick(model: TemplateNotesModel) {
        val fragment = TemplateNoteFragment()
        val bundle = Bundle()
        bundle.putString("id", model.id)
        fragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fr_container, fragment)
            .addToBackStack(null)
            .commit()

    }

    private fun initAdapter() {
        binding.rv.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.notesHome.collect { notes ->
                adapter.submitList(notes.templateNotes)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        val userId = "your_user_id_here" // Замените на актуальный id пользователя
        viewModel.refreshTemplatesNotes(userId) // Вызовите функцию обновления списка заметок для конкретного пользователя
    }
}