package br.com.lucolimac.qrmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteLinkDAO {
    @Insert
    fun createContact(favoriteLink: FavoriteLink)

    @Query("SELECT * FROM favoritelink WHERE title = :title ORDER BY title")
    suspend fun getFavoriteLinksByTitle(title: String): List<FavoriteLink>

    @Query("SELECT * FROM favoritelink ORDER BY title")
    suspend fun getFavoriteLinks(): List<FavoriteLink>

    @Update
    suspend fun updateFavoriteLink(favoriteLink: FavoriteLink)

    @Delete
    suspend fun deleteFavoriteLink(favoriteLink: FavoriteLink)
}