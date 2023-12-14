package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states

import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Note

sealed class NoteState {
    object Loading: NoteState()
    data class Success(val notes: List<Note>): NoteState()
    data class Error(val message: String): NoteState()
}