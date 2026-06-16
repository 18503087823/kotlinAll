package com.example.kotlinlearn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kotlinlearn.databinding.ActivityMainBinding
import com.example.kotlinlearn.ui.home.HomeFragment
import com.example.kotlinlearn.ui.knowledge.KnowledgeFragment
import com.example.kotlinlearn.ui.demo.DemoFragment
import com.example.kotlinlearn.ui.profile.ProfileFragment

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  MainActivity — 4 Tab 主页（登录后进入）                                     ║
// ║                                                                             ║
// ║  ┌────────┬────────┬────────┬────────┐                                     ║
// ║  │ 首页   │ 学习   │ 实战   │ 我的   │                                     ║
// ║  │ Home   │Knowledge│ Demo  │Profile │                                     ║
// ║  ├────────┼────────┼────────┼────────┤                                     ║
// ║  │轮播+天气│ 知识卡片│ MVVM  │个人中心│                                     ║
// ║  └────────┴────────┴────────┴────────┘                                     ║
// ║                                                                             ║
// ║  使用 Fragment 管理 4 个 Tab，hide/show 切换                                   ║
// ║  而非 replace，保证切换时 Fragment 状态不丢失                                  ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding

    private val homeFragment by lazy { HomeFragment() }
    private val knowledgeFragment by lazy { KnowledgeFragment() }
    private val demoFragment by lazy { DemoFragment() }
    private val profileFragment by lazy { ProfileFragment() }

    /** 当前显示的 Fragment */
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)

        // 首次进入 → 默认显示「首页」
        if (savedInstanceState == null) {
            switchTab(homeFragment)
        }

        // 底部导航点击切换
        b.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home      -> switchTab(homeFragment)
                R.id.nav_knowledge -> switchTab(knowledgeFragment)
                R.id.nav_demo      -> switchTab(demoFragment)
                R.id.nav_profile   -> switchTab(profileFragment)
            }
            true
        }
    }

    /**
     * ## Fragment 切换（hide/show 策略）
     *
     * 用 hide/show 而非 replace：
     * - hide：Fragment 实例保留，onSaveInstanceState 保留
     * - replace：Fragment 被销毁重建，状态丢失，性能差
     *
     * 首次添加时用 `add + hide + show`，之后用 `hide + show`
     */
    private fun switchTab(target: Fragment) {
        val current = currentFragment

        supportFragmentManager.beginTransaction().apply {
            // 如果之前有 Fragment 且还在，先隐藏它
            if (current != null && current.isAdded) {
                hide(current)
            }

            // 目标 Fragment 是否已添加过？
            if (target.isAdded) {
                show(target)
            } else {
                add(R.id.fragmentContainer, target)
            }
        }.commit()

        currentFragment = target
    }

    /** 处理系统返回键 → 如果不在首页 Tab，切回首页 */
    @Deprecated("Deprecated in Java") // suppress warning
    override fun onBackPressed() {
        if (currentFragment != homeFragment) {
            b.bottomNav.selectedItemId = R.id.nav_home
        } else {
            super.onBackPressed()
        }
    }
}
