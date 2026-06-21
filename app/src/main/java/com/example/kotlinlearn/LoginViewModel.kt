package com.example.kotlinlearn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinlearn.mvvm.ui.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ## LoginViewModel — 登录页 ViewModel
 *
 * 继承 BaseViewModel 复用 isLoading / errorMessage。
 */
class LoginViewModel : BaseViewModel() {

    private val repository = LoginRepository()

    private val _loginResult = MutableLiveData<LoginResponse?>()
    val loginResult: LiveData<LoginResponse?> get() = _loginResult

    fun login(username: String, password: String) {
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

    fun clearError() {
        _errorMessage.value = null
    }
}
