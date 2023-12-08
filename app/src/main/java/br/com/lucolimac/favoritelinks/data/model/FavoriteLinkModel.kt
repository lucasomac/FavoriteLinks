package br.com.lucolimac.favoritelinks.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.lucolimac.favoritelinks.domain.entity.FavoriteLink
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteLinkModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String?,
    val url: String,
    var counter: Long = 0
) : Parcelable {
    fun toEntity(): FavoriteLink {
        return FavoriteLink(id, title, description, url, counter)
    }

    companion object {
        fun fromEntity(favoriteLink: FavoriteLink): FavoriteLinkModel {
            return FavoriteLinkModel(
                favoriteLink.id,
                favoriteLink.title,
                favoriteLink.description,
                favoriteLink.url,
                favoriteLink.counter
            )
        }
    }
}