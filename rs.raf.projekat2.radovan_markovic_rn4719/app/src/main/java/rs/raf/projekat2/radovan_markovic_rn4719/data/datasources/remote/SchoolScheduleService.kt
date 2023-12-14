package rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.SchoolScheduleResponse

interface SchoolScheduleService {

    @GET("raspored/json.php")
    fun getAll(): Observable<List<SchoolScheduleResponse>>
}