package br.com.lucolimac.favoritelinks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lucolimac.favoritelinks.domain.FavoriteLinkUseCase
import br.com.lucolimac.favoritelinks.domain.entity.FavoriteLink
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteLinkViewModel(
    private val favoriteLinkUseCase: FavoriteLinkUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private var _listOfFavoriteLinksModel: MutableStateFlow<List<FavoriteLink>> =
        MutableStateFlow(listOf())
    val listOfFavoriteLinksModel: StateFlow<List<FavoriteLink>> =
        _listOfFavoriteLinksModel.asStateFlow()

    fun createFavoriteLink(favoriteLink: FavoriteLink) {
        viewModelScope.launch(dispatcher) {
            favoriteLinkUseCase.insertFavoriteLink(favoriteLink)
        }
    }

    fun getFavoriteLinksByTitle(title: String) {
        viewModelScope.launch(dispatcher) {
            favoriteLinkUseCase.getFavoriteLinkByTitle(title)
        }
    }

    fun getFavoriteLinks() {
        viewModelScope.launch(dispatcher) {
            _listOfFavoriteLinksModel.value = favoriteLinkUseCase.getAllFavoriteLinks()
        }
    }

    fun updateFavoriteLink(favoriteLink: FavoriteLink) {
        viewModelScope.launch(dispatcher) {
            favoriteLinkUseCase.updateFavoriteLink(favoriteLink)
        }
    }

    fun deleteFavoriteLink(favoriteLink: FavoriteLink) {
        viewModelScope.launch(dispatcher) {
            favoriteLinkUseCase.deleteFavoriteLink(favoriteLink)
            _listOfFavoriteLinksModel.value = favoriteLinkUseCase.getAllFavoriteLinks()
        }
    }
}