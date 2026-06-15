package com.example.kotlinlearn

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinlearn.databinding.ActivityMainBinding
import com.example.kotlinlearn.mvvm.ui.list.PostListActivity

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  MainActivity — 应用入口                                                   ║
// ║                                                                             ║
// ║  页面结构（从上到下）：                                                     ║
// ║  1. Toolbar — 标题栏                                                        ║
// ║  2. 作用域函数对比表格（静态参考，table_comparison.xml）                     ║
// ║  3. 快速选择决策树（静态参考）                                               ║
// ║  4. RecyclerView — 全部 8 个知识页面的卡片入口                              ║
// ║     ├── let / run / with / apply / also（作用域函数 × 5）                    ║
// ║     ├── object 关键字（Object × 1）                                          ║
// ║     ├── 空安全操作符（空安全 × 1）                                           ║
// ║     └── typealias & import alias（类型别名 × 1）                             ║
// ║                                                                             ║
// ║  数据源：KnowledgeData.allPages（8 条 KnowledgePage）                        ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class MainActivity : AppCompatActivity() {

    // ── 属性声明 ────────────────────────────────────────────────────────────

    // lateinit：延迟初始化，在 onCreate 中赋值
    // ViewBinding 引用，避免 findViewById
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: KnowledgePageAdapter

    // ── 生命周期 ────────────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ── 第 1 步：加载布局 ──
        // ActivityMainBinding 由 ViewBinding 根据 activity_main.xml 自动生成
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ── 第 2 步：配置 RecyclerView ──
        setupRecyclerView()
    }

    // ── 私有方法 ────────────────────────────────────────────────────────────

    /**
     * 配置 RecyclerView：创建适配器、设置布局管理器。
     *
     * ### 数据流
     * ```
     * KnowledgeData.allPages  →  KnowledgePageAdapter  →  RecyclerView
     *        (8 条数据)               (绑定卡片)             (显示列表)
     * ```
     *
     * ### 【实战：apply 配置 Intent】
     * `Intent(...).apply { putExtra(...) }` 展示了 apply 的经典用法：
     * 创建对象 → 配置属性 → 返回对象本身
     *
     * ### 【实战：apply 配置 RecyclerView】
     * `recyclerView.apply { layoutManager = ...; adapter = ... }`
     * 对已有对象做批量配置，不创建新变量
     */
    private fun setupRecyclerView() {

        // ── 创建适配器 ──
        // KnowledgeData.allPages：8 个知识页面数据
        // 尾随 Lambda：点击回调 → 跳转到详情页
        adapter = KnowledgePageAdapter(
            pages = KnowledgeData.allPages,
            onItemClick = { page ->
                // ── MVVM Demo 特殊处理：打开真实的 API 列表页 ──
                if (page.id == "mvvm-demo") {
                    // 直接打开 MVVM 文章列表页（不做详情页渲染）
                    startActivity(Intent(this, PostListActivity::class.java))
                    return@KnowledgePageAdapter  // 提前结束 Lambda
                }

                // ── 普通知识页面：跳转到 DetailActivity ──
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_PAGE_ID, page.id)
                }
                startActivity(intent)
            }
        )

        // ── 配置 RecyclerView ──
        //
        // 这里用 apply 而不是直接赋值：
        // - layoutManager 和 adapter 是两个独立配置
        // - 用 apply 把它们分组在一起，表示「对 RecyclerView 做配置」
        // - this@MainActivity 显式引用外部类（因为 apply 块内的 this 是 recyclerView）
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }
}
