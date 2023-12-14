package rs.raf.projekat2.radovan_markovic_rn4719.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "school_schedules")
class SchoolScheduleEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val subject: String,
    val type: String,
    val professor: String,
    val groupe: String,
    val day: String,
    val time: String,
    val classroom: String
)