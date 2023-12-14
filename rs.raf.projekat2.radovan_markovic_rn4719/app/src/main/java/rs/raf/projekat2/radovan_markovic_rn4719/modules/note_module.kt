package rs.raf.projekat2.radovan_markovic_rn4719.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.local.StudentHelperDataBase
import rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.remote.SchoolScheduleService
import rs.raf.projekat2.radovan_markovic_rn4719.data.repositories.NoteRepository
import rs.raf.projekat2.radovan_markovic_rn4719.data.repositories.NoteRepositoryImp
import rs.raf.projekat2.radovan_markovic_rn4719.data.repositories.SchoolScheduleRepository
import rs.raf.projekat2.radovan_markovic_rn4719.data.repositories.SchoolScheduleRepositoryImp
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.viewmodels.NoteViewModel
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.viewmodels.SchoolScheduleViewModel

val noteModule = module {

    viewModel { NoteViewModel(noteRepository = get()) }

    single<NoteRepository> { NoteRepositoryImp(localDataSource = get()) }

    single { get<StudentHelperDataBase>().getNoteDao() }

}

