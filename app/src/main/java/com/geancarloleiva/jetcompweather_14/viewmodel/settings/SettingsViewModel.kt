package com.geancarloleiva.jetcompweather_14.viewmodel.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geancarloleiva.jetcompweather_14.model.Favorite
import com.geancarloleiva.jetcompweather_14.model.MetricUnit
import com.geancarloleiva.jetcompweather_14.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: WeatherDbRepository
) : ViewModel() {

    private val _settingsLst = MutableStateFlow<List<MetricUnit>>(emptyList())
    val settingsLst = _settingsLst.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMetricUnits()
                .distinctUntilChanged()
                .collect { lst ->
                    if(lst.isNullOrEmpty()){
                        Log.e("GCLV", "Sin data de MetricUnits", )
                    } else {
                        _settingsLst.value = lst
                    }
                }
        }
    }

    fun createMetricUnit(metricUnit: MetricUnit) {
        viewModelScope.launch {
            repository.createMetricUnit(metricUnit)
        }
    }

    fun updateMetricUnit(metricUnit: MetricUnit) {
        viewModelScope.launch {
            repository.updateMetricUnit(metricUnit)
        }
    }

    fun deleteMetricUnit(metricUnit: MetricUnit) {
        viewModelScope.launch {
            repository.deleteMetricUnit(metricUnit)
        }
    }

    fun deleteAllMetricUnits() {
        viewModelScope.launch {
            repository.deleteAllMetricUnits()
        }
    }
}