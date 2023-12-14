package rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.local

import androidx.room.*
import io.reactivex.Observable
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.NoteEntity

@Dao
abstract class NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: NoteEntity)

    @Query("SELECT * FROM notes")
    abstract fun getAll(): Observable<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE `title` LIKE :mixed OR `content` LIKE :mixed")
    abstract fun getFilteredNote(mixed: String) : Observable<List<NoteEntity>>

    @Update
    abstract fun update(noteEntity: NoteEntity)

    @Delete
    abstract fun delete(noteEntity: NoteEntity)

    @Query("SELECT * FROM notes WHERE id == :id")
    abstract fun getById(id: Int?): NoteEntity
}