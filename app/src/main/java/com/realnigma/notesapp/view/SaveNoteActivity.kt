package com.realnigma.notesapp.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.realnigma.notesapp.R
import com.realnigma.notesapp.room.Note
import kotlinx.android.synthetic.main.activity_save_note.*
import kotlinx.android.synthetic.main.activity_save_note.noteText
import kotlinx.android.synthetic.main.activity_save_note.noteTitle
import java.text.SimpleDateFormat
import java.util.*

class SaveNoteActivity : AppCompatActivity() {

    private var onEdit = false
    private var editNote =
        Note(
            id = 0,
            title = "",
            text = "",
            createDate = getCurrentDate(),
            editDate = getCurrentDate()
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_note)
        defineSaveType()
    }

    override fun onBackPressed() {
        if (onEdit) { saveNote() }
        super.onBackPressed()
    }

    private fun defineSaveType() {
        if (intent.hasExtra(EXTRA_REPLY)) {
            title = getString(R.string.edit_title)
            editNote = intent.getSerializableExtra(EXTRA_REPLY) as Note
            noteTitle.setText(editNote.title)
            noteText.setText(editNote.text)
            noteDate.text = getString(R.string.last_edit, getFormattedDate(editNote.editDate!!))
            onEdit = true
        } else {
            title = getString(R.string.add_title)
        }
    }

    private fun saveNote() {
        val replyIntent = Intent()

        val noteTitle = noteTitle.text.toString()
        val noteText = noteText.text.toString()

        val noteID: Int =
            if (onEdit) {
                editNote.id
            } else {
                0
            }

        val createDate: Date? =
            if (onEdit) {
                getCurrentDate()
            } else {
                editNote.createDate
            }

        val editDate = getCurrentDate()

        val note = Note(
            noteID,
            noteTitle,
            noteText,
            createDate,
            editDate
        )
        replyIntent.putExtra(EXTRA_REPLY, note)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    companion object {
        const val EXTRA_REPLY = "com.realnigma.android.notelistsql.REPLY"
    }

    private fun getCurrentDate() : Date {
        return Calendar.getInstance().time
    }

    @SuppressLint("SimpleDateFormat")
    private fun getFormattedDate(date : Date) : String {
        val format = SimpleDateFormat("HH:mm EEE, d MMM")
        return format.format(date)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_note) {
            saveNote()
        }
        return super.onOptionsItemSelected(item)
    }


}
