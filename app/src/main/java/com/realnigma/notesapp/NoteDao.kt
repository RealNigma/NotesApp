package com.realnigma.notesapp

import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAll(): List<Note>

    @Insert
    fun insertNote(note : Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note : Note)

}