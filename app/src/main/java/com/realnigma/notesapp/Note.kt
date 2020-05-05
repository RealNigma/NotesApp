package com.realnigma.notesapp

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "notes")
data class Note
    (@PrimaryKey(autoGenerate = true) val id : Int,
     @ColumnInfo val title : String,
     val text : String,
     val createDate : Date?,
     val editDate : Date?
    ) : Serializable

