package com.example.kotlinlearn.ui.chart

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinlearn.databinding.ActivityChartDemoBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter

/**
 * ## ChartDemoActivity — MPAndroidChart 图表演示
 *
 * 4 种图表：K线 | 折线+MA5 | 成交量柱状 | 饼图
 *
 * demo 中每个图表都展示了最常用的配置项。
 */
class ChartDemoActivity : AppCompatActivity() {

    private lateinit var b: ActivityChartDemoBinding

    // ── 模拟 30 日 K 线数据 ───────────────────────────────────────────────────

    data class OHLCEntry(
        val open: Float, val high: Float, val low: Float,
        val close: Float, val volume: Float
    )

    private fun mock(): List<OHLCEntry> {
        val rng = java.util.Random(42)
        var p = 100f
        return (1..30).map {
            val c = ((p + (rng.nextFloat() - 0.45f) * 8f).coerceAtLeast(1f))
            val e = OHLCEntry(
                open = p, close = c,
                high = maxOf(p, c) + rng.nextFloat() * 5f,
                low  = minOf(p, c) - rng.nextFloat() * 5f,
                volume = rng.nextFloat() * 100_000f + 20_000f
            )
            p = c;
            e
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityChartDemoBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.toolbar.setNavigationOnClickListener { finish() }

        val data = mock()
        candle(data)
        line(data)
        bar(data)
        pie()
    }

    // ═══ 1. K 线 ═══════════════════════════════════════════════════════════════

    private fun candle(data: List<OHLCEntry>) {
        val entries = data.mapIndexed { i, d ->
            CandleEntry(i.toFloat(), d.high, d.low, d.open, d.close)
        }
        val set = CandleDataSet(entries, "K线").apply {
            decreasingColor = Color.rgb(38, 166, 91)
            increasingColor = Color.rgb(219, 50, 50)
            shadowColor = Color.DKGRAY
            shadowWidth = 0.7f
            decreasingPaintStyle = Paint.Style.FILL
            increasingPaintStyle = Paint.Style.FILL
            barSpace = 0.3f
            setDrawValues(false)
        }

        b.candleChart.apply {
            setLayerType(android.view.View.LAYER_TYPE_SOFTWARE, null)
            description.isEnabled = false
            legend.textSize = 12f
            setMaxVisibleValueCount(60)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
                labelCount = 10
                textSize = 10f
                valueFormatter = IndexAxisValueFormatter(data.mapIndexed { i, _ -> "${i + 1}日" })
            }
            axisLeft.apply {
                setDrawGridLines(true)
                gridLineWidth = 0.5f
                gridColor = Color.parseColor("#E0E0E0")
                textSize = 10f
            }
            axisRight.isEnabled = false

            this.data = CandleData(set)
            animateX(800)
        }
    }

    // ═══ 2. 折线 + MA5 ════════════════════════════════════════════════════════

    private fun line(data: List<OHLCEntry>) {
        val closeSet = LineDataSet(
            data.mapIndexed { i, d -> Entry(i.toFloat(), d.close) },
            "收盘价"
        ).apply {
            color = Color.parseColor("#1565C0")
            setCircleColor(Color.parseColor("#1565C0"))
            circleRadius = 3f
            lineWidth = 2f
            setDrawValues(false)
        }

        val ma = ma5(data)
        val maSet = LineDataSet(
            ma.indices.filter { ma[it] > 0f }.map { Entry(it.toFloat(), ma[it]) },
            "MA5"
        ).apply {
            color = Color.parseColor("#E65100")
            setCircleColor(Color.parseColor("#E65100"))
            circleRadius = 2f
            lineWidth = 1.5f
            setDrawValues(false)
            enableDashedLine(8f, 4f, 0f)
        }

        b.lineChart.apply {
            description.isEnabled = false
            legend.textSize = 12f
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f; labelCount = 10; textSize = 10f
            }
            axisLeft.apply {
                setDrawGridLines(true)
                gridLineWidth = 0.5f
                gridColor = Color.parseColor("#E0E0E0")
                textSize = 10f
            }
            axisRight.isEnabled = false
            this.data = LineData(closeSet, maSet)
            animateX(800)
        }
    }

    private fun ma5(data: List<OHLCEntry>): List<Float> {
        return data.indices.map { i ->
            val s = (i - 4).coerceAtLeast(0)
            (s..i).sumOf { data[it].close.toDouble() }.toFloat() / (i - s + 1)
        }
    }

    // ═══ 3. 成交量柱状 ═════════════════════════════════════════════════════════

    private fun bar(data: List<OHLCEntry>) {
        val entries = data.mapIndexed { i, d -> BarEntry(i.toFloat(), d.volume) }
        val colors = data.map { d ->
            if (d.close >= d.open) Color.rgb(38, 166, 91)
            else Color.rgb(219, 50, 50)
        }
        val set = BarDataSet(entries, "成交量").apply {
            this.colors = colors
            setDrawValues(false)
        }

        b.barChart.apply {
            description.isEnabled = false
            legend.textSize = 12f
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f; labelCount = 10; textSize = 10f
            }
            axisLeft.apply {
                setDrawGridLines(true)
                gridLineWidth = 0.5f
                gridColor = Color.parseColor("#E0E0E0")
                textSize = 10f
            }
            axisRight.isEnabled = false
            this.data = BarData(set)
            animateY(600)
        }
    }

    // ═══ 4. 饼图 ═══════════════════════════════════════════════════════════════

    private fun pie() {
        val entries = listOf(
            PieEntry(35f, "科技股"),
            PieEntry(25f, "消费股"),
            PieEntry(18f, "医药股"),
            PieEntry(12f, "金融股"),
            PieEntry(10f, "能源股")
        )
        val set = PieDataSet(entries, "").apply {
            sliceSpace = 3f
            selectionShift = 8f
            this.colors = listOf(
                Color.parseColor("#1565C0"),
                Color.parseColor("#E65100"),
                Color.parseColor("#2E7D32"),
                Color.parseColor("#6A1B9A"),
                Color.parseColor("#EF6C00")
            )
            valueTextSize = 12f
            valueTextColor = Color.WHITE
        }

        b.pieChart.apply {
            description.isEnabled = false
            setUsePercentValues(true)
            isDrawHoleEnabled = true
            holeRadius = 40f
            transparentCircleRadius = 45f
            setHoleColor(Color.WHITE)
            centerText = "持仓分布"
            setCenterTextSize(14f)
            isRotationEnabled = true
            isHighlightPerTapEnabled = true
            legend.apply {
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                orientation = Legend.LegendOrientation.HORIZONTAL
                textSize = 11f
                setDrawInside(false)
            }
            val pd = PieData(set)
            pd.setValueFormatter(PercentFormatter(this))
            this.data = pd
            animateY(800)
        }
    }
}
