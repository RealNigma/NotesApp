package com.realnigma.notesapp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.realnigma.notesapp.room.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("DELETE FROM notes")
    fun deleteAllNotes()

    @Insert
    fun insertNote(note : Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note : Note)

}