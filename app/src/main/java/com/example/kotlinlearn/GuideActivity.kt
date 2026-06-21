package com.example.kotlinlearn

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlinlearn.databinding.ActivityGuideBinding
import com.example.kotlinlearn.databinding.FragmentGuideBinding

/**
 * ## GuideActivity — 引导页（首次启动展示）
 *
 * 3 个页面横向滑动 + 底部圆点指示器 + "开始学习"按钮
 */
class GuideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuideBinding

    data class GuidePage(
        val icon: String, val title: String, val desc: String, val color: String
    )

    private val pages = listOf(
        GuidePage("📚", "系统学习 Kotlin",
            "23 个知识点页面，从作用域函数到协程、Flow，\n用 Java 对比的方式帮你快速上手 Kotlin", "#6200EE"),
        GuidePage("🔨", "实战 MVVM 架构",
            "真实的 Retrofit + API 网络请求，\n完整的 ViewModel + LiveData 架构演示，\n可直接运行的列表页和详情页", "#FF6F00"),
        GuidePage("🚀", "持续学习，保持进步",
            "每个知识点都精心编排了代码示例，\n关联知识点互相串联，\n看完就能用，用了就能懂", "#00838F")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        DotIndicator.setup(binding.dotsLayout, pages.size)
        setupButton()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = GuideAdapter(pages)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) = DotIndicator.update(binding.dotsLayout, position)
        })
    }

    private fun setupButton() {
        binding.btnStart.setOnClickListener {
            PreferenceManager.setFirstLaunchComplete()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    // ── GuideAdapter ──────────────────────────────────────────────────────────

    inner class GuideAdapter(
        private val data: List<GuidePage>
    ) : RecyclerView.Adapter<GuideAdapter.VH>() {

        inner class VH(val b: FragmentGuideBinding) : RecyclerView.ViewHolder(b.root) {
            fun bind(page: GuidePage) = with(b) {
                tvGuideIcon.text = page.icon
                tvGuideTitle.text = page.title
                tvGuideDesc.text = page.desc
                val circle = resources.getDrawable(R.drawable.bg_circle, theme).mutate()
                circle.setTint(Color.parseColor(page.color))
                tvGuideIcon.background = circle
            }
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): VH {
            val binding = FragmentGuideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return VH(binding)
        }

        override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(data[position])
        override fun getItemCount(): Int = data.size
    }
}
