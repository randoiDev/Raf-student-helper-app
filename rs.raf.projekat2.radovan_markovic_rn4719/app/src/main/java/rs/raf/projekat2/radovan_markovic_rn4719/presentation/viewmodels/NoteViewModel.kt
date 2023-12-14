package rs.raf.projekat2.radovan_markovic_rn4719.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Note
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Resource
import rs.raf.projekat2.radovan_markovic_rn4719.data.repositories.NoteRepository
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.contracts.NoteContract
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.ActionsNoteState
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.NoteState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class NoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel(), NoteContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val noteState: MutableLiveData<NoteState> = MutableLiveData()
    override val addDone: MutableLiveData<ActionsNoteState> = MutableLiveData()
    override val deleteDone: MutableLiveData<ActionsNoteState> = MutableLiveData()
    override val updateDone: MutableLiveData<ActionsNoteState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()
    private val publishSubject2: PublishSubject<Boolean> = PublishSubject.create()
    private val publishSubject3: PublishSubject<Note> = PublishSubject.create()
    private val publishSubject4: PublishSubject<Note> = PublishSubject.create()
    private val publishSubject5: PublishSubject<Note> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                noteRepository
                    .getFiltered(it)
                    .startWith(Resource.Loading())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }.subscribe(
                {
                    when(it) {
                        is Resource.Success -> noteState.value = NoteState.Success(it.data)
                        is Resource.Loading -> noteState.value = NoteState.Loading
                        is Resource.Error -> noteState.value = NoteState.Error("Error happened while fetching filtered data from db")

                    }
                },
                {
                    noteState.value = NoteState.Error("Error happened while fetching filtered data from db")
                }
            )
        subscriptions.add(subscription)

        val subscription2 = publishSubject2
            .switchMap {
                noteRepository
                    .getAll(it)
                    .startWith(Resource.Loading())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }.subscribe(
                {
                    when(it) {
                        is Resource.Success -> noteState.value = NoteState.Success(it.data)
                        is Resource.Loading -> noteState.value = NoteState.Loading
                        is Resource.Error -> noteState.value = NoteState.Error("Error happened while getting data from db")
                    }
                },
                {
                    noteState.value = NoteState.Error("Error happened while getting data from db")
                }
            )

        subscriptions.add(subscription2)

        val subscription3 = publishSubject3
            .switchMap {
                noteRepository
                    .insertNote(it.title,it.content)
                    .startWith(Resource.Loading())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }.subscribe(
                {
                    when(it) {
                        is Resource.Success -> addDone.value = ActionsNoteState.Success("Note added successfully")
                        is Resource.Error -> addDone.value = ActionsNoteState.Error("Error happened while adding note")
                        else -> addDone.value = ActionsNoteState.Loading
                    }
                },
                {
                    updateDone.value = ActionsNoteState.Error("Error happened while adding note")
                }
            )

        subscriptions.add(subscription3)

        val subscription4 = publishSubject4
            .switchMap {
                noteRepository
                    .updateNote(it)
                    .startWith(Resource.Loading())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }.subscribe(
                {
                    when(it) {
                        is Resource.Success -> updateDone.value = ActionsNoteState.Success("Note updated successfully")
                        is Resource.Error -> updateDone.value = ActionsNoteState.Error("Error happened while updating note")
                        else -> updateDone.value = ActionsNoteState.Loading
                    }
                },
                {
                    updateDone.value = ActionsNoteState.Error("Error happened while updating note")
                }
            )

        subscriptions.add(subscription4)

        val subscription5 = publishSubject5
            .switchMap {
                noteRepository
                    .deleteNote(it)
                    .startWith(Resource.Loading())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }.subscribe(
                {
                    when(it) {
                        is Resource.Success -> deleteDone.value = ActionsNoteState.Success("Note deleted successfully")
                        is Resource.Error -> deleteDone.value = ActionsNoteState.Error("Error happened while deleting note")
                        else -> deleteDone.value = ActionsNoteState.Loading
                    }
                },
                {
                    deleteDone.value = ActionsNoteState.Error("Error happened while deleting note")
                }
            )

        subscriptions.add(subscription5)

    }

    override fun getAllNotes(value: Boolean) {
        publishSubject2.onNext(value)
    }

    override fun getFilteredNotes(mixed: String) {
        publishSubject.onNext(mixed)
    }

    override fun addNote(title: String, content: String) {
        val note = Note(null,title, content, false)
        publishSubject3.onNext(note)
    }

    override fun deleteNote(note: Note) {
        publishSubject5.onNext(note)
    }

    override fun updateNote(note: Note) {
        publishSubject4.onNext(note)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}