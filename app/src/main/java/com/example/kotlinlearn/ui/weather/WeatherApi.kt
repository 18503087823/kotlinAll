package com.example.kotlinlearn.ui.weather

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  WeatherApi — Open-Meteo 免费天气 API (无需 API Key)                       ║
// ║  https://open-meteo.com/                                                    ║
// ╚══════════════════════════════════════════════════════════════════════════════╝

/** API 返回的 JSON 根对象 */
data class WeatherResponse(
    val daily: DailyData
)

data class DailyData(
    val time: List<String>,
    @SerializedName("temperature_2m_max") val temperature_2m_max: List<Double>,
    @SerializedName("temperature_2m_min") val temperature_2m_min: List<Double>,
    @SerializedName("weather_code")       val weathercode: List<Int>,
    @SerializedName("precipitation_sum")  val precipitation_sum: List<Double>,
    @SerializedName("wind_speed_10m_max") val windspeed_10m_max: List<Double>
)

/** UI 层使用的单日天气数据（从 WeatherResponse 提取） */
data class DayWeather(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val weatherCode: Int,
    val precip: Double,
    val windSpeed: Double
)

/**
 * ## Retrofit 天气接口
 *
 * GET 请求示例：
 * ```
 * https://api.open-meteo.com/v1/forecast
 *   ?latitude=39.9042&longitude=116.4074
 *   &daily=temperature_2m_max,temperature_2m_min,weathercode,precipitation_sum,windspeed_10m_max
 *   &timezone=Asia/Shanghai&forecast_days=16
 * ```
 *
 * @Query 注解 → URL 查询参数，等价于 URL 末尾的 ?key=value
 */
interface WeatherApi {

    companion object {
        const val BASE_URL = "https://api.open-meteo.com/"
        const val LATITUDE = 39.9042   // 北京纬度
        const val LONGITUDE = 116.4074 // 北京经度
    }

    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") lat: Double = LATITUDE,
        @Query("longitude") lon: Double = LONGITUDE,
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min,weather_code,precipitation_sum,wind_speed_10m_max",
        @Query("timezone") tz: String = "Asia/Shanghai",
        @Query("forecast_days") days: Int = 16
    ): WeatherResponse
}
