package com.realnigma.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.realnigma.notesapp.repository.NoteRepository
import com.realnigma.notesapp.room.Note
import com.realnigma.notesapp.room.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel (application: Application) : AndroidViewModel(application) {

    private val repository : NoteRepository

    val notes: LiveData<List<Note>>

    init {
        val noteDao = NoteDatabase.getDatabase(application, viewModelScope).noteDao()
        repository = NoteRepository(noteDao)
        notes = repository.notes
    }

    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllNotes()
    }

    fun insertNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
    }

    fun updateNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNote(note)
    }

    fun deleteNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(note)
    }

}