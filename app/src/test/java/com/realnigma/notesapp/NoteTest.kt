package com.realnigma.notesapp

import com.realnigma.notesapp.room.Note
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.Assert.assertEquals
import java.util.*

class NoteTest {
    private val currentDate: Date = Calendar.getInstance().time

    @Test
    internal fun isNotesEqual() {
        val note1 = Note(
            1,
            "Hello",
            " Hello World!",
            currentDate,
            currentDate
        )
        val note2 = Note(
            1,
            "Hello",
            " Hello World!",
            currentDate,
            currentDate
        )

        assertEquals(note1, note2)
        println("The notes are equal")
    }

    @Test
    internal fun isNotesNoteEqual() {

        val note1 = Note(
            1,
            "Hello",
            " Hello World!",
            currentDate,
            currentDate
        )
        val note2 = Note(
            2,
            "Goodbye",
            " Goodbye World!",
            currentDate,
            currentDate
        )

        assertNotEquals(note1, note2)
        println("The note aren't equal")


    }


}