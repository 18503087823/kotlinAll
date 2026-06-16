package com.example.kotlinlearn.ui.weather

import com.example.kotlinlearn.mvvm.data.repository.NetworkResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient

/**
 * ## WeatherRepository — 天气数据仓库 (MVVM 的 Model 层)
 *
 * 调用 Open-Meteo API → 原始 JSON → 转为 List<DayWeather>
 */
class WeatherRepository {

    private val api: WeatherApi by lazy {
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
        Retrofit.Builder()
            .baseUrl(WeatherApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    /**
     * 获取北京 30 天天气 → 用 NetworkResult 密封类封装结果
     *
     * @return NetworkResult<List<DayWeather>>
     */
    suspend fun getWeather(): NetworkResult<List<DayWeather>> {
        return try {
            val resp = api.getWeather()
            // 将 API 返回的平行数组转为 List<DayWeather>
            val days = resp.daily.time.mapIndexed { i, date ->
                DayWeather(
                    date = date,
                    maxTemp = resp.daily.temperature_2m_max.getOrElse(i) { 0.0 },
                    minTemp = resp.daily.temperature_2m_min.getOrElse(i) { 0.0 },
                    weatherCode = resp.daily.weathercode.getOrElse(i) { 0 },
                    precip = resp.daily.precipitation_sum.getOrElse(i) { 0.0 },
                    windSpeed = resp.daily.windspeed_10m_max.getOrElse(i) { 0.0 }
                )
            }
            NetworkResult.Success(days.take(30))
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }
}

/** 天气编码 → 中文描述 + emoji */
fun weatherDesc(code: Int): Pair<String, String> = when (code) {
    0 -> "☀️" to "晴"
    1,2,3 -> "⛅" to "多云"
    45,48 -> "🌫" to "雾"
    51,53,55 -> "🌧" to "小雨"
    61,63,65 -> "🌧" to "雨"
    71,73,75 -> "🌨" to "雪"
    80,81,82 -> "🌦" to "阵雨"
    95,96,99 -> "⛈" to "雷暴"
    else -> "🌤" to "晴转多云"
}
