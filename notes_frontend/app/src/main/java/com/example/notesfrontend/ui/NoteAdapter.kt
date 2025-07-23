package com.example.notesfrontend.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.notesfrontend.R
import com.example.notesfrontend.model.Note

class NoteAdapter(
    private val notes: List<Note>,
    val onEdit: (Note) -> Unit,
    val onDelete: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.textNoteTitle)
        val contentView: TextView = itemView.findViewById(R.id.textNoteContent)
        val editBtn: ImageButton = itemView.findViewById(R.id.buttonEdit)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleView.text = note.title
        holder.contentView.text = note.content
        holder.editBtn.setOnClickListener { onEdit(note) }
        holder.deleteBtn.setOnClickListener { onDelete(note) }
    }
}
