package com.realnigma.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_same_note.*
import kotlinx.android.synthetic.main.activity_same_note.noteText
import kotlinx.android.synthetic.main.activity_same_note.noteTitle
import java.text.SimpleDateFormat
import java.util.*

class SaveNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_same_note)

        var note = Note(
            id = 0,
            title = "",
            text = "",
            createDate = getCurrentDate(),
            editDate = getCurrentDate()
        )
        var onEdit = false

        if (intent.hasExtra(EXTRA_REPLY)) {
            title = getString(R.string.edit_title)
            note  = intent.getSerializableExtra(EXTRA_REPLY) as Note
            noteTitle.setText(note.title)
            noteText.setText(note.text)
            noteDate.text = getString(R.string.last_edit, getFormattedDate(note.editDate!!))
            onEdit = true
        }
        else {
            title = getString(R.string.add_title)
        }

        saveButtonClickListener(note, onEdit)

    }

    private fun saveButtonClickListener(noteEdit : Note, onEdit : Boolean) {
        button_save.setOnClickListener {
            val replyIntent = Intent()

            val noteTitle = noteTitle.text.toString()
            val noteText = noteText.text.toString()

            val noteID : Int =
                if (onEdit) { noteEdit.id}
                else { 0 }

            val createDate : Date? =
                if (onEdit) { getCurrentDate() }
                else { noteEdit.createDate }

            val editDate = getCurrentDate()

            val note = Note(noteID, noteTitle, noteText, createDate, editDate)
            replyIntent.putExtra(EXTRA_REPLY, note)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.realnigma.android.notelistsql.REPLY"
    }

    private fun getCurrentDate() : Date {
        return Calendar.getInstance().time
    }

    private fun getFormattedDate(date : Date) : String {
        val format = SimpleDateFormat("HH:mm EEE, d MMM")
        return format.format(date)
    }

}
