package com.example.kotlinlearn.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlinlearn.DotIndicator
import com.example.kotlinlearn.databinding.FragmentHomeBinding
import com.example.kotlinlearn.databinding.ItemWeatherBinding
import com.example.kotlinlearn.ui.weather.DayWeather
import com.example.kotlinlearn.ui.weather.WeatherDetailActivity
import com.example.kotlinlearn.ui.weather.weatherDesc

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  HomeFragment — 首页 (Tab 1)                                              ║
// ║  结构：Banner 轮播图 → 跑马灯 → 16 天天气列表                                ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class HomeFragment : Fragment() {

    private var _b: FragmentHomeBinding? = null
    private val b get() = _b!!

    private val vm: HomeViewModel by viewModels()

    private val weatherAdapter by lazy { WeatherAdapter { day -> onWeatherClick(day) } }

    override fun onCreateView(inflater: LayoutInflater, c: ViewGroup?, state: Bundle?): View {
        _b = FragmentHomeBinding.inflate(inflater, c, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBanner()
        setupMarquee()
        setupWeatherList()
        observeViewModel()
    }

    // ── Banner 轮播 ───────────────────────────────────────────────────────────

    private fun setupBanner() {
        val adapter = BannerAdapter(vm.bannerUrls, vm.bannerTitles)
        b.viewPagerBanner.adapter = adapter
        DotIndicator.setup(b.dotsBanner, adapter.itemCount)
        b.viewPagerBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(pos: Int) = DotIndicator.update(b.dotsBanner, pos)
        })
        vm.startBannerAutoScroll(adapter.itemCount)
        vm.currentBannerPos.observe(viewLifecycleOwner) { pos ->
            b.viewPagerBanner.setCurrentItem(pos, true)
        }
    }

    // ── 跑马灯 ────────────────────────────────────────────────────────────────

    private fun setupMarquee() {
        b.tvMarquee.isSelected = true
    }

    // ── 天气列表 ──────────────────────────────────────────────────────────────

    private fun setupWeatherList() {
        b.recyclerWeather.layoutManager = LinearLayoutManager(requireContext())
        b.recyclerWeather.adapter = weatherAdapter
    }

    private fun observeViewModel() {
        vm.isLoading.observe(viewLifecycleOwner) { loading ->
            b.progressWeather.visibility = if (loading) View.VISIBLE else View.GONE
        }
        vm.errorMessage.observe(viewLifecycleOwner) { err ->
            b.tvError.visibility = if (err != null) View.VISIBLE else View.GONE
            if (err != null) b.tvError.text = err
        }
        vm.weatherDays.observe(viewLifecycleOwner) { days ->
            weatherAdapter.submitList(days)
        }
    }

    private fun onWeatherClick(day: DayWeather) {
        startActivity(Intent(requireContext(), WeatherDetailActivity::class.java).apply {
            putExtra(WeatherDetailActivity.EXTRA_DATE, day.date)
            putExtra(WeatherDetailActivity.EXTRA_MAX, day.maxTemp)
            putExtra(WeatherDetailActivity.EXTRA_MIN, day.minTemp)
            putExtra(WeatherDetailActivity.EXTRA_CODE, day.weatherCode)
            putExtra(WeatherDetailActivity.EXTRA_WIND, day.windSpeed)
            putExtra(WeatherDetailActivity.EXTRA_PRECIP, day.precip)
        })
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }

    // ── WeatherAdapter — ListAdapter + DiffUtil ─────────────────────────────────

    class WeatherAdapter(private val onClick: (DayWeather) -> Unit)
        : ListAdapter<DayWeather, WeatherAdapter.VH>(DiffCallback) {

        companion object {
            val DiffCallback = object : DiffUtil.ItemCallback<DayWeather>() {
                override fun areItemsTheSame(old: DayWeather, new: DayWeather) = old.date == new.date
                override fun areContentsTheSame(old: DayWeather, new: DayWeather) = old == new
            }
        }

        inner class VH(val b: ItemWeatherBinding) : RecyclerView.ViewHolder(b.root) {
            fun bind(d: DayWeather) = with(b) {
                val (emoji, desc) = weatherDesc(d.weatherCode)
                tvWeatherIcon.text = emoji; tvDesc.text = desc
                tvMaxTemp.text = "${d.maxTemp.toInt()}°"
                tvMinTemp.text = "${d.minTemp.toInt()}°"
                tvDate.text = d.date.takeLast(5); tvWind.text = "${d.windSpeed.toInt()} km/h"
                root.setOnClickListener { onClick(d) }
            }
        }

        override fun onCreateViewHolder(p: ViewGroup, v: Int) = VH(
            ItemWeatherBinding.inflate(LayoutInflater.from(p.context), p, false)
        )
        override fun onBindViewHolder(h: VH, pos: Int) = h.bind(getItem(pos))
    }
}
