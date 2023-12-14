package rs.raf.projekat2.radovan_markovic_rn4719.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
    val content: String,
    val archived: Boolean
)