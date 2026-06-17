package com.example.kotlinlearn.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinlearn.mvvm.data.repository.NetworkResult
import com.example.kotlinlearn.ui.weather.DayWeather
import com.example.kotlinlearn.ui.weather.WeatherRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ## HomeViewModel — 首页 ViewModel (MVVM)
 *
 * 管理数据：
 * - 轮播 Banner 图片 URL 列表
 * - 16 天天气列表
 * - 加载/错误状态
 *
 * 数据流：
 * WeatherRepository.getWeather() → NetworkResult
 *   → when(result) { Success → LiveData, Error → LiveData }
 *   → Fragment.observe() → 更新 UI
 */
class HomeViewModel : ViewModel() {

    private val weatherRepo = WeatherRepository()

    /** 4 张轮播图片 URL (免费图库 picsum.photos) */
    val bannerUrls = listOf(
        "https://picsum.photos/800/400?random=1",
        "https://picsum.photos/800/400?random=2",
        "https://picsum.photos/800/400?random=3",
        "https://picsum.photos/800/400?random=4"
    )

    val bannerTitles = listOf("Kotlin 系统学习", "MVVM 实战演练", "协程与 Flow", "空安全与类型系统")

    // ── 天气 LiveData ──
    private val _weatherDays = MutableLiveData<List<DayWeather>>()
    val weatherDays: LiveData<List<DayWeather>> get() = _weatherDays

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    /** 当前轮播位置（用于自动滚动） */
    private val _currentBannerPos = MutableLiveData(0)
    val currentBannerPos: LiveData<Int> get() = _currentBannerPos

    init { loadWeather() }

    /** 加载 16 天天气数据 */
    fun loadWeather() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            when (val result = weatherRepo.getWeather()) {
                is NetworkResult.Success -> {
                    _isLoading.value = false
                    _weatherDays.value = result.data
                }
                is NetworkResult.Error -> {
                    _isLoading.value = false
                    _error.value = result.exception.message ?: "天气加载失败"
                }
                is NetworkResult.Loading -> {}
            }
        }
    }

    /** 启动 Banner 自动轮播（每 3 秒切一张） */
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
