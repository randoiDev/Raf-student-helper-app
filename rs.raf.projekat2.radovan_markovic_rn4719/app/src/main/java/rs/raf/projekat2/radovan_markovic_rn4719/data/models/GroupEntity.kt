package rs.raf.projekat2.radovan_markovic_rn4719.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class GroupEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val groupe: String,
)