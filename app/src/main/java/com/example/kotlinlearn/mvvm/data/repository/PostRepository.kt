package com.example.kotlinlearn.mvvm.data.repository

import com.example.kotlinlearn.mvvm.data.model.Post
import com.example.kotlinlearn.mvvm.data.remote.RetrofitClient

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  密封类 — 封装网络请求的三种状态                                            ║
// ║                                                                             ║
// ║  这是 sealed class 最经典的实际应用：                                        ║
// ║  把 API 返回的「加载中 / 成功 / 失败」三种状态建模为三个子类。                ║
// ║                                                                             ║
// ║  为什么用 sealed class？                                                    ║
// ║  - 编译器知道所有子类 → when 分支穷尽检查                                    ║
// ║  - 每个子类可以携带不同数据：Success 带 List<Post>，Error 带 Exception       ║
// ║  - 比 enum 强大：enum 的每个值都是单例，不能携带不同形状的数据               ║
// ╚══════════════════════════════════════════════════════════════════════════════╝

/**
 * ## 网络请求结果密封类
 *
 * 三种状态 + 各自的附带数据：
 * - `Loading`：正在加载中，无附带数据 → object 单例
 * - `Success`：请求成功，附带数据 T
 * - `Error`：请求失败，附带异常信息
 *
 * @param T  成功时的数据类型（泛型，Post 或 List<Post> 都可以）
 */
sealed class NetworkResult<out T> {
    /** 加载中 — 所有请求共用一个实例（object 单例） */
    object Loading : NetworkResult<Nothing>()

    /** 请求成功 — 携带实际数据 */
    data class Success<out T>(val data: T) : NetworkResult<T>()

    /** 请求失败 — 携带异常信息 */
    data class Error(val exception: Throwable) : NetworkResult<Nothing>()
}

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  PostRepository — 数据仓库层                                                ║
// ║                                                                             ║
// ║  职责：从网络获取数据，将原始 API 响应包装为 NetworkResult。                  ║
// ║  上层（ViewModel）不需要知道数据来自 Retrofit / 数据库 / 缓存。              ║
// ║                                                                             ║
// ║  Repository 模式的好处：                                                     ║
// ║  - 隔离数据源：ViewModel 不直接依赖 Retrofit                                ║
// ║  - 统一错误处理：try-catch 在这里，ViewModel 只关心结果                     ║
// ║  - 可测试：替换 Repository 实现即可测试 ViewModel                           ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class PostRepository {

    /** Retrofit 生成的 API 实现（动态代理） */
    private val api = RetrofitClient.postApi

    /**
     * ## 获取文章列表
     *
     * 流程：
     * 1. 发出 Loading → UI 显示加载动画
     * 2. 调用 API → Retrofit 在 IO 线程执行网络请求
     * 3. 成功 → 发出 Success(data) → UI 展示列表
     * 4. 失败 → 发出 Error(e) → UI 展示错误信息
     *
     * `suspend` 函数 → 必须在协程中调用
     */
    suspend fun getPosts(): NetworkResult<List<Post>> {
        return try {
            val posts = api.getPosts()
            NetworkResult.Success(posts.take(30)) // 只取前 30 条，避免列表过长
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }

    /**
     * ## 获取文章详情
     *
     * @param postId  文章 ID
     */
    suspend fun getPostById(postId: Int): NetworkResult<Post> {
        return try {
            val post = api.getPostById(postId)
            NetworkResult.Success(post)
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }
}
