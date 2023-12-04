package br.com.lucolimac.qrmanager.adapter

import br.com.lucolimac.qrmanager.data.FavoriteLink

fun interface OnFavoriteClickListener {
    fun onQrCodeClick(favoriteLink: FavoriteLink)
}