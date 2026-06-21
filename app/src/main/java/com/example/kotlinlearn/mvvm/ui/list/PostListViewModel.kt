package com.example.kotlinlearn.mvvm.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinlearn.mvvm.data.model.Post
import com.example.kotlinlearn.mvvm.data.repository.NetworkResult
import com.example.kotlinlearn.mvvm.data.repository.PostRepository
import com.example.kotlinlearn.mvvm.ui.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ## PostListViewModel — 列表页的 ViewModel
 *
 * 继承 BaseViewModel 复用 isLoading / errorMessage。
 */
class PostListViewModel : BaseViewModel() {

    private val repository = PostRepository()

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    init { loadPosts() }

    fun loadPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            when (val result = repository.getPosts()) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    _isLoading.value = false
                    _posts.value = result.data
                }
                is NetworkResult.Error -> {
                    _isLoading.value = false
                    _errorMessage.value = result.exception.message ?: "未知错误"
                }
            }
        }
    }
}
