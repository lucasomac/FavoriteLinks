package br.com.lucolimac.favoritelinks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lucolimac.favoritelinks.data.FavoriteLink
import br.com.lucolimac.favoritelinks.data.FavoriteLinkDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteLinkViewModel(
    private val favoriteLinkDAO: FavoriteLinkDAO
) : ViewModel() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    private var _listOfFavoriteLinks: MutableStateFlow<List<FavoriteLink>> =
        MutableStateFlow(listOf())
    val listOfFavoriteLinks: StateFlow<List<FavoriteLink>> = _listOfFavoriteLinks.asStateFlow()

    fun createFavoriteLink(favoriteLink: FavoriteLink) {
        viewModelScope.launch(dispatcher) {
            favoriteLinkDAO.createFavoriteLink(favoriteLink)
        }
    }

    fun getFavoriteLinksByTitle(title: String) {
        viewModelScope.launch(dispatcher) {
            favoriteLinkDAO.getFavoriteLinksByTitle(title)
        }
    }

    fun getFavoriteLinks() {
        viewModelScope.launch(dispatcher) {
            _listOfFavoriteLinks.value = favoriteLinkDAO.getFavoriteLinks()
        }
    }

    fun updateFavoriteLink(favoriteLink: FavoriteLink) {
        viewModelScope.launch(dispatcher) {
            favoriteLinkDAO.updateFavoriteLink(favoriteLink)
        }
    }

    fun deleteFavoriteLink(favoriteLink: FavoriteLink) {
        viewModelScope.launch(dispatcher) {
            favoriteLinkDAO.deleteFavoriteLink(favoriteLink)
            _listOfFavoriteLinks.value = favoriteLinkDAO.getFavoriteLinks()
        }
    }
}