package rs.raf.projekat2.radovan_markovic_rn4719.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import rs.raf.projekat2.radovan_markovic_rn4719.modules.coreModule
import rs.raf.projekat2.radovan_markovic_rn4719.modules.noteModule
import rs.raf.projekat2.radovan_markovic_rn4719.modules.schoolScheduleModule
import timber.log.Timber

class RafStudentHelperApp: Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        val modules = listOf(
            coreModule,
            schoolScheduleModule,
            noteModule
        )
        startKoin {
            androidLogger(Level.ERROR)

            androidContext(this@RafStudentHelperApp)

            androidFileProperties()

            modules(modules)
        }
    }
}