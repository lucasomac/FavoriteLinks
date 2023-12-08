package br.com.lucolimac.favoritelinks.data.repository

import br.com.lucolimac.favoritelinks.data.datasource.FavoriteLinkDAO
import br.com.lucolimac.favoritelinks.data.model.FavoriteLinkModel
import br.com.lucolimac.favoritelinks.domain.FavoriteLinkUseCase
import br.com.lucolimac.favoritelinks.domain.entity.FavoriteLink

class FavoriteLinkRepositoryImpl(private val favoriteLinkDAO: FavoriteLinkDAO) :
    FavoriteLinkUseCase {
    override suspend fun insertFavoriteLink(favoriteLink: FavoriteLink) {
        favoriteLinkDAO.createFavoriteLink(FavoriteLinkModel.fromEntity(favoriteLink))
    }

    override suspend fun updateFavoriteLink(favoriteLink: FavoriteLink) {
        favoriteLinkDAO.updateFavoriteLink(FavoriteLinkModel.fromEntity(favoriteLink))
    }

    override suspend fun deleteFavoriteLink(favoriteLink: FavoriteLink) {
        favoriteLinkDAO.deleteFavoriteLink(FavoriteLinkModel.fromEntity(favoriteLink))
    }

    override suspend fun getAllFavoriteLinks(): List<FavoriteLink> {
        return favoriteLinkDAO.getFavoriteLinks().map { it.toEntity() }
    }

    override suspend fun getFavoriteLinkByTitle(title: String): List<FavoriteLink> {
        return favoriteLinkDAO.getFavoriteLinksByTitle(title).map { it.toEntity() }
    }
}