package br.com.lucolimac.favoritelinks.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.lucolimac.favoritelinks.data.model.FavoriteLinkModel

@Dao
interface FavoriteLinkDAO {
    @Insert
    fun createFavoriteLink(favoriteLinkModel: FavoriteLinkModel)

    @Query("SELECT * FROM favoritelinkmodel WHERE title LIKE :title ORDER BY title")
    suspend fun getFavoriteLinksByTitle(title: String): List<FavoriteLinkModel>

    @Query("SELECT * FROM favoritelinkmodel ORDER BY title")
    suspend fun getFavoriteLinks(): List<FavoriteLinkModel>

    @Update
    suspend fun updateFavoriteLink(favoriteLinkModel: FavoriteLinkModel)

    @Delete
    suspend fun deleteFavoriteLink(favoriteLinkModel: FavoriteLinkModel)
}