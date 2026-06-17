package com.example.kotlinlearn.ui.weather

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinlearn.mvvm.data.repository.NetworkResult
import com.example.kotlinlearn.databinding.ActivityWeatherDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  WeatherDetailActivity — 单日天气详情页                                      ║
// ║                                                                             ║
// ║  接收 HomeFragment 传来的单日天气数据 (Intent extras)                         ║
// ║  展示：大图标、温度、最高/最低、风速、降水量、生活建议                           ║
// ║                                                                             ║
// ║  如果 date 是 "today" → 从 API 实时拉取 (Demo/实战页入口)                     ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class WeatherDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATE = "date"; const val EXTRA_MAX = "max"
        const val EXTRA_MIN = "min"; const val EXTRA_CODE = "code"
        const val EXTRA_WIND = "wind"; const val EXTRA_PRECIP = "precip"
    }

    private lateinit var b: ActivityWeatherDetailBinding
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityWeatherDetailBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.toolbar.setNavigationOnClickListener { finish() }

        val date = intent.getStringExtra(EXTRA_DATE) ?: "unknown"

        if (date == "today") {
            loadFromApi()   // 从 API 实时拉取
        } else {
            displayData(date) // 直接展示传入数据
        }
    }

    /** 展示传入的天气数据 */
    private fun displayData(date: String) {
        val max = intent.getDoubleExtra(EXTRA_MAX, 0.0)
        val min = intent.getDoubleExtra(EXTRA_MIN, 0.0)
        val code = intent.getIntExtra(EXTRA_CODE, 0)
        val wind = intent.getDoubleExtra(EXTRA_WIND, 0.0)
        val precip = intent.getDoubleExtra(EXTRA_PRECIP, 0.0)
        val humidity = estimateHumidity(code, precip)

        val (emoji, desc) = weatherDesc(code)
        val bgColor = when {
            code == 0 -> "#2196F3"; code in 1..3 -> "#78909C"
            code >= 51 -> "#455A64"; else -> "#1565C0"
        }

        with(b) {
            headerBg.setBackgroundColor(Color.parseColor(bgColor))
            tvWeatherIcon.text = emoji; tvTemp.text = "${max.toInt()}°C"
            tvDesc.text = desc; tvDate.text = date
            tvMaxTemp.text = "${max.toInt()}°C"; tvMinTemp.text = "${min.toInt()}°C"
            tvWindSpeed.text = "${wind.toInt()} km/h"
            tvPrecip.text = "${"%.1f".format(precip)} mm"
            tvHumidity.text = "$humidity%"; tvCode.text = code.toString()
            tvAdvice.text = makeAdvice(code, max, min, precip)
        }
    }

    /** 从 API 实时获取今日天气 */
    private fun loadFromApi() {
        scope.launch {
            when (val result = WeatherRepository().getWeather()) {
                is NetworkResult.Success -> {
                    val today = result.data.firstOrNull()
                    if (today != null) {
                        with(intent) {
                            putExtra(EXTRA_DATE, today.date)
                                .putExtra(EXTRA_MAX, today.maxTemp)
                                .putExtra(EXTRA_MIN, today.minTemp)
                                .putExtra(EXTRA_CODE, today.weatherCode)
                                .putExtra(EXTRA_WIND, today.windSpeed)
                                .putExtra(EXTRA_PRECIP, today.precip)
                        }
                        displayData(today.date)
                    }
                }
                is NetworkResult.Error -> {
                    intent.apply {
                        putExtra(EXTRA_DATE, "2024-01-15").putExtra(EXTRA_MAX, 25.0)
                        putExtra(EXTRA_MIN, 15.0).putExtra(EXTRA_CODE, 0)
                        putExtra(EXTRA_WIND, 12.0).putExtra(EXTRA_PRECIP, 0.0)
                    }
                    displayData("2024-01-15")
                }
                is NetworkResult.Loading -> {}
            }
        }
    }

    /** 估算湿度（Open-Meteo 免费版不直接提供湿度） */
    private fun estimateHumidity(code: Int, precip: Double): Int = when {
        precip > 0 -> 70 + (precip * 5).toInt().coerceAtMost(25)
        code == 0 -> 40; code in 1..3 -> 55; code in 45..48 -> 85
        else -> 60
    }

    /** 根据天气生成生活建议 */
    private fun makeAdvice(code: Int, max: Double, min: Double, precip: Double): String = when {
        code == 0 && max > 30 -> "高温天气，注意防晒和补水。建议避免中午户外运动。"
        code == 0 && max in 20.0..30.0 -> "天气晴好，适合户外运动。早晚温差${"%.0f".format(max - min)}°C，记得添衣。"
        code == 0 -> "天气晴朗，气温舒适。适合散步、跑步等户外活动。"
        code in 1..3 -> "多云天气，适合户外活动。紫外线较弱。"
        code >= 51 && code <= 65 -> "有雨，出门请带伞。路面湿滑，注意安全。"
        code in 71..75 -> "下雪天气，注意保暖。路面可能结冰。"
        precip > 5 -> "降水量较大，建议减少外出。"
        else -> "注意查看天气预报，合理安排出行计划。"
    }

    override fun onDestroy() { super.onDestroy(); scope.cancel() }
}
