package com.realnigma.notesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter internal constructor(context : Context) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Note>()

    inner class NoteViewHolder(itemView: View, private  val interaction: OnItemClickListener?) : RecyclerView.ViewHolder(itemView) {
        var noteTitleItemView: TextView = itemView.findViewById(R.id.noteTitle)
        var noteTextItemView : TextView = itemView.findViewById(R.id.noteText)
        var noteIDItemView : TextView = itemView.findViewById(R.id.noteID)
        var noteCreateDateItemView : TextView = itemView.findViewById(R.id.noteCreateDate)
        var noteEditDateItemView : TextView = itemView.findViewById(R.id.noteEditDate)
        var noteDeleteItemView : Button = itemView.findViewById(R.id.noteDeleteButton)

        fun onItemClick(item : Note) {
            itemView.setOnClickListener {
                    interaction?.onItemClick(adapterPosition, item)
            }
        }

        fun onDeleteButtonClick(note: Note) {
            noteDeleteItemView.setOnClickListener {
                interaction?.onDeleteButtonClick(note)
            }
        }

    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = notes[position]
        holder.noteTitleItemView.text = current.title
        holder.noteTextItemView.text = current.text
        holder.noteIDItemView.text = current.id.toString()
        holder.noteCreateDateItemView.text = current.createDate.toString()
        holder.noteEditDateItemView.text = current.editDate.toString()
        holder.onItemClick(notes[position])
        holder.onDeleteButtonClick(notes[position])
    }

    internal fun getAllNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = notes.size

    interface OnItemClickListener {
        fun onItemClick(position: Int, note : Note)
        fun onDeleteButtonClick(note : Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


}