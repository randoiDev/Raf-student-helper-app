package rs.raf.projekat2.radovan_markovic_rn4719.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days")
data class DayEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val day: String,
)