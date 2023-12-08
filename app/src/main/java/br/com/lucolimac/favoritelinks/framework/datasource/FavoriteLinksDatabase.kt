package br.com.lucolimac.favoritelinks.framework.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.lucolimac.favoritelinks.data.datasource.FavoriteLinkDAO
import br.com.lucolimac.favoritelinks.data.model.FavoriteLinkModel

@Database(entities = [FavoriteLinkModel::class], version = 1)
abstract class FavoriteLinksDatabase : RoomDatabase() {
    abstract fun favoriteLinkDAO(): FavoriteLinkDAO

    companion object {
        const val DB_NAME = "favoritelinks"
    }
}