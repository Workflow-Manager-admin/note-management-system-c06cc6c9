package com.example.notesfrontend.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.notesfrontend.R
import com.example.notesfrontend.api.ApiClient
import com.example.notesfrontend.model.NoteRequest
import com.example.notesfrontend.util.PrefHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteDetailActivity : AppCompatActivity() {
    private var noteId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val titleEdit = findViewById<EditText>(R.id.editTitle)
        val contentEdit = findViewById<EditText>(R.id.editContent)
        val saveBtn = findViewById<Button>(R.id.buttonSave)

        noteId = intent.getIntExtra("note_id", -1).takeIf { it != -1 }
        if (noteId != null) {
            titleEdit.setText(intent.getStringExtra("note_title"))
            contentEdit.setText(intent.getStringExtra("note_content"))
        }

        saveBtn.setOnClickListener {
            val title = titleEdit.text.toString().trim()
            val content = contentEdit.text.toString().trim()
            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Title and Content required.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            CoroutineScope(Dispatchers.Main).launch {
                val token = PrefHelper.getToken(this@NoteDetailActivity) ?: ""
                try {
                    val request = NoteRequest(title, content)
                    val res = if (noteId != null) {
                        ApiClient.apiService.updateNote("Bearer $token", noteId!!, request)
                    } else {
                        ApiClient.apiService.createNote("Bearer $token", request)
                    }
                    if (res.isSuccessful) {
                        setResult(RESULT_OK)
                        finish()
                    } else {
                        Toast.makeText(this@NoteDetailActivity, "Error saving note", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@NoteDetailActivity, "Failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
