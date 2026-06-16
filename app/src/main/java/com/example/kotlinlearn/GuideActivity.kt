package com.example.kotlinlearn

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlinlearn.databinding.ActivityGuideBinding
import com.example.kotlinlearn.databinding.FragmentGuideBinding

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  GuideActivity — 引导页（首次启动展示）                                     ║
// ║                                                                             ║
// ║  3 个 Fragment 页面，横向滑动切换：                                           ║
// ║  ┌───────────┬────────────┬──────────────┐                                  ║
// ║  │ 页面 1    │  页面 2    │  页面 3      │                                  ║
// ║  │ 📚        │  🔨        │  🚀          │                                  ║
// ║  │ 系统学习   │  实战练习  │  持续更新    │                                  ║
// ║  │ 23个知识点│  MVVM DEMO │  保持进步    │                                  ║
// ║  └───────────┴────────────┴──────────────┘                                  ║
// ║                                                                             ║
// ║  底部：圆点指示器 + 「开始学习」按钮                                          ║
// ║  滑动时圆点同步变化，点击按钮进入登录页                                       ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class GuideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuideBinding

    // ── 3 个引导页数据 ──────────────────────────────────────────────────────

    /** 每个引导页就是一组 {图标, 标题, 描述, 背景色} */
    private data class GuidePage(
        val icon: String,
        val title: String,
        val desc: String,
        val color: String
    )

    private val pages = listOf(
        GuidePage(
            icon = "📚",
            title = "系统学习 Kotlin",
            desc = "23 个知识点页面，从作用域函数到协程、Flow，\n用 Java 对比的方式帮你快速上手 Kotlin",
            color = "#6200EE"
        ),
        GuidePage(
            icon = "🔨",
            title = "实战 MVVM 架构",
            desc = "真实的 Retrofit + API 网络请求，\n完整的 ViewModel + LiveData 架构演示，\n可直接运行的列表页和详情页",
            color = "#FF6F00"
        ),
        GuidePage(
            icon = "🚀",
            title = "持续学习，保持进步",
            desc = "每个知识点都精心编排了代码示例，\n关联知识点互相串联，\n看完就能用，用了就能懂",
            color = "#00838F"
        )
    )

    // ── 生命周期 ────────────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupDots()
        setupButton()
    }

    // ── ViewPager2 ───────────────────────────────────────────────────────────

    /** 配置 ViewPager2：设置适配器 + 页面切换监听（同步圆点） */
    private fun setupViewPager() {
        binding.viewPager.adapter = GuideAdapter(pages)

        // 监听页面切换 → 更新底部圆点指示器
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
            }
        })
    }

    // ── 圆点指示器 ───────────────────────────────────────────────────────────

    /**
     * 动态创建 3 个圆点，放在 dotsLayout 中。
     *
     * 圆点本质上是小 TextView：灰色空心圆 ○ 表示未选中，实心 ● 表示选中
     */
    private fun setupDots() {
        val dots = mutableListOf<TextView>()

        // 用 forEach + apply 创建圆点（演示集合操作的实际应用）
        pages.forEachIndexed { index, _ ->
            dots.add(TextView(this).apply {
                text = "●"
                textSize = 16f
                setTextColor(
                    if (index == 0) Color.parseColor("#6200EE")   // 第一个默认选中
                    else Color.parseColor("#CCCCCC")               // 其余灰色
                )
                // 圆点间距
                setPadding(8, 0, 8, 0)
            })
        }

        // 把所有圆点添加到容器
        dots.forEach { binding.dotsLayout.addView(it) }
    }

    /** 页面切换时更新圆点颜色 */
    private fun updateDots(position: Int) {
        val dotsLayout = binding.dotsLayout
        for (i in 0 until dotsLayout.childCount) {
            val dot = dotsLayout.getChildAt(i) as? TextView ?: continue
            dot.setTextColor(
                if (i == position) Color.parseColor("#6200EE")   // 选中的紫色
                else Color.parseColor("#CCCCCC")                  // 未选中灰色
            )
        }
    }

    // ── 按钮 ─────────────────────────────────────────────────────────────────

    /** 「开始学习」按钮 → 标记引导完成 → 跳转登录页 */
    private fun setupButton() {
        binding.btnStart.setOnClickListener {
            // 标记引导已完成（下次启动不再显示引导页）
            PreferenceManager.setFirstLaunchComplete()
            // 跳转登录页
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  内部适配器：ViewPager2 的页面适配器
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * ## GuideAdapter — 引导页适配器
     *
     * 使用 RecyclerView.Adapter<ViewHolder> 作为 ViewPager2 的适配器。
     * ViewPager2 底层就是 RecyclerView，所以用同样的 Adapter 模式。
     *
     * ### Java 对比
     * 旧的 ViewPager 用 PagerAdapter（Fragment 模式），
     * ViewPager2 用 RecyclerView.Adapter（更简单、更高效）。
     */
    inner class GuideAdapter(
        private val data: List<GuidePage>
    ) : RecyclerView.Adapter<GuideAdapter.VH>() {

        inner class VH(val b: FragmentGuideBinding) : RecyclerView.ViewHolder(b.root) {

            fun bind(page: GuidePage) {
                with(b) {
                    // 设置文字
                    tvGuideIcon.text = page.icon
                    tvGuideTitle.text = page.title
                    tvGuideDesc.text = page.desc

                    // 动态设置圆形图标背景色
                    val circle = resources.getDrawable(
                        R.drawable.bg_circle, theme
                    ).mutate()
                    circle.setTint(Color.parseColor(page.color))
                    tvGuideIcon.background = circle
                }
            }
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): VH {
            val binding = FragmentGuideBinding.inflate(
                layoutInflater, parent, false
            )
            return VH(binding)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind(data[position])
        }

        override fun getItemCount(): Int = data.size
    }
}
