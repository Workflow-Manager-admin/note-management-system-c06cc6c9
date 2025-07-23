package com.example.notesfrontend.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.notesfrontend.R
import com.example.notesfrontend.api.ApiClient
import com.example.notesfrontend.model.AuthRequest
import com.example.notesfrontend.util.PrefHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val usernameInput = findViewById<EditText>(R.id.inputUsername)
        val passwordInput = findViewById<EditText>(R.id.inputPassword)
        val loginBtn = findViewById<Button>(R.id.buttonLogin)
        val signupLink = findViewById<TextView>(R.id.linkSignup)

        loginBtn.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val pwd = passwordInput.text.toString()
            if (username.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(this, "Enter all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val response = ApiClient.apiService.login(AuthRequest(username, pwd))
                    if (response.isSuccessful) {
                        val token = response.body()?.access_token
                        if (token != null) {
                            PrefHelper.saveToken(this@LoginActivity, token)
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Invalid response!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid username or password!", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        signupLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
