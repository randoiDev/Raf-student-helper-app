package rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.DayEntity
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.GroupEntity
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.NoteEntity
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.SchoolScheduleEntity

@Database(
    entities = [SchoolScheduleEntity::class,NoteEntity::class,DayEntity::class,GroupEntity::class],
    version = 1,
    exportSchema = false
)

abstract class StudentHelperDataBase():RoomDatabase() {
    abstract fun getSchoolScheduleDao(): SchoolScheduleDao
    abstract fun getNoteDao(): NoteDao
    abstract fun getDayDao(): DayDao
    abstract fun getGroupDao(): GroupDao
}