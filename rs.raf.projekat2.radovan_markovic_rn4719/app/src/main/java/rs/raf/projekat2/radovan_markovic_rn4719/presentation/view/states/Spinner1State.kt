package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states

import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Group

sealed class Spinner1State {
    data class Success(val groups: List<Group>): Spinner1State()
    object Loading: Spinner1State()
    data class Error(val message: String): Spinner1State()
}