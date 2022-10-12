package com.geancarloleiva.jetcompweather_14.viewmodel.favorite

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geancarloleiva.jetcompweather_14.model.Favorite
import com.geancarloleiva.jetcompweather_14.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: WeatherDbRepository
) : ViewModel() {

    private val _favoriteLst = MutableStateFlow<List<Favorite>>(emptyList())
    val favoriteLst = _favoriteLst.asStateFlow()

    val fav = MutableLiveData<Favorite>(null)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites()
                .distinctUntilChanged()
                .collect { lstFav ->
                    if (lstFav.isNullOrEmpty()) {
                        Log.d("GCLV ERROR", "Fav list is empty")
                    } else {
                        _favoriteLst.value = lstFav
                    }
                }
        }
    }

    fun getFavoriteById(city: String) {
        viewModelScope.launch{
            fav.postValue(repository.getFavoriteById(city))
        }
    }

    fun createFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.createFavorite(favorite)
        }
    }

    fun updateFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.updateFavorite(favorite)
        }
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
        }
    }

    fun deleteAllFavorites(){
        viewModelScope.launch {
            repository.deleteAllFavorites()
        }
    }
}