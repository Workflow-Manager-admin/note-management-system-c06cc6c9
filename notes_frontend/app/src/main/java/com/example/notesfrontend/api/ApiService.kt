package com.example.notesfrontend.api

import com.example.notesfrontend.model.AuthRequest
import com.example.notesfrontend.model.AuthResponse
import com.example.notesfrontend.model.Note
import com.example.notesfrontend.model.NoteRequest
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // PUBLIC_INTERFACE
    @POST("/auth/signup")
    suspend fun signup(@Body request: AuthRequest): Response<AuthResponse>

    // PUBLIC_INTERFACE
    @POST("/auth/login")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

    // PUBLIC_INTERFACE
    @GET("/notes/")
    suspend fun getNotes(
        @Header("Authorization") token: String,
        @Query("search") search: String? = null
    ): Response<List<Note>>

    // PUBLIC_INTERFACE
    @POST("/notes/")
    suspend fun createNote(
        @Header("Authorization") token: String,
        @Body request: NoteRequest
    ): Response<Note>

    // PUBLIC_INTERFACE
    @PUT("/notes/{note_id}")
    suspend fun updateNote(
        @Header("Authorization") token: String,
        @Path("note_id") noteId: Int,
        @Body request: NoteRequest
    ): Response<Note>

    // PUBLIC_INTERFACE
    @DELETE("/notes/{note_id}")
    suspend fun deleteNote(
        @Header("Authorization") token: String,
        @Path("note_id") noteId: Int
    ): Response<Void>
}
