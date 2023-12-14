package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states

import rs.raf.projekat2.radovan_markovic_rn4719.data.models.SchoolSchedule

sealed class SchoolScheduleState {
    object Loading: SchoolScheduleState()
    object DataFetched: SchoolScheduleState()
    data class Success(val schoolSchedules: List<SchoolSchedule>): SchoolScheduleState()
    data class Error(val message: String): SchoolScheduleState()
}