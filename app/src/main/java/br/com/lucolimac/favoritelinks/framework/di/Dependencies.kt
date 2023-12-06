package br.com.lucolimac.favoritelinks.framework.di

import br.com.lucolimac.favoritelinks.data.FavoriteLinkDAO
import br.com.lucolimac.favoritelinks.framework.FavoriteLinksDatabase
import br.com.lucolimac.favoritelinks.ui.viewmodel.FavoriteLinkViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object Dependencies {
    val favoriteLinksModule = module{
        viewModelOf(::FavoriteLinkViewModel)
        factory<FavoriteLinkDAO> {
            FavoriteLinksDatabase.getDatabase(androidApplication().applicationContext)
                .favoriteLinkDAO()
        }
    }
}