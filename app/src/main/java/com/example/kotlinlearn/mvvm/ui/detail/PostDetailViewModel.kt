package com.example.kotlinlearn.mvvm.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinlearn.mvvm.data.model.Post
import com.example.kotlinlearn.mvvm.data.repository.NetworkResult
import com.example.kotlinlearn.mvvm.data.repository.PostRepository
import com.example.kotlinlearn.mvvm.ui.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ## PostDetailViewModel — 文章详情页的 ViewModel
 *
 * 继承 BaseViewModel 复用 isLoading / errorMessage。
 */
class PostDetailViewModel : BaseViewModel() {

    private val repository = PostRepository()

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post

    fun loadPost(postId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            when (val result = repository.getPostById(postId)) {
                is NetworkResult.Success -> {
                    _isLoading.value = false
                    _post.value = result.data
                }
                is NetworkResult.Error -> {
                    _isLoading.value = false
                    _errorMessage.value = result.exception.message ?: "加载失败"
                }
                is NetworkResult.Loading -> {}
            }
        }
    }
}
