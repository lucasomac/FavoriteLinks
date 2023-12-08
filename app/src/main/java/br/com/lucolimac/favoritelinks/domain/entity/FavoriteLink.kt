package br.com.lucolimac.favoritelinks.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteLink(
    val id: Int = 0,
    val title: String,
    val description: String?,
    val url: String,
    var counter: Long = 0
) : Parcelable