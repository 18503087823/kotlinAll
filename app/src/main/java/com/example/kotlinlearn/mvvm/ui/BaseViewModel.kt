package com.example.kotlinlearn.mvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ## BaseViewModel — 复用的 MVVM 基类
 *
 * 提供所有页面通用的 LiveData：
 * - isLoading → 控制 ProgressBar 显隐
 * - errorMessage → 错误提示（null = 无错误）
 *
 * 子类直接写 `_isLoading.value = ...` / `_errorMessage.value = ...`
 */
open class BaseViewModel : ViewModel() {

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    protected val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage
}
