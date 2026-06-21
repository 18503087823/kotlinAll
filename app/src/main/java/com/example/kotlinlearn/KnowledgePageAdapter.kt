package com.example.kotlinlearn

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinlearn.databinding.ItemScopeFunctionBinding

/**
 * ## KnowledgePageAdapter — 知识点卡片列表适配器
 *
 * 继承 ListAdapter + DiffUtil → 自动计算新旧列表差异，高效局部刷新。
 */
class KnowledgePageAdapter(
    private val onItemClick: (KnowledgePage) -> Unit
) : ListAdapter<KnowledgePage, KnowledgePageAdapter.VH>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<KnowledgePage>() {
            override fun areItemsTheSame(old: KnowledgePage, new: KnowledgePage) = old.id == new.id
            override fun areContentsTheSame(old: KnowledgePage, new: KnowledgePage) = old == new
        }
    }

    private val tagBgMap = mapOf(
        "作用域函数" to R.drawable.bg_tag_purple, "Object" to R.drawable.bg_tag_blue,
        "空安全" to R.drawable.bg_tag_orange, "类型别名" to R.drawable.bg_tag_green,
        "协程" to R.drawable.bg_tag_amber, "修饰符" to R.drawable.bg_tag_teal,
        "泛型" to R.drawable.bg_tag_deeppurple, "循环" to R.drawable.bg_tag_indigo,
        "密封类" to R.drawable.bg_tag_deeppurple, "Demo" to R.drawable.bg_tag_red,
        "内联函数" to R.drawable.bg_tag_red, "操作符" to R.drawable.bg_tag_amber,
        "扩展" to R.drawable.bg_tag_green, "高阶函数" to R.drawable.bg_tag_blue,
        "集合" to R.drawable.bg_tag_teal, "分支" to R.drawable.bg_tag_orange,
        "委托" to R.drawable.bg_tag_deeppurple, "Flow" to R.drawable.bg_tag_blue
    )

    private val tagTextColors = mapOf(
        "作用域函数" to "#7B1FA2", "Object" to "#1565C0", "空安全" to "#D84315",
        "类型别名" to "#2E7D32", "协程" to "#FF6F00", "修饰符" to "#00838F",
        "泛型" to "#4527A0", "循环" to "#5C6BC0", "密封类" to "#6A1B9A",
        "Demo" to "#BF360C", "内联函数" to "#C62828", "操作符" to "#EF6C00",
        "扩展" to "#2E7D32", "高阶函数" to "#1565C0", "集合" to "#00838F",
        "分支" to "#4E342E", "委托" to "#4A148C", "Flow" to "#0D47A1"
    )

    inner class VH(val b: ItemScopeFunctionBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(page: KnowledgePage) = with(b) {
            tvName.text = page.title
            tvBrief.text = page.briefDesc
            tvCategory.apply {
                text = page.category
                background = ResourcesCompat.getDrawable(resources, tagBgMap[page.category] ?: R.drawable.bg_tag_purple, null)
                setTextColor(Color.parseColor(tagTextColors[page.category] ?: "#7B1FA2"))
            }
            tvIcon.apply {
                text = page.iconText
                val circle = ResourcesCompat.getDrawable(resources, R.drawable.bg_circle, null)!!.mutate()
                circle.setTint(Color.parseColor(page.iconColor))
                background = circle
            }
            root.setOnClickListener { onItemClick(page) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemScopeFunctionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, pos: Int) = holder.bind(getItem(pos))
}
