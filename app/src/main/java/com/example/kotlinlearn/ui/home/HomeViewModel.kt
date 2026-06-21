package com.example.kotlinlearn.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinlearn.mvvm.data.repository.NetworkResult
import com.example.kotlinlearn.mvvm.ui.BaseViewModel
import com.example.kotlinlearn.ui.weather.DayWeather
import com.example.kotlinlearn.ui.weather.WeatherRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ## HomeViewModel — 首页 ViewModel，继承 BaseViewModel
 */
class HomeViewModel : BaseViewModel() {

    private val weatherRepo = WeatherRepository()

    val bannerUrls = listOf(
        "https://picsum.photos/800/400?random=1",
        "https://picsum.photos/800/400?random=2",
        "https://picsum.photos/800/400?random=3",
        "https://picsum.photos/800/400?random=4"
    )
    val bannerTitles = listOf("Kotlin 系统学习", "MVVM 实战演练", "协程与 Flow", "空安全与类型系统")

    private val _weatherDays = MutableLiveData<List<DayWeather>>()
    val weatherDays: LiveData<List<DayWeather>> get() = _weatherDays

    private val _currentBannerPos = MutableLiveData(0)
    val currentBannerPos: LiveData<Int> get() = _currentBannerPos

    init { loadWeather() }

    fun loadWeather() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            when (val result = weatherRepo.getWeather()) {
                is NetworkResult.Success -> {
                    _isLoading.value = false
                    _weatherDays.value = result.data
                }
                is NetworkResult.Error -> {
                    _isLoading.value = false
                    _errorMessage.value = result.exception.message ?: "天气加载失败"
                }
                is NetworkResult.Loading -> {}
            }
        }
    }

    fun startBannerAutoScroll(totalPages: Int) {
        viewModelScope.launch {
            while (true) {
                delay(3000)
                val current = _currentBannerPos.value ?: 0
                val next = (current + 1) % totalPages
                _currentBannerPos.postValue(next)
            }
        }
    }
}
