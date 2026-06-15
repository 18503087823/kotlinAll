package com.example.kotlinlearn

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinlearn.databinding.ItemScopeFunctionBinding

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  KnowledgePageAdapter — 主页列表的通用适配器                                ║
// ║                                                                             ║
// ║  数据源：KnowledgeData.allPages（8 个 KnowledgePage）                        ║
// ║  每个列表项 = 一个知识页面的入口卡片                                         ║
// ║                                                                             ║
// ║  卡片按分类着色：                                                            ║
// ║  - 作用域函数 → 各自主题色（紫/蓝/绿/橙/玫红）                               ║
// ║  - Object     → 蓝色                                                         ║
// ║  - 空安全      → 深橙                                                         ║
// ║  - 类型别名    → 绿色                                                         ║
// ║  - 协程        → 琥珀色                                                       ║
// ║  - 修饰符      → 青色                                                         ║
// ║  - 泛型        → 深紫                                                         ║
// ║  - 循环        → 靛蓝                                                         ║
// ║  - 密封类      → 紫色                                                         ║
// ║  - Demo        → 深红                                                         ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class KnowledgePageAdapter(
    private val pages: List<KnowledgePage>,
    private val onItemClick: (KnowledgePage) -> Unit
) : RecyclerView.Adapter<KnowledgePageAdapter.VH>() {

    // ═════════════════════════════════════════════════════════════════════
    //  分类 → 标签背景 drawable 映射
    //  （作用域函数各函数颜色不同，在 bind 中单独处理）
    // ═════════════════════════════════════════════════════════════════════

    /** 分类 → tag 标签背景 drawable */
    private val tagBgMap = mapOf(
        "作用域函数" to R.drawable.bg_tag_purple,
        "Object"   to R.drawable.bg_tag_blue,
        "空安全"    to R.drawable.bg_tag_orange,
        "类型别名"  to R.drawable.bg_tag_green,
        "协程"      to R.drawable.bg_tag_amber,
        "修饰符"    to R.drawable.bg_tag_teal,
        "泛型"      to R.drawable.bg_tag_deeppurple,
        "循环"      to R.drawable.bg_tag_indigo,
        "密封类"    to R.drawable.bg_tag_deeppurple,
        "Demo"      to R.drawable.bg_tag_red,
        "内联函数"  to R.drawable.bg_tag_red,
        "操作符"    to R.drawable.bg_tag_amber,
        "扩展"      to R.drawable.bg_tag_green,
        "高阶函数"  to R.drawable.bg_tag_blue,
        "集合"      to R.drawable.bg_tag_teal,
        "分支"      to R.drawable.bg_tag_orange,
        "委托"      to R.drawable.bg_tag_deeppurple,
        "Flow"      to R.drawable.bg_tag_blue
    )

    /** 分类 → tag 标签文字颜色 */
    private val tagTextColors = mapOf(
        "作用域函数" to "#7B1FA2",
        "Object"   to "#1565C0",
        "空安全"    to "#D84315",
        "类型别名"  to "#2E7D32",
        "协程"      to "#FF6F00",
        "修饰符"    to "#00838F",
        "泛型"      to "#4527A0",
        "循环"      to "#5C6BC0",
        "密封类"    to "#6A1B9A",
        "Demo"      to "#BF360C",
        "内联函数"  to "#C62828",
        "操作符"    to "#EF6C00",
        "扩展"      to "#2E7D32",
        "高阶函数"  to "#1565C0",
        "集合"      to "#00838F",
        "分支"      to "#4E342E",
        "委托"      to "#4A148C",
        "Flow"      to "#0D47A1"
    )

    // ═════════════════════════════════════════════════════════════════════
    //  ViewHolder
    // ═════════════════════════════════════════════════════════════════════

    inner class VH(val b: ItemScopeFunctionBinding) : RecyclerView.ViewHolder(b.root) {

        fun bind(page: KnowledgePage) {
            // 【实战：with 批量操作 binding】
            with(b) {
                // ── 设置文字 ──
                tvName.text = page.title
                tvBrief.text = page.briefDesc

                // ── 设置分类标签 ──
                // 根据分类选择不同的背景色和文字色
                tvCategory.apply {
                    text = page.category
                    // 动态设置标签背景
                    val bgRes = tagBgMap[page.category] ?: R.drawable.bg_tag_purple
                    background = ResourcesCompat.getDrawable(resources, bgRes, null)
                    // 动态设置标签文字颜色
                    setTextColor(Color.parseColor(tagTextColors[page.category] ?: "#7B1FA2"))
                }

                // ── 设置圆形图标 ──
                tvIcon.apply {
                    text = page.iconText
                    // 取一个 drawable 副本并着色
                    val circle = ResourcesCompat.getDrawable(
                        resources, R.drawable.bg_circle, null
                    )!!.mutate()
                    circle.setTint(Color.parseColor(page.iconColor))
                    background = circle
                }

                // ── 点击事件 ──
                root.setOnClickListener { onItemClick(page) }
            }
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    //  RecyclerView.Adapter 三方法
    // ═════════════════════════════════════════════════════════════════════

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemScopeFunctionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        // 【实战：also 附加日志】
        return VH(binding).also {
            android.util.Log.d("Adapter", "创建 VH: ${it.hashCode()}")
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(pages[position])
    }

    override fun getItemCount(): Int = pages.size
}
