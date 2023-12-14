package rs.raf.projekat2.radovan_markovic_rn4719.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SchoolScheduleResponse(
    val predmet: String,
    val tip: String,
    val nastavnik: String,
    val grupe: String,
    val dan: String,
    val termin: String,
    val ucionica: String
)