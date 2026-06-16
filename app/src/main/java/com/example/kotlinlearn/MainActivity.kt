package com.example.kotlinlearn

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinlearn.databinding.ActivityMainBinding
import com.example.kotlinlearn.mvvm.ui.list.PostListActivity

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  MainActivity — 知识点主页（登录后进入）                                     ║
// ║                                                                             ║
// ║  页面结构（从上到下）：                                                     ║
// ║  1. Toolbar — 标题栏 + 右侧退出登录按钮                                      ║
// ║  2. 作用域函数对比表格（静态参考）                                           ║
// ║  3. 快速选择决策树                                                           ║
// ║  4. RecyclerView — 23 个知识卡片入口                                         ║
// ║                                                                             ║
// ║  退出登录：点击 Toolbar 右侧按钮 → 清除登录态 → 回到 LoginActivity            ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: KnowledgePageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 设置 Toolbar（作为 ActionBar）
        setSupportActionBar(binding.toolbar)

        setupRecyclerView()
    }

    // ── Toolbar 菜单 ──────────────────────────────────────────────────────────

    /** 加载右上角退出登录按钮 */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /**
     * ## 处理 Toolbar 菜单点击
     *
     * 退出登录流程：
     * 1. 调用 PreferenceManager.setLoggedOut() 清除登录态
     * 2. 跳转到 LoginActivity
     * 3. finish() 关掉 MainActivity（防止返回键回到主页）
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                PreferenceManager.setLoggedOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // ── RecyclerView ──────────────────────────────────────────────────────────

    private fun setupRecyclerView() {
        adapter = KnowledgePageAdapter(
            pages = KnowledgeData.allPages,
            onItemClick = { page ->
                if (page.id == "mvvm-demo") {
                    startActivity(Intent(this, PostListActivity::class.java))
                    return@KnowledgePageAdapter
                }
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_PAGE_ID, page.id)
                }
                startActivity(intent)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }
}
