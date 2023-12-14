package rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.GroupEntity

@Dao
abstract class GroupDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<GroupEntity>): Completable

    @Query("DELETE FROM groups")
    abstract fun deleteAll()

    @Query("SELECT * FROM groups")
    abstract fun getAll(): Observable<List<GroupEntity>>

    @Transaction
    open fun deleteAndInsertAll(entities: List<GroupEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
}