package rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.DayEntity

@Dao
abstract class DayDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<DayEntity>): Completable

    @Query("DELETE FROM days")
    abstract fun deleteAll()

    @Query("SELECT * FROM days")
    abstract fun getAll(): Observable<List<DayEntity>>

    @Transaction
    open fun deleteAndInsertAll(entities: List<DayEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
}