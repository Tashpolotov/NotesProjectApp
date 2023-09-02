package com.example.notesprojectapp.fragments.homefragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.HomeNotesModel
import com.example.notesprojectapp.databinding.ItemHomeBinding

class HomeNotesListAdapter(val onClick:(HomeNotesModel)->Unit) :
    ListAdapter<HomeNotesModel, HomeNotesListAdapter.NoteViewHolder>(HomeNotesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

   inner class NoteViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: HomeNotesModel) {
            binding.tvPoint1.text = note.textNotes
            binding.tvDate.text = note.date
            binding.tvMonth.text = note.month
            binding.tvYears.text = note.years
            binding.tvWorkList.text = note.workList
            itemView.setOnClickListener {
                onClick(note)
            }

        }
    }

    private class HomeNotesDiffCallback : DiffUtil.ItemCallback<HomeNotesModel>() {
        override fun areItemsTheSame(oldItem: HomeNotesModel, newItem: HomeNotesModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HomeNotesModel, newItem: HomeNotesModel): Boolean {
            return oldItem == newItem
        }
    }
}