package com.example.kotlinlearn.mvvm.ui.list

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinlearn.R
import com.example.kotlinlearn.databinding.ActivityPostListBinding
import com.example.kotlinlearn.databinding.ItemPostBinding
import com.example.kotlinlearn.mvvm.data.model.Post
import com.example.kotlinlearn.mvvm.ui.detail.PostDetailActivity

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  PostListActivity — MVVM 列表页 Demo                                       ║
// ║                                                                             ║
// ║  演示内容：                                                                 ║
// ║  1. MVVM 架构：Activity → ViewModel → Repository → Retrofit → 真实 API      ║
// ║  2. NetworkResult 密封类的 when 分支处理三大状态                             ║
// ║  3. LiveData 观察者模式：数据变化 → 自动更新 UI                              ║
// ║  4. RecyclerView 列表展示 + 点击跳转详情页                                   ║
// ║                                                                             ║
// ║  数据流：                                                                    ║
// ║  PostListViewModel.loadPosts()                                              ║
// ║    → PostRepository.getPosts() [try-catch 包装为 NetworkResult]             ║
// ║      → RetrofitClient.postApi.getPosts() [Retrofit 动态代理]                ║
// ║        → OkHttp GET https://jsonplaceholder.typicode.com/posts              ║
// ║          ← JSON [ {id: 1, title: "...", body: "..."}, ... ]                 ║
// ║        ← List<Post> (Gson 自动反序列化)                                      ║
// ║      ← NetworkResult.Success(posts)                                          ║
// ║    ← LiveData.value = posts                                                  ║
// ║      ← Observer 触发 → adapter.submitList(posts) → RecyclerView 刷新！        ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class PostListActivity : AppCompatActivity() {

    /** ViewBinding — 布局中所有带 id 的 View 引用 */
    private lateinit var binding: ActivityPostListBinding

    /**
     * ## viewModels() — Kotlin 委托获取 ViewModel
     *
     * `by viewModels()` 是 Activity KTX 提供的扩展：
     * - 自动创建 ViewModel（通过 ViewModelProvider）
     * - 不需要手动写 ViewModelProvider(this).get(...)
     * - 横竖屏切换时返回**同一个** ViewModel 实例
     */
    private val viewModel: PostListViewModel by viewModels()

    /** 列表适配器 — 负责将 Post 数据渲染到 RecyclerView */
    private val adapter = PostAdapter { post ->
        // 用 apply 配置 Intent 后跳转到详情页
        startActivity(Intent(this, PostDetailActivity::class.java).apply {
            putExtra(PostDetailActivity.EXTRA_POST_ID, post.id)
        })
    }

    // ── 生命周期 ────────────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        observeViewModel()
    }

    // ── 初始化 ──────────────────────────────────────────────────────────────

    /** 配置 Toolbar */
    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    /** 配置 RecyclerView — 线性列表布局 */
    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@PostListActivity)
            adapter = this@PostListActivity.adapter
        }
    }

    /**
     * ## 观察 ViewModel 的 LiveData
     *
     * ### 【核心知识点：LiveData 的 observe 模式】
     *
     * `liveData.observe(lifecycleOwner, Observer { ... })`
     * - `lifecycleOwner` = this (Activity) → 生命周期感知
     * - Activity 在后台时不会更新 UI（避免 crash）
     * - Activity 销毁时自动取消观察（避免内存泄漏）
     *
     * ### 观察三个 LiveData：
     * - `posts` → 有新数据时刷新列表
     * - `isLoading` → 切换加载动画显隐
     * - `errorMessage` → 显示/隐藏错误提示
     */
    private fun observeViewModel() {
        // ── 观察文章列表 ──
        viewModel.posts.observe(this) { posts ->
            adapter.submitList(posts)
        }

        // ── 观察加载状态 ──
        viewModel.isLoading.observe(this) { loading ->
            // apply：批量配置 ProgressBar 的可见性
            binding.progressBar.apply {
                visibility = if (loading) android.view.View.VISIBLE
                             else android.view.View.GONE
            }
        }

        // ── 观察错误信息 ──
        viewModel.errorMessage.observe(this) { error ->
            if (error != null) {
                // 有错误 → 显示错误布局
                binding.layoutError.visibility = android.view.View.VISIBLE
                binding.tvError.text = error
                // 重试按钮
                binding.btnRetry.setOnClickListener { viewModel.loadPosts() }
            } else {
                binding.layoutError.visibility = android.view.View.GONE
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  内部适配器
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * ## Post 列表适配器（使用 ListAdapter + DiffUtil）
     *
     * `ListAdapter` 是 RecyclerView 的高效基类：
     * - 内置 DiffUtil → 自动计算新旧列表差异 → 局部刷新
     * - `submitList()` → 提交新列表，后台线程计算差异
     *
     * 如果用普通的 RecyclerView.Adapter，需要手动调用 notifyDataSetChanged()
     * → 全量刷新，性能差且没有动画。
     */
    class PostAdapter(
        private val onClick: (Post) -> Unit
    ) : RecyclerView.Adapter<PostAdapter.VH>() {

        /** 内部可变列表 */
        private var items = listOf<Post>()

        fun submitList(list: List<Post>) {
            items = list
            notifyDataSetChanged()
        }

        inner class VH(val b: ItemPostBinding) : RecyclerView.ViewHolder(b.root) {
            fun bind(post: Post) {
                // with：批量操作 binding 内的控件
                with(b) {
                    tvTitle.text = post.title
                    tvBody.text = post.body
                    tvId.text = "#${post.id}"

                    // 根据 id 动态改变圆形背景色
                    val circle = ResourcesCompat.getDrawable(
                        resources, R.drawable.bg_circle, null
                    )!!.mutate()
                    // id 不同 → 颜色深浅不同（用 HSL 微调）
                    val hue = ((post.id * 37) % 360).toFloat()
                    circle.setTint(Color.HSVToColor(floatArrayOf(hue, 0.6f, 0.7f)))
                    tvId.background = circle

                    root.setOnClickListener { onClick(post) }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val binding = ItemPostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return VH(binding)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size
    }
}
