package rs.raf.projekat2.radovan_markovic_rn4719.data.repositories

import io.reactivex.Observable
import rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.local.DayDao
import rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.local.GroupDao
import rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.local.SchoolScheduleDao
import rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.remote.SchoolScheduleService
import rs.raf.projekat2.radovan_markovic_rn4719.data.models.*

class SchoolScheduleRepositoryImp(
    private val localDataSource: SchoolScheduleDao,
    private val localDataSource2: GroupDao,
    private val localDataSource3: DayDao,
    private val remoteDataSource: SchoolScheduleService
):SchoolScheduleRepository {

    override fun fetchAll(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAll()
            .doOnNext {
                val entities = it.map {
                        val l: List<String> = it.grupe.split(",")
                        val tmp: SchoolScheduleResponse = it
                        lateinit var sch: SchoolScheduleEntity
                        l.forEach {
                                sch = SchoolScheduleEntity(
                                    null,
                                    tmp.predmet,
                                    tmp.tip,
                                    tmp.nastavnik,
                                    it.trim(),
                                    tmp.dan,
                                    tmp.termin,
                                    tmp.ucionica
                                )

                        }
                        sch
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                val groups: MutableList<String> = mutableListOf()
                val days: MutableList<String> = mutableListOf()
                it.map {
                    val l: List<String> = it.grupe.split(",")
                    if(!days.contains(it.dan)) {
                        days.add(it.dan)
                    }
                    l.forEach {
                        if(!groups.contains(it.trim())) {
                            groups.add(it.trim())
                        }
                    }
                }
                val daysEntity = days.map {
                    DayEntity(
                        null,
                        it
                    )
                }
                val groupsEntity = groups.map {
                    GroupEntity(
                        null,
                        it
                    )
                }
                localDataSource2.deleteAndInsertAll(groupsEntity)
                localDataSource3.deleteAndInsertAll(daysEntity)
                Resource.Success(Unit)
            }
    }

    override fun getAllFiltered(
        group: String,
        day: String,
        mixed: String
    ): Observable<Resource<List<SchoolSchedule>>> {
        var groupp: String = if(group == "Group" || group == "Grupa") {
            "%"
        } else {
            group
        }
        var dayy: String = if(day == "Day" || day == "Dan") {
            "%"
        } else {
            day
        }
        var mixedd: String = "$mixed%"
        println(dayy)
        return localDataSource
            .getFiltered(dayy,groupp,mixedd)
            .map {
                Resource.Success<List<SchoolSchedule>>(it.map {
                    SchoolSchedule(
                        it.subject,
                        it.type,
                        it.professor,
                        it.groupe,
                        it.day,
                        it.time,
                        it.classroom
                    )})
            }
    }

    override fun getAll(): Observable<Resource<List<SchoolSchedule>>> {
        return localDataSource
            .getAll()
            .map {
                Resource.Success<List<SchoolSchedule>>(it.map {
                    SchoolSchedule(
                        it.subject,
                        it.type,
                        it.professor,
                        it.groupe,
                        it.day,
                        it.time,
                        it.classroom
                    )
                    })

            }
    }

    override fun getAllGroups(): Observable<Resource<List<Group>>> {
        return localDataSource2
            .getAll()
            .map {
                Resource.Success<List<Group>>(it.map {
                    Group(
                        it.groupe
                    )})
            }
    }

    override fun getAllDays(): Observable<Resource<List<Day>>> {
        return localDataSource3
            .getAll()
            .map {
                Resource.Success<List<Day>>(it.map {
                    Day(
                        it.day
                    )})
            }
    }


}