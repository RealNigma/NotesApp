package com.realnigma.notesapp

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class NoteRepository (private val noteDao: NoteDao) {


val notes: LiveData<List<Note>> = noteDao.getAllNotes()


    fun deleteAllNotes() = noteDao.deleteAllNotes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertNote (note : Note) {
        noteDao.insertNote(note)
    }
}