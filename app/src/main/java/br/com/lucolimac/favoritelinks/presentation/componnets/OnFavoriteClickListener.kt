package br.com.lucolimac.favoritelinks.presentation.componnets

import br.com.lucolimac.favoritelinks.domain.entity.FavoriteLink

interface OnFavoriteClickListener {
    fun onFavoriteLinkClick(favoriteLink: FavoriteLink)
    fun onEditClick(favoriteLink: FavoriteLink)
    fun onDeleteClick(favoriteLink: FavoriteLink)
}