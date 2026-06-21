package com.example.kotlinlearn

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.location.AMapLocationClient
import com.amap.api.maps.MapsInitializer
import com.example.kotlinlearn.databinding.ActivitySplashBinding
import com.example.kotlinlearn.ui.profile.UmengShareConfig
import com.example.kotlinlearn.ui.profile.UmengShareHelper
import com.umeng.commonsdk.UMConfigure
import kotlinx.coroutines.*

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  SplashActivity — 启动页                                                   ║
// ║                                                                             ║
// ║  职责：                                                                     ║
// ║  1. 展示启动动画（图标淡入放大 + 文字从下往上滑入）                          ║
// ║  2. 判断用户类型 → 首次 / 回访                                              ║
// ║  3. 首次用户 → GuideActivity 引导页                                           ║
// ║  4. 回访用户且未登录 → LoginActivity 登录页                                  ║
// ║  5. 回访用户且已登录 → MainActivity 主页                                     ║
// ║                                                                             ║
// ║  路由逻辑：                                                                 ║
// ║  ┌─ 首次启动 ──→ GuideActivity ──→ LoginActivity ──→ MainActivity           ║
// ║  │                                                                          ║
// ║  └─ 非首次 ──→ isLoggedIn?                                                  ║
// ║       ├─ true  ──→ MainActivity（直接进主页）                               ║
// ║       └─ false ──→ LoginActivity（先登录）                                  ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 初始化 SharedPreferences
        PreferenceManager.init(this)

        // 高德地图 SDK 隐私合规（v11 强制，必须在任何地图/定位 API 前调用）
        MapsInitializer.updatePrivacyShow(this, true, true)
        MapsInitializer.updatePrivacyAgree(this, true)
        AMapLocationClient.updatePrivacyShow(this, true, true)
        AMapLocationClient.updatePrivacyAgree(this, true)

        // 初始化友盟分享 SDK
        if (UmengShareConfig.isConfigured) {
            UMConfigure.init(
                this,
                UmengShareConfig.UMENG_APP_KEY,
                "umeng",
                UMConfigure.DEVICE_TYPE_PHONE,
                ""
            )
            UmengShareHelper.init(this)
        }

        // ── 第 1 步：播放启动动画 ──
        playSplashAnimation()

        // ── 第 2 步：2 秒后根据状态路由 ──
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000) // 等待动画完成
            routeToNextPage()
        }
    }

    /**
     * ## 播放启动动画
     *
     * 两个动画同时播放：
     * - 图标：淡入 + 从 0.6 倍放大到 1.0 倍（anim_splash_fade_in）
     * - 标语：从下往上滑入（anim_splash_text_up）
     *
     * ### Java 对比
     * Java: `AnimationUtils.loadAnimation(this, R.anim.xxx)`
     * Kotlin: 语法相同，`apply` 让配置集中
     */
    private fun playSplashAnimation() {
        with(binding) {
            // 图标：淡入放大
            tvIcon.startAnimation(
                AnimationUtils.loadAnimation(this@SplashActivity, R.anim.anim_splash_fade_in)
            )
            // 标语文字：从下往上滑入
            tvSlogan.startAnimation(
                AnimationUtils.loadAnimation(this@SplashActivity, R.anim.anim_splash_text_up)
            )
        }
    }

    /**
     * ## 路由到下一个页面
     *
     * ### 判断逻辑
     * 1. `isFirstLaunch == true` → 引导页（首次启动）
     * 2. `isLoggedIn == true` → 主页（回访已登录）
     * 3. 否则 → 登录页（回访未登录）
     *
     * `finish()` 关掉 SplashActivity，防止按返回键回到启动页
     */
    private fun routeToNextPage() {
        val targetActivity = when {
            PreferenceManager.isFirstLaunch -> GuideActivity::class.java
            PreferenceManager.isLoggedIn    -> MainActivity::class.java
            else                            -> LoginActivity::class.java
        }

        startActivity(Intent(this, targetActivity))
        finish()
    }
}
