package com.example.notesfrontend.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.notesfrontend.R
import com.example.notesfrontend.api.ApiClient
import com.example.notesfrontend.model.AuthRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameInput = findViewById<EditText>(R.id.inputUsername)
        val passwordInput = findViewById<EditText>(R.id.inputPassword)
        val registerBtn = findViewById<Button>(R.id.buttonRegister)
        val loginLink = findViewById<TextView>(R.id.linkLogin)

        registerBtn.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val pwd = passwordInput.text.toString()
            if (username.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(this, "Enter all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val response = ApiClient.apiService.signup(AuthRequest(username, pwd))
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "Registered! Please login.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Registration failed!", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@RegisterActivity, "Failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
