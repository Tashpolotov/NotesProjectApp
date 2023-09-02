package com.example.notesprojectapp.template

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.HomeNotesModel
import com.example.domain.model.model
import com.example.notesprojectapp.databinding.ItemHomeBinding
import com.example.notesprojectapp.fragments.homefragment.HomeNotesListAdapter

class TempNoteAdapter (val onClick:(model)->Unit) :
    ListAdapter<model, TempNoteAdapter.NoteViewHolder>(HomeNotesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: model) {
            binding.tvWorkList.text = note.textDesc
            binding.tvPoint1.text = note.textNotes
            itemView.setOnClickListener {
                onClick(note)
            }

        }
    }

    private class HomeNotesDiffCallback : DiffUtil.ItemCallback<model>() {
        override fun areItemsTheSame(oldItem: model, newItem: model): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: model, newItem: model): Boolean {
            return oldItem == newItem
        }
    }
}