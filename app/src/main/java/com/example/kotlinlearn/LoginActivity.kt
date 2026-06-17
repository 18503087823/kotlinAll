package com.example.kotlinlearn

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinlearn.databinding.ActivityLoginBinding

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  LoginActivity — 登录页                                                    ║
// ║                                                                             ║
// ║  职责：                                                                     ║
// ║  1. 展示账号密码输入框（密码可切换显示/隐藏）                                   ║
// ║  2. 系统 adjustResize 保证键盘弹出时底部按钮不被遮挡                            ║
// ║  3. 点击登录按钮 → ViewModel.login() → POST 请求                             ║
// ║  4. 成功 → 跳转 MainActivity（主页）                                        ║
// ║  5. 失败 → 展示错误提示                                                      ║
// ║                                                                             ║
// ║  布局结构：                                                                  ║
// ║  ┌─ LinearLayout (root) ─────────────────────────────────────┐             ║
// ║  │  ScrollView (weight=1)                                    │             ║
// ║  │    └─ 图标 + 账号 + 密码输入框                             │             ║
// ║  │  LinearLayout (layoutBottom)                              │             ║
// ║  │    └─ 登录按钮 + ProgressBar + tvError                    │             ║
// ║  └───────────────────────────────────────────────────────────┘             ║
// ║  键盘弹出 → adjustResize 缩小窗口 → 底部区域自动顶到键盘上方                  ║
// ║                                                                             ║
// ║  正确账号密码：mql493536775 / 520xxx314                                      ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
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

    private fun setupListeners() {
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

    private fun observeViewModel() {
        // ── 登录结果：成功 → 跳转主页 ──
        viewModel.loginResult.observe(this) { result ->
            if (result != null && result.success) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        // ── 加载状态 ──
        viewModel.isLoading.observe(this) { loading ->
            binding.btnLogin.isEnabled = !loading
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
