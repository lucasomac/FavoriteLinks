package br.com.lucolimac.favoritelinks.ui.componnets

import br.com.lucolimac.favoritelinks.data.FavoriteLink

interface OnFavoriteClickListener {
    fun onFavoriteLinkClick(favoriteLink: FavoriteLink)
    fun onEditClick(favoriteLink: FavoriteLink)
    fun onDeleteClick(favoriteLink: FavoriteLink)
}