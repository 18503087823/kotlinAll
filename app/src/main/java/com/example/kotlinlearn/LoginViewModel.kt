package com.example.kotlinlearn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * ## LoginViewModel — 登录页的 ViewModel
 *
 * ### 数据流
 * ```
 * LoginActivity(输入框)
 *   → LoginViewModel.login(username, password)
 *     → LoginRepository.login(username, password)
 *       → Retrofit POST /api/login      [优先，走网络]
 *       → 本地校验 mql493536775/520xxx314  [兜底，网络不通时]
 *     ← LoginResponse(success, message)
 *   ← LiveData 更新 → LoginActivity 跳转主页或显示错误
 * ```
 *
 * ### 三个 LiveData
 * - `loginResult` → 登录结果（成功则携带 token）
 * - `isLoading` → 按钮显示加载动画，防止重复点击
 * - `errorMessage` → 错误提示文字
 */
class LoginViewModel : ViewModel() {

    private val repository = LoginRepository()

    /** 登录结果 — success=true 时说明登录成功 */
    private val _loginResult = MutableLiveData<LoginResponse?>()
    val loginResult: LiveData<LoginResponse?> get() = _loginResult

    /** 加载状态 — 登录按钮 loading */
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    /** 错误消息 — 登录失败时展示 */
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    /**
     * ## 执行登录
     *
     * 在协程中调用 Repository，成功后自动持久化登录状态到 SharedPreferences。
     *
     * @param username  账号
     * @param password  密码
     */
    fun login(username: String, password: String) {
        // 输入校验：空值直接拦截
        if (username.isBlank() || password.isBlank()) {
            _errorMessage.value = "账号和密码不能为空"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val result = repository.login(username, password)

                if (result.success) {
                    // 登录成功 → 持久化状态
                    PreferenceManager.setLoggedIn()
                    _loginResult.value = result
                } else {
                    _errorMessage.value = result.message
                }
            } catch (e: Exception) {
                _errorMessage.value = "网络异常：${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /** 清除错误消息（输入框获得焦点时调用） */
    fun clearError() {
        _errorMessage.value = null
    }
}
