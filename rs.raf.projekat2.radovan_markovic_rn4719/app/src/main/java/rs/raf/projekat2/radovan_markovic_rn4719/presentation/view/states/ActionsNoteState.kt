package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states

sealed class ActionsNoteState {
    object Loading:ActionsNoteState()
    data class Success(val message: String): ActionsNoteState()
    data class Error(val message: String): ActionsNoteState()
}