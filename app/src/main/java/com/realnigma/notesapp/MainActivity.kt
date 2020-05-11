package com.realnigma.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.*
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel : NoteViewModel
    private val addNoteRequestCode = 1
    private val editNoteRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = initRecyclerView()
        initViewModel(adapter)
        fabListener()
    }

    private fun fabListener() {
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, SaveNoteActivity::class.java)
            startActivityForResult(intent, addNoteRequestCode)
        }
    }

    private fun initRecyclerView(): NoteAdapter {
        val adapter = NoteAdapter(this)
        noteRecyclerView.adapter = adapter
        noteRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        adapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, note: Note) {
                val intent = Intent(this@MainActivity, SaveNoteActivity::class.java)
                intent.putExtra(SaveNoteActivity.EXTRA_REPLY, note)
                startActivityForResult(intent, editNoteRequestCode)
            }

            override fun onDeleteButtonClick(note: Note) {
                noteViewModel.deleteNote(note)
            }
        })
        return adapter
    }

    private fun initViewModel(adapter: NoteAdapter) {
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.notes.observe(this, Observer { notes ->
            notes?.let { adapter.getAllNotes(it) }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        when(resultCode) {
            Activity.RESULT_OK -> when(requestCode) {
                                    1 -> addNewNote(intentData)
                                    2 -> editNote(intentData)
                                    }
            //Activity.RESULT_CANCELED -> notSavedToast()
        }
    }

    private fun editNote(intentData: Intent?) {
        intentData?.let {
            val note = intentData.getSerializableExtra(SaveNoteActivity.EXTRA_REPLY) as Note
            noteViewModel.updateNote(note)
            Unit
        }
    }

    private fun notSavedToast() {
        Toast.makeText(
            applicationContext,
            R.string.note_is_not_saved,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun addNewNote(intentData: Intent?) {
        intentData?.let {
            val note = intentData.getSerializableExtra(SaveNoteActivity.EXTRA_REPLY) as Note
            noteViewModel.insertNote(note)
            Unit
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll) {
            noteViewModel.deleteAllNotes()
        }
        return super.onOptionsItemSelected(item)
    }



}
