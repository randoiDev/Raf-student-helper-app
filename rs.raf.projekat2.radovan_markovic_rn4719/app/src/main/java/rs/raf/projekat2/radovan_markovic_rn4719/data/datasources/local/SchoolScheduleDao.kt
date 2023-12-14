package rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.SchoolScheduleEntity

@Dao
abstract class SchoolScheduleDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<SchoolScheduleEntity>): Completable

    @Query("DELETE FROM school_schedules")
    abstract fun deleteAll()

    @Query("SELECT * FROM school_schedules WHERE day LIKE :day AND groupe LIKE :groupp AND professor LIKE :mixed OR day LIKE :day AND groupe LIKE :groupp AND subject LIKE :mixed")
    abstract fun getFiltered(day: String,groupp: String,mixed:String): Observable<List<SchoolScheduleEntity>>

    @Query("SELECT * FROM school_schedules")
    abstract fun getAll(): Observable<List<SchoolScheduleEntity>>

    @Transaction
    open fun deleteAndInsertAll(entities: List<SchoolScheduleEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
}