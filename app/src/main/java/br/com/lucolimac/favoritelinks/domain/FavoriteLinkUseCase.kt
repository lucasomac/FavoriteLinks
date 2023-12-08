package br.com.lucolimac.favoritelinks.domain

import br.com.lucolimac.favoritelinks.domain.entity.FavoriteLink

interface FavoriteLinkUseCase {
    suspend fun insertFavoriteLink(favoriteLink: FavoriteLink)

    suspend fun updateFavoriteLink(favoriteLink: FavoriteLink)

    suspend fun deleteFavoriteLink(favoriteLink: FavoriteLink)

    suspend fun getAllFavoriteLinks(): List<FavoriteLink>

    suspend fun getFavoriteLinkByTitle(title: String): List<FavoriteLink>
}