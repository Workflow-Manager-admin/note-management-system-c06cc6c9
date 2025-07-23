package com.example.notesfrontend

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notesfrontend.ui.LoginActivity
import com.example.notesfrontend.ui.MainActivity as NotesMainActivity
import com.example.notesfrontend.util.PrefHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val token = PrefHelper.getToken(this)
        if (token.isNullOrEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, NotesMainActivity::class.java))
        }
        finish()
    }
}
