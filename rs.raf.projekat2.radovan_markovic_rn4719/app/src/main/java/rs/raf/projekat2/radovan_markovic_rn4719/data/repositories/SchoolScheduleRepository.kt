package rs.raf.projekat2.radovan_markovic_rn4719.data.repositories

import io.reactivex.Observable
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.*

interface SchoolScheduleRepository {

    fun fetchAll(): Observable<Resource<Unit>>
    fun getAllFiltered(group: String,day: String,mixed: String ): Observable<Resource<List<SchoolSchedule>>>
    fun getAll(): Observable<Resource<List<SchoolSchedule>>>
    fun getAllDays(): Observable<Resource<List<Day>>>
    fun getAllGroups(): Observable<Resource<List<Group>>>
}