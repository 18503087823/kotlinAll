package com.example.kotlinlearn

import android.content.Context
import android.content.SharedPreferences

/**
 * ## SharedPreferences 轻量存储工具
 *
 * 用于记录「是否首次启动」、「登录状态」等简单标记值。
 *
 * ### Java 对比
 * Java 写法：`context.getSharedPreferences("app", MODE_PRIVATE);`
 * Kotlin 用 `object` 单例 + 扩展函数封装，外部不需要传 Context 反复获取。
 *
 * ### 存储的两个字段
 * - `isFirstLaunch` → 首次启动时进入引导页，看完后置为 false
 * - `isLoggedIn` → 登录成功后置为 true，退出登录时置为 false
 *
 * @property prefs SharedPreferences 实例（懒加载）
 */
object PreferenceManager {

    private const val PREF_NAME = "kotlin_learn_prefs"
    private const val KEY_FIRST_LAUNCH = "is_first_launch"
    private const val KEY_LOGGED_IN = "is_logged_in"

    /** 懒加载：第一次使用时才初始化 SharedPreferences */
    private var prefs: SharedPreferences? = null

    /** 初始化 — 在 Application 或 SplashActivity 中调用一次即可 */
    fun init(context: Context) {
        prefs = context.applicationContext
            .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // ── 首次启动判断 ─────────────────────────────────────────────────────────

    /**
     * 是否首次启动应用
     *
     * 默认值 `true`（因为 Android 中 `getBoolean` 在 key 不存在时返回默认值）
     * 引导页结束后调用 `setFirstLaunchComplete()` 置为 false
     */
    val isFirstLaunch: Boolean
        get() = prefs?.getBoolean(KEY_FIRST_LAUNCH, true) ?: true

    /** 标记引导已完成 — 下次启动不再显示引导页 */
    fun setFirstLaunchComplete() {
        prefs?.edit()?.putBoolean(KEY_FIRST_LAUNCH, false)?.apply()
    }

    // ── 登录状态 ─────────────────────────────────────────────────────────────

    /** 是否已登录 */
    val isLoggedIn: Boolean
        get() = prefs?.getBoolean(KEY_LOGGED_IN, false) ?: false

    /** 登录成功时调用 */
    fun setLoggedIn() {
        prefs?.edit()?.putBoolean(KEY_LOGGED_IN, true)?.apply()
    }

    /** 退出登录时调用 — 回到登录页 */
    fun setLoggedOut() {
        prefs?.edit()?.putBoolean(KEY_LOGGED_IN, false)?.apply()
    }
}
