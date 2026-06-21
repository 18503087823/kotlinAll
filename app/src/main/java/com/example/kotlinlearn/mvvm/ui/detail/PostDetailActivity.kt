package com.example.kotlinlearn.mvvm.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinlearn.databinding.ActivityPostDetailBinding

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  PostDetailActivity — 文章详情页                                            ║
// ║                                                                             ║
// ║  从 Intent 获取 postId → ViewModel 加载详情 → observe LiveData 更新 UI      ║
// ║                                                                             ║
// ║  与列表页的 MVVM 模式完全一致：                                              ║
// ║  Activity(UI) ← observe ← LiveData ← ViewModel ← Repository ← Retrofit      ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class PostDetailActivity : AppCompatActivity() {

    // ═══════════════════════════════════════════════════════════════════════════
    //  静态常量
    // ═══════════════════════════════════════════════════════════════════════════

    companion object {
        /** Intent 传参 key：文章 ID */
        const val EXTRA_POST_ID = "post_id"
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  实例属性
    // ═══════════════════════════════════════════════════════════════════════════

    private lateinit var binding: ActivityPostDetailBinding
    private val viewModel: PostDetailViewModel by viewModels()

    // ═══════════════════════════════════════════════════════════════════════════
    //  生命周期
    // ═══════════════════════════════════════════════════════════════════════════

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 用 apply 批量配置 Toolbar
        binding.toolbar.setNavigationOnClickListener { finish() }

        // 获取 postId → 发出网络请求（取一次，重试复用）
        val postId = intent.getIntExtra(EXTRA_POST_ID, 1)
        viewModel.loadPost(postId)

        // 开始观察 LiveData
        observeViewModel(postId)
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  LiveData 观察
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * ## 观察 ViewModel 的三路 LiveData
     *
     * 与 PostListActivity 的模式完全一致：
     * post → 更新 UI / isLoading → 加载动画 / errorMessage → 错误提示
     */
    private fun observeViewModel(postId: Int) {
        // ── 文章数据 ──
        viewModel.post.observe(this) { post ->
            binding.scrollContent.visibility = View.VISIBLE
            // with：批量填充文章信息
            with(binding) {
                tvPostTitle.text = post.title
                tvPostBody.text = post.body
                tvPostId.text = "#${post.id}"
                tvUserId.text = "作者 #${post.userId}"
            }
        }

        // ── 加载状态 ──
        viewModel.isLoading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        // ── 错误状态 ──
        viewModel.errorMessage.observe(this) { error ->
            if (error != null) {
                binding.layoutError.visibility = View.VISIBLE
                binding.tvError.text = error
                binding.btnRetry.setOnClickListener { viewModel.loadPost(postId) }
            } else {
                binding.layoutError.visibility = View.GONE
            }
        }
    }
}
