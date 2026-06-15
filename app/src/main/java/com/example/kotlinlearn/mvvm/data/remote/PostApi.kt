package com.example.kotlinlearn.mvvm.data.remote

import com.example.kotlinlearn.mvvm.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * ## Retrofit API 接口
 *
 * Retrofit 的核心：定义一个接口，用注解描述 HTTP 请求。
 * 运行时 Retrofit 自动生成实现类（动态代理）。
 *
 * ### 注解说明
 * - `@GET("posts")`         → GET 请求，路径为 /posts
 * - `@GET("posts/{id}")`    → GET 请求，{id} 会被 @Path 参数替换
 * - `@Path("id")`           → 把参数值填入 URL 路径的 {id} 占位符
 *
 * ### 返回类型
 * 返回类型是 `List<Post>`，Retrofit + Gson 自动将 JSON 数组转为 List。
 * Retrofit 内部使用协程支持 `suspend` 函数 — 调用时会自动在 IO 线程执行。
 */
interface PostApi {

    companion object {
        /** API 基础地址 */
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    /**
     * ## 获取文章列表
     *
     * 请求：GET https://jsonplaceholder.typicode.com/posts
     * 返回：100 篇文章的 JSON 数组
     *
     * `suspend` 意味着这个函数是挂起函数 — 调用时不会阻塞线程。
     */
    @GET("posts")
    suspend fun getPosts(): List<Post>

    /**
     * ## 获取单篇文章详情
     *
     * 请求：GET https://jsonplaceholder.typicode.com/posts/{id}
     * 例如：GET /posts/1 → 返回 id=1 的文章
     *
     * @param postId  文章 ID，会被替换到 URL 的 {id} 位置
     */
    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") postId: Int): Post
}
