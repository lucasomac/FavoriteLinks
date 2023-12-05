package br.com.lucolimac.qrmanager.adapter

import br.com.lucolimac.qrmanager.data.FavoriteLink

interface OnFavoriteClickListener {
    fun onFavoriteLinkClick(favoriteLink: FavoriteLink)
    fun onFavoriteLinkCLongClick(favoriteLink: FavoriteLink)
}