package com.realnigma.notesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel (application: Application) : AndroidViewModel(application) {

    private var repository : NoteRepository

    val notes: LiveData<List<Note>>

    init {
        val noteDao = NoteDatabase.getDatabase(application)?.noteDao()
        repository = noteDao?.let { NoteRepository(it) }!!
        notes = repository.notes
    }

    fun deleteAllNotes() = repository.deleteAllNotes()

    fun insertNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
    }

}