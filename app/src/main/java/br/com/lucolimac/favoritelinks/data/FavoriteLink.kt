package br.com.lucolimac.favoritelinks.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteLink(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String?,
    val url: String,
    var counter: Long = 0
) : Parcelable