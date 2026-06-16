package com.example.kotlinlearn.ui.demo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlinlearn.databinding.FragmentDemoBinding
import com.example.kotlinlearn.mvvm.ui.list.PostListActivity
import com.example.kotlinlearn.ui.weather.WeatherDetailActivity

/**
 * ## DemoFragment — 实战练习 (Tab 3)
 *
 * 两个入口按钮：
 * 1. 文章列表 → MVVM Demo（真实 API）
 * 2. 天气详情 → 跳转到今日天气详情页
 */
class DemoFragment : Fragment() {

    private var _b: FragmentDemoBinding? = null
    private val b get() = _b!!

    override fun onCreateView(inflater: LayoutInflater, c: ViewGroup?, state: Bundle?): View {
        _b = FragmentDemoBinding.inflate(inflater, c, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.btnPostList.setOnClickListener {
            startActivity(Intent(requireContext(), PostListActivity::class.java))
        }
        b.btnWeatherDetail.setOnClickListener {
            startActivity(Intent(requireContext(), WeatherDetailActivity::class.java).apply {
                putExtra(WeatherDetailActivity.EXTRA_DATE, "today")
            })
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
