package com.example.kotlinlearn.mvvm.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinlearn.mvvm.data.model.Post
import com.example.kotlinlearn.mvvm.data.repository.NetworkResult
import com.example.kotlinlearn.mvvm.data.repository.PostRepository
import kotlinx.coroutines.launch

/**
 * ## PostDetailViewModel — 文章详情页的 ViewModel
 *
 * 与 PostListViewModel 模式相同：Repository → LiveData → UI observe。
 * 这里展示了 ViewModel 如何携带参数（postId）：
 * 通过 loadPost(id) 方法传入，而非构造函数（ViewModel 由框架创建，不能自定义构造）。
 */
class PostDetailViewModel : ViewModel() {

    private val repository = PostRepository()

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    /**
     * ## 根据 ID 加载文章详情
     *
     * 不放在 init 中，因为需要外部传入 postId。
     * Activity 在 onCreate 时调用此方法。
     *
     * @param postId  文章 ID
     */
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
                is NetworkResult.Loading -> { /* 不做特殊处理 */ }
            }
        }
    }
}
