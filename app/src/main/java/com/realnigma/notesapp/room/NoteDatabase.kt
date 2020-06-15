package com.realnigma.notesapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Note::class], version = 2)
@TypeConverters(Converters::class)
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
                        .fallbackToDestructiveMigration()
                        .addCallback(
                            NoteDatabaseCallback(
                                scope
                            )
                        )
                        .build()
                    INSTANCE = instance
                    instance
            }
        }

        private class NoteDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.noteDao())
                    }
                }
            }

            fun populateDatabase(noteDao : NoteDao) {
                var note = Note(
                    0,
                    "TODO",
                    "Be awesome",
                    getCurrentDate(),
                    getCurrentDate()
                )
                noteDao.insertNote(note)
                note = Note(
                    0,
                    "Lorem ipsum",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                    getCurrentDate(),
                    getCurrentDate()
                )
                noteDao.insertNote(note)
                note = Note(
                    0,
                    "TODO",
                    "Visit London",
                    getCurrentDate(),
                    getCurrentDate()
                )
                noteDao.insertNote(note)

            }

            private fun getCurrentDate() : Date {
                return Calendar.getInstance().time
            }
        }
    }


}

