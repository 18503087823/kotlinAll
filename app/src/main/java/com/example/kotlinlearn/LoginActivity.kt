package com.example.kotlinlearn

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.kotlinlearn.databinding.ActivityLoginBinding

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  LoginActivity — 登录页                                                    ║
// ║                                                                             ║
// ║  职责：                                                                     ║
// ║  1. 展示账号密码输入框                                                       ║
// ║  2. 点击登录按钮 → ViewModel.login() → POST 请求                             ║
// ║  3. 成功 → 跳转 MainActivity（主页）                                        ║
// ║  4. 失败 → 展示错误提示                                                      ║
// ║                                                                             ║
// ║  数据流：                                                                    ║
// ║  LoginActivity ─→ LoginViewModel.login(u, p) ─→ LoginRepository.login(u, p) ║
// ║       ↑                                              │                    ║
// ║       └── observe LiveData ←─────────────────────────┘                    ║
// ║                                                                             ║
// ║  正确账号密码：mql493536775 / 520xxx314                                      ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class LoginActivity : AppCompatActivity() {

    /** ViewBinding — 布局中所有控件的类型安全引用 */
    private lateinit var binding: ActivityLoginBinding

    /**
     * ## viewModels() — Activity KTX 扩展
     *
     * 等价于 Java 的 `new ViewModelProvider(this).get(LoginViewModel.class)`
     * Kotlin 用 `by viewModels()` 一行搞定。
     *
     * 委托的原理 → 见知识点页面 [委托 by](app:delegation)
     */
    private val viewModel: LoginViewModel by viewModels()

    // ── 生命周期 ────────────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeViewModel()
    }

    // ── 事件监听 ─────────────────────────────────────────────────────────────

    /** 设置按钮点击和输入框焦点事件 */
    private fun setupListeners() {
        // 登录按钮点击 → 发起 POST 请求
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.login(username, password)
        }

        // 输入框获得焦点时清除之前的错误提示
        binding.etUsername.setOnFocusChangeListener { _, _ -> viewModel.clearError() }
        binding.etPassword.setOnFocusChangeListener { _, _ -> viewModel.clearError() }
    }

    // ── LiveData 观察 ────────────────────────────────────────────────────────

    /**
     * ## observe ViewModel 的三个 LiveData
     *
     * ### 为什么 LiveData 是 lifecycle-aware？
     * `observe(lifecycleOwner, Observer { ... })`
     * - Activity 在后台时自动暂停更新 UI
     * - Activity 销毁时自动取消观察 → 没有内存泄漏
     *
     * ### 三个信号
     * 1. `loginResult` — 非空且 success=true 时跳转主页
     * 2. `isLoading` — 控制按钮 loading 动画
     * 3. `errorMessage` — 非空时展示红色错误提示
     */
    private fun observeViewModel() {
        // ── 登录结果：成功 → 跳转主页 ──
        viewModel.loginResult.observe(this) { result ->
            if (result != null && result.success) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()  // 关掉登录页，防止按返回键回退到登录
            }
        }

        // ── 加载状态：按钮显示 loading ──
        viewModel.isLoading.observe(this) { loading ->
            binding.btnLogin.isEnabled = !loading        // loading 时禁用按钮
            binding.btnLogin.text = if (loading) "登录中..." else "登  录"
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        // ── 错误消息 ──
        viewModel.errorMessage.observe(this) { error ->
            binding.tvError.apply {
                text = error ?: ""
                visibility = if (error != null) View.VISIBLE else View.GONE
            }
        }
    }
}
