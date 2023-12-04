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

    @Query("SELECT * FROM qrcode ORDER BY title")
    suspend fun getQrCodeByName(): List<FavoriteLink>

    @Query("SELECT * FROM qrcode")
    suspend fun getQrCode(): List<FavoriteLink>

    @Update
    suspend fun updateQrCode(favoriteLink: FavoriteLink)

    @Delete
    suspend fun deleteQrCode(favoriteLink: FavoriteLink)
}