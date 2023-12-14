package rs.raf.projekat2.radovan_markovic_rn4719.presentation.contracts

import androidx.lifecycle.LiveData
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Note
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.ActionsNoteState
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.NoteState

interface NoteContract {

    interface ViewModel {

        val noteState: LiveData<NoteState>
        val addDone: LiveData<ActionsNoteState>
        val deleteDone: LiveData<ActionsNoteState>
        val updateDone: LiveData<ActionsNoteState>

        fun getAllNotes(value: Boolean)
        fun getFilteredNotes(mixed: String)
        fun addNote(title: String,content: String)
        fun deleteNote(note: Note)
        fun updateNote(note: Note)
    }

}