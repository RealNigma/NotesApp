package com.realnigma.notesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter internal constructor(context : Context) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Note>()

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var noteItemView: TextView = itemView.findViewById(R.id.noteTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = notes[position]
        holder.noteItemView.text = current.text
    }

    internal fun getAllNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = notes.size
}