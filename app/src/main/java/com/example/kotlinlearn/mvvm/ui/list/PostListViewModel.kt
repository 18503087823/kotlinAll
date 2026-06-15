package com.example.kotlinlearn.mvvm.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinlearn.mvvm.data.model.Post
import com.example.kotlinlearn.mvvm.data.repository.NetworkResult
import com.example.kotlinlearn.mvvm.data.repository.PostRepository
import kotlinx.coroutines.launch

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  PostListViewModel — 列表页的 ViewModel                                     ║
// ║                                                                             ║
// ║  MVVM 架构中 ViewModel 的职责：                                              ║
// ║  - 持有 UI 状态（LiveData）                                                  ║
// ║  - 调用 Repository 获取数据                                                  ║
// ║  - 在协程中执行异步操作（viewModelScope）                                     ║
// ║  - 不持有 View 引用 → 横竖屏切换时 View 重建但 ViewModel 存活                 ║
// ║                                                                             ║
// ║  数据流：                                                                    ║
// ║  PostListViewModel → (LiveData) → PostListActivity → (observe) → 更新 UI     ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class PostListViewModel : ViewModel() {

    /** 数据仓库 — 负责从网络获取文章数据 */
    private val repository = PostRepository()

    // ── LiveData：UI 观察的数据 ──────────────────────────────────────────────

    /**
     * MutableLiveData = 内部可写（private），外部通过只读 LiveData 暴露。
     * 这是 LiveData 的最佳实践 — 封装可变性。
     */
    private val _posts = MutableLiveData<List<Post>>()
    /** 文章列表 — Activity observe 这个 */
    val posts: LiveData<List<Post>> get() = _posts

    private val _isLoading = MutableLiveData<Boolean>()
    /** 是否正在加载 — 控制 ProgressBar 显隐 */
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    /** 错误信息 — null 表示没有错误 */
    val errorMessage: LiveData<String?> get() = _errorMessage

    // ── 初始化 ───────────────────────────────────────────────────────────────

    /**
     * ## init 块 — ViewModel 创建时自动执行
     *
     * 等价于在 Activity 的 onCreate 中调用 loadPosts()，
     * 但放在 init 中让 ViewModel 自治 — 不需要外部主动触发。
     */
    init {
        loadPosts()
    }

    // ── 公开方法 ─────────────────────────────────────────────────────────────

    /**
     * ## 加载文章列表
     *
     * ### viewModelScope.launch { }
     *
     * `viewModelScope` 是 ViewModel 专属的协程作用域：
     * - 协程在 **ViewModel 被清除时自动取消**（不会泄漏）
     * - 默认在 **Dispatchers.Main** 执行 → 可以直接更新 LiveData
     * - 内部调用 `repository.getPosts()` → Retrofit 自动切到 IO 线程
     *
     * ### 【实战：sealed class 的 when 穷尽检查】
     *
     * `when (result)` 必须覆盖 NetworkResult 的所有子类：
     * - `Loading` → 设置加载状态
     * - `Success` → 更新列表数据
     * - `Error` → 显示错误
     *
     * 如果漏掉任何一个分支 → **编译器警告**！这就是 sealed class 的价值。
     */
    fun loadPosts() {
        // viewModelScope.launch：在 ViewModel 的协程作用域中启动协程
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            // 调用 Repository → 网络请求在 IO 线程执行
            val result = repository.getPosts()

            // 【核心知识点：sealed class 的 when 分支】
            // 由于 NetworkResult 是 sealed class，编译器知道所有子类
            // Loading / Success / Error — 必须全部覆盖
            when (result) {
                is NetworkResult.Loading -> {
                    // 加载中（首次请求前）
                }

                is NetworkResult.Success -> {
                    _isLoading.value = false
                    _posts.value = result.data          // 取出 Success 携带的 List<Post>
                }

                is NetworkResult.Error -> {
                    _isLoading.value = false
                    _errorMessage.value = result.exception.message ?: "未知错误"
                }
            } // ← 如果你漏掉一个分支，编译器会在这里报 warning！
        }
    }
}
