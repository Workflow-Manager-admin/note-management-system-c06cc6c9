package com.example.notesfrontend.model

data class AuthRequest(
    val username: String,
    val password: String
)

data class AuthResponse(
    val access_token: String
)

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val created_at: String?,
    val updated_at: String?
)

data class NoteRequest(
    val title: String,
    val content: String
)
