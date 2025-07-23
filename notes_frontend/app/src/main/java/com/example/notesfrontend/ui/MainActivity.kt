package com.example.notesfrontend.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesfrontend.R
import com.example.notesfrontend.api.ApiClient
import com.example.notesfrontend.model.Note
import com.example.notesfrontend.util.PrefHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: NoteAdapter
    private val notes = mutableListOf<Note>()
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        token = PrefHelper.getToken(this) ?: ""
        if (token.isEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        setContentView(R.layout.activity_main)

        val searchEdit = findViewById<EditText>(R.id.inputSearch)
        val searchBtn = findViewById<Button>(R.id.buttonSearch)
        val notesList = findViewById<RecyclerView>(R.id.recyclerNotes)
        val fab = findViewById<FloatingActionButton>(R.id.fabAddNote)
        val logoutBtn = findViewById<Button>(R.id.buttonLogout)

        adapter = NoteAdapter(notes,
            onEdit = { note -> openEditNote(note) },
            onDelete = { note -> confirmDelete(note) }
        )
        notesList.layoutManager = LinearLayoutManager(this)
        notesList.adapter = adapter

        fun fetchNotes(searchQuery: String? = null) {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val res = ApiClient.apiService.getNotes("Bearer $token", searchQuery)
                    if (res.isSuccessful) {
                        notes.clear()
                        notes.addAll(res.body() ?: emptyList())
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@MainActivity, "Error fetching notes", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fetchNotes()
        searchBtn.setOnClickListener { fetchNotes(searchEdit.text.toString()) }
        fab.setOnClickListener { openCreateNote() }
        logoutBtn.setOnClickListener {
            PrefHelper.clearToken(this)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun openEditNote(note: Note) {
        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra("note_id", note.id)
        intent.putExtra("note_title", note.title)
        intent.putExtra("note_content", note.content)
        startActivity(intent)
    }

    private fun openCreateNote() {
        val intent = Intent(this, NoteDetailActivity::class.java)
        startActivity(intent)
    }

    private fun confirmDelete(note: Note) {
        AlertDialog.Builder(this)
            .setTitle("Delete Note?")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Yes") { _, _ -> deleteNote(note) }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteNote(note: Note) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = ApiClient.apiService.deleteNote("Bearer $token", note.id)
                if (res.isSuccessful) {
                    notes.remove(note)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "Delete failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // When coming back from note detail, refresh list
        token = PrefHelper.getToken(this) ?: ""
        if (token.isNotEmpty()) {
            CoroutineScope(Dispatchers.Main).launch {
                val res = ApiClient.apiService.getNotes("Bearer $token")
                if (res.isSuccessful) {
                    notes.clear()
                    notes.addAll(res.body() ?: emptyList())
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}
