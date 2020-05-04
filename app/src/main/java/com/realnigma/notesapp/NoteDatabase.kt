package com.realnigma.notesapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao() : NoteDao

    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "note_database"
                    )
                        .addCallback(NoteDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                    instance
            }
        }

        private class NoteDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback(){

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        //populateDatabase(database.noteDao())
                    }
                }
            }

            fun populateDatabase(noteDao : NoteDao) {
                var note = Note(0,"Hello","Hello!",0,0)
                noteDao.insertNote(note)
                note = Note(0,"World","World!",0,0)
                noteDao.insertNote(note)

            }
        }
    }


}

