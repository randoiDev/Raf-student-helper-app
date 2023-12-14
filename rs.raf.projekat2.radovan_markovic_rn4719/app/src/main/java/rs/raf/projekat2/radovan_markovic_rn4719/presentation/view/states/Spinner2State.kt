package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states

import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Day

sealed class Spinner2State {
    data class Success(val days: List<Day>): Spinner2State()
    object Loading: Spinner2State()
    data class Error(val message: String): Spinner2State()
}