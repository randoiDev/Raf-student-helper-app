package rs.raf.projekat2.radovan_markovic_rn4719.data.repositories

import io.reactivex.Observable
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Note
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Resource

interface NoteRepository {

    var archived:Boolean

    fun getAll(value: Boolean): Observable<Resource<List<Note>>>
    fun insertNote(title:String,content:String): Observable<Resource<Unit>>
    fun updateNote(note: Note): Observable<Resource<Unit>>
    fun deleteNote(note: Note): Observable<Resource<Unit>>
    fun getFiltered(mixed:String): Observable<Resource<List<Note>>>
}