package br.com.lucolimac.favoritelinks.framework.di

import androidx.room.Room
import br.com.lucolimac.favoritelinks.data.datasource.FavoriteLinkDAO
import br.com.lucolimac.favoritelinks.data.repository.FavoriteLinkRepositoryImpl
import br.com.lucolimac.favoritelinks.domain.FavoriteLinkUseCase
import br.com.lucolimac.favoritelinks.framework.datasource.FavoriteLinksDatabase
import br.com.lucolimac.favoritelinks.presentation.viewmodel.FavoriteLinkViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object Dependencies {
    val favoriteLinksModule = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                FavoriteLinksDatabase::class.java,
                FavoriteLinksDatabase.DB_NAME
            ).build()
        }
        viewModelOf(::FavoriteLinkViewModel)
        single<FavoriteLinkDAO> {
            get<FavoriteLinksDatabase>().favoriteLinkDAO()
        }
        factory<FavoriteLinkUseCase> { FavoriteLinkRepositoryImpl(get()) }
    }
}