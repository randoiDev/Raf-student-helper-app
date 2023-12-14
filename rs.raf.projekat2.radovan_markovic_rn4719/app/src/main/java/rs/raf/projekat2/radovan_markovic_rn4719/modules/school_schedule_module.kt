package rs.raf.projekat2.radovan_markovic_rn4719.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.local.StudentHelperDataBase
import rs.raf.projekat2.radovan_markovic_rn4719.data.datasources.remote.SchoolScheduleService
import rs.raf.projekat2.radovan_markovic_rn4719.data.repositories.SchoolScheduleRepository
import rs.raf.projekat2.radovan_markovic_rn4719.data.repositories.SchoolScheduleRepositoryImp
import rs.raf.projekat2.radovan_markovic_rn4719.presentation.viewmodels.SchoolScheduleViewModel

val schoolScheduleModule = module {

    viewModel { SchoolScheduleViewModel(schoolScheduleRepository = get()) }

    single<SchoolScheduleRepository> { SchoolScheduleRepositoryImp(localDataSource = get(), localDataSource2 = get(), localDataSource3 = get(), remoteDataSource = get()) }

    single { get<StudentHelperDataBase>().getSchoolScheduleDao() }

    single { get<StudentHelperDataBase>().getGroupDao() }

    single { get<StudentHelperDataBase>().getDayDao() }

    single<SchoolScheduleService> { create(retrofit = get()) }

}

