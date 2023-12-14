package rs.raf.projekat2.radovan_markovic_rn4719.data.repositories

import io.reactivex.Observable
import rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.local.NoteDao
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Note
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.NoteEntity
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Resource

class NoteRepositoryImp(
    private val localDataSource: NoteDao
):NoteRepository {

    override var archived: Boolean = true

    override fun getAll(value: Boolean): Observable<Resource<List<Note>>> {
        archived = value
        return localDataSource
            .getAll()
            .map {
                var list: MutableList<NoteEntity> = mutableListOf()
                if(archived) {
                    it.forEach {
                        if(!it.archived) {
                            list.add(it)
                        }
                    }
                } else {
                    list = it as MutableList<NoteEntity>
                }
                Resource.Success<List<Note>>(list.map {
                    Note(
                        it.id,
                        it.title,
                        it.content,
                        it.archived
                    )
                })
            }
    }

    override fun insertNote(title:String,content:String): Observable<Resource<Unit>> {
        return Observable.fromCallable {
            val noteEntity = NoteEntity(
                null,
                title,
                content,
                false
            )
            Resource.Success(localDataSource.insert(noteEntity))
        }

    }

    override fun updateNote(note: Note): Observable<Resource<Unit>> {
            return Observable.fromCallable {
                val noteEntity = localDataSource.getById(note.id)
                val updated = NoteEntity(
                    noteEntity.id,
                    note.title,
                    note.content,
                    note.archived
                )
                Resource.Success(localDataSource.update(updated))
            }

        }

    override fun deleteNote(note: Note): Observable<Resource<Unit>> {
        return Observable.fromCallable {
            val noteEntity = localDataSource.getById(note.id)
            Resource.Success(localDataSource.delete(noteEntity))
        }
    }

    override fun getFiltered(mixed: String): Observable<Resource<List<Note>>> {
        val mixedd: String = "$mixed%"
        return localDataSource
            .getFilteredNote(mixedd)
            .map {
                Resource.Success<List<Note>>(it.map {
                    Note(
                        it.id,
                        it.title,
                        it.content,
                        it.archived
                    )
                })
            }
    }
}
