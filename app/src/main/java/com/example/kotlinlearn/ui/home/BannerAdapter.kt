package com.example.kotlinlearn.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kotlinlearn.databinding.ItemBannerBinding

/**
 * ## BannerAdapter — ViewPager2 轮播图适配器
 *
 * 使用 Coil 加载网络图片（已引入 coil:2.5.0）。
 * Coil 是 Kotlin 协程优先的图片加载库，比 Glide 更轻量。
 */
class BannerAdapter(
    private val images: List<String>,
    private val titles: List<String>
) : RecyclerView.Adapter<BannerAdapter.VH>() {

    inner class VH(val b: ItemBannerBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(pos: Int) {
            with(b) {
                ivBanner.load(images[pos]) {
                    crossfade(400)
                    placeholder(android.R.color.darker_gray)
                }
                tvBannerTitle.text = titles.getOrElse(pos) { "" }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, pos: Int) = holder.bind(pos)
    override fun getItemCount() = images.size
}
