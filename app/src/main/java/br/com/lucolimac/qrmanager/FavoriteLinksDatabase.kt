package br.com.lucolimac.qrmanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.lucolimac.qrmanager.data.FavoriteLink
import br.com.lucolimac.qrmanager.data.FavoriteLinkDAO

@Database(entities = [FavoriteLink::class], version = 1)
abstract class FavoriteLinksDatabase : RoomDatabase() {
    abstract fun contactDAO(): FavoriteLinkDAO

    companion object {
        @Volatile
        private var INSTANCE: FavoriteLinksDatabase? = null
        fun getDatabase(context: Context): FavoriteLinksDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, FavoriteLinksDatabase::class.java, "favoritelinks.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}