package rs.raf.projekat2.radovan_markovic_rn4719.presentation.contracts

import androidx.lifecycle.LiveData
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.SchoolScheduleState
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.Spinner1State
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.Spinner2State

interface SchoolScheduleContract {

    interface ViewModel {

        val schoolScheduleState: LiveData<SchoolScheduleState>
        val spinner1State: LiveData<Spinner1State>
        val spinner2State: LiveData<Spinner2State>

        fun fetchAllSchoolSchedules()
        fun getAllSchoolSchedules()
        fun getAllDays()
        fun getAllGroups()
        fun getFilteredSchoolSchedules(group: String,day: String,mixed: String)
    }

}