package com.example.kotlinlearn

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.TextView

/**
 * ## DotIndicator — ViewPager2 圆点指示器工具
 *
 * HomeFragment / GuideActivity 共用，消除重复代码。
 *
 * 用法：
 * ```kotlin
 * DotIndicator.setup(dotsLayout, count)       // 创建 count 个圆点
 * DotIndicator.update(dotsLayout, position)    // 页面切换时更新颜色
 * ```
 */
object DotIndicator {

    private const val COLOR_ACTIVE = "#6200EE"
    private const val COLOR_INACTIVE = "#CCCCCC"
    private const val PADDING = 6

    /** 创建圆点并添加到 dotsContainer */
    fun setup(dotsContainer: ViewGroup, count: Int) {
        val ctx = dotsContainer.context
        repeat(count) { i ->
            dotsContainer.addView(createDot(ctx, i == 0))
        }
    }

    /** 更新圆点颜色（position = 当前页面） */
    fun update(dotsContainer: ViewGroup, position: Int) {
        for (i in 0 until dotsContainer.childCount) {
            (dotsContainer.getChildAt(i) as? TextView)?.setTextColor(
                if (i == position) Color.parseColor(COLOR_ACTIVE)
                else Color.parseColor(COLOR_INACTIVE)
            )
        }
    }

    private fun createDot(ctx: Context, isActive: Boolean) = TextView(ctx).apply {
        text = "●"
        textSize = 14f
        setTextColor(Color.parseColor(if (isActive) COLOR_ACTIVE else COLOR_INACTIVE))
        setPadding(PADDING, 0, PADDING, 0)
    }
}
