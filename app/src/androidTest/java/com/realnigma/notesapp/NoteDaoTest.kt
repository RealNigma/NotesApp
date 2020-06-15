package com.realnigma.notesapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.realnigma.notesapp.room.Note
import com.realnigma.notesapp.room.NoteDao
import com.realnigma.notesapp.room.NoteDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var noteDao: NoteDao
    private lateinit var  db : NoteDatabase
    private val currentDate = Calendar.getInstance().time


    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = db.noteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun insertAndGetNote() {
        val note = Note(
            1,
            "Hello",
            "World",
            currentDate,
            currentDate
        )
        noteDao.insertNote(note)
        val allNotes = noteDao.getAllNotes().waitForValue()
        assertEquals(allNotes[0], note)
    }

    @Test
    @Throws(Exception::class)
    fun getAllNotes() {
        val note1 = Note(
            1,
            "Hello",
            "World",
            currentDate,
            currentDate
        )
        noteDao.insertNote(note1)
        val note2 = Note(
            2,
            "Goodbye",
            "World",
            currentDate,
            currentDate
        )
        noteDao.insertNote(note2)
        val allNotes = noteDao.getAllNotes().waitForValue()
        assertEquals(allNotes[0], note1)
        assertEquals(allNotes[1], note2)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() {
        val note1 = Note(
            1,
            "Hello",
            "World",
            currentDate,
            currentDate
        )
        noteDao.insertNote(note1)
        val note2 = Note(
            2,
            "Goodbye",
            "World",
            currentDate,
            currentDate
        )
        noteDao.insertNote(note2)
        noteDao.deleteAllNotes()
        val allNotes = noteDao.getAllNotes().waitForValue()
        assertTrue(allNotes.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun updateNote() {
        val note = Note(
            1,
            "Hello",
            "World",
            currentDate,
            currentDate
        )
        noteDao.insertNote(note)
        val updatedNote = Note(
            1,
            "Goodbye",
            "World!",
            currentDate,
            currentDate
        )
        noteDao.updateNote(updatedNote)
        val allNotes = noteDao.getAllNotes().waitForValue()
        assertEquals(allNotes[0], updatedNote)
    }

    @Test
    @Throws(Exception::class)
    fun deleteNote() {
        val note = Note(
            1,
            "Hello",
            "World",
            currentDate,
            currentDate
        )
        noteDao.insertNote(note)
        noteDao.deleteNote(note)
        val allNotes = noteDao.getAllNotes().waitForValue()
        assertTrue(allNotes.isEmpty())
    }


}
