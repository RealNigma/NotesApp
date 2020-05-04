package com.realnigma.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_new_note.*
import java.time.LocalDate

class NewNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        button_save.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(noteTitle.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val noteTitle = noteTitle.text.toString()
                val noteText = noteText.text.toString()
                val note = Note(0, noteTitle, noteText,0,0)
                replyIntent.putExtra(EXTRA_REPLY, note)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.realnigma.android.notelistsql.REPLY"
    }

    fun getCurrentDate() : Long {
        //LocalDate.now().toLong()
        return 0
    }

}
