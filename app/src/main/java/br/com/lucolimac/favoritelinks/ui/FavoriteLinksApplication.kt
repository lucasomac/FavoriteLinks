package br.com.lucolimac.favoritelinks.ui

import android.app.Application
import br.com.lucolimac.favoritelinks.framework.di.Dependencies.favoriteLinksModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class FavoriteLinksApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            allowOverride(true)
            androidLogger()
            // Reference Android context
            androidContext(this@FavoriteLinksApplication)
            // Load modules
            modules(listOf(favoriteLinksModule))
        }
    }
}