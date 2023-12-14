package rs.raf.projekat2.radovan_markovic_rn4719.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.FilteredSchoolSchedule
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.Resource
import rs.raf.projekat2.radovan_markovic_rn4719.data.repositories.SchoolScheduleRepository
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.contracts.SchoolScheduleContract
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.SchoolScheduleState
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.Spinner1State
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.states.Spinner2State
import timber.log.Timber

class SchoolScheduleViewModel(
    private val schoolScheduleRepository: SchoolScheduleRepository
) : ViewModel(), SchoolScheduleContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val schoolScheduleState: MutableLiveData<SchoolScheduleState> = MutableLiveData()
    override val spinner1State: MutableLiveData<Spinner1State> = MutableLiveData()
    override val spinner2State: MutableLiveData<Spinner2State> = MutableLiveData()
    private val publishSubject: PublishSubject<FilteredSchoolSchedule> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .switchMap {
                schoolScheduleRepository
                    .getAllFiltered(it.group,it.day,it.mixed)
                    .startWith(Resource.Loading())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                    }
            }
            .subscribe(
                {
                    when(it) {
                        is Resource.Success -> schoolScheduleState.value = SchoolScheduleState.Success(it.data)
                        is Resource.Loading -> schoolScheduleState.value = SchoolScheduleState.Loading
                        is Resource.Error -> schoolScheduleState.value = SchoolScheduleState.Error("Error happened while fetching filtered data from db")

                    }
                },
                {
                    schoolScheduleState.value = SchoolScheduleState.Error("Error happened while fetching filtered data from db")
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllSchoolSchedules() {
        val subscription = schoolScheduleRepository
            .fetchAll()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> schoolScheduleState.value = SchoolScheduleState.Loading
                        is Resource.Success -> {
                            schoolScheduleState.value = SchoolScheduleState.DataFetched
                        }
                        is Resource.Error -> {
                            when(schoolScheduleState.value) {
                                is SchoolScheduleState.Success -> {
                                    if((schoolScheduleState.value as SchoolScheduleState.Success).schoolSchedules.isEmpty()) {
                                        schoolScheduleState.value = SchoolScheduleState.Error("Fetching error,you are watching empty data")
                                    } else {
                                        schoolScheduleState.value = SchoolScheduleState.Error("Fetching error,you are watching cached data")
                                    }
                                }
                                else -> schoolScheduleState.value = SchoolScheduleState.Error("Fetching error")
                            }
                        }
                    }
                },
                {
                    when(schoolScheduleState.value) {
                        is SchoolScheduleState.Success -> {
                            if((schoolScheduleState.value as SchoolScheduleState.Success).schoolSchedules.isEmpty()) {
                                schoolScheduleState.value = SchoolScheduleState.Error("Fetching error,you are watching empty data")
                            } else {
                                schoolScheduleState.value = SchoolScheduleState.Error("Fetching error,you are watching cached data")
                            }
                        }
                        else -> schoolScheduleState.value = SchoolScheduleState.Error("Fetching error")
                    }
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllSchoolSchedules() {
        val subscription = schoolScheduleRepository
            .getAll()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Success -> {
                            schoolScheduleState.value = SchoolScheduleState.Success(it.data)
                        }
                        is Resource.Loading -> schoolScheduleState.value = SchoolScheduleState.Loading
                        is Resource.Error -> schoolScheduleState.value = SchoolScheduleState.Error("Error happened while gathering data from db")

                    }
                },
                {
                    schoolScheduleState.value = SchoolScheduleState.Error("Error happened while gathering data from db")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllDays() {
        val subscription = schoolScheduleRepository
            .getAllDays()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Success -> {
                            spinner2State.value = Spinner2State.Success(it.data)
                        }
                        is Resource.Loading -> spinner2State.value = Spinner2State.Loading
                        is Resource.Error -> spinner2State.value = Spinner2State.Error("Error happened while gathering days from db")

                    }
                },
                {
                    spinner2State.value = Spinner2State.Error("Error happened while gathering days from db")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllGroups() {
        val subscription = schoolScheduleRepository
            .getAllGroups()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Success -> {
                            spinner1State.value = Spinner1State.Success(it.data)
                        }
                        is Resource.Loading -> spinner1State.value = Spinner1State.Loading
                        is Resource.Error -> spinner1State.value = Spinner1State.Error("Error happened while gathering groups from db")

                    }
                },
                {
                    spinner1State.value = Spinner1State.Error("Error happened while gathering groups from db")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getFilteredSchoolSchedules(group: String, day: String, mixed: String) {
        publishSubject.onNext(FilteredSchoolSchedule(group,day,mixed))
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}