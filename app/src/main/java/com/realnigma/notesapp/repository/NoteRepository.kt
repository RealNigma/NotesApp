package com.realnigma.notesapp.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.realnigma.notesapp.room.Note
import com.realnigma.notesapp.room.NoteDao

class NoteRepository (private val noteDao: NoteDao) {


val notes: LiveData<List<Note>> = noteDao.getAllNotes()


    fun deleteAllNotes() = noteDao.deleteAllNotes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertNote (note : Note) {
        noteDao.insertNote(note)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateNote (note : Note) {
        noteDao.updateNote(note)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteNote (note : Note) {
        noteDao.deleteNote(note)
    }
}