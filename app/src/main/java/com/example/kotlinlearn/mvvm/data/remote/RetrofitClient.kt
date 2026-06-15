package com.example.kotlinlearn.mvvm.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * ## Retrofit 单例客户端
 *
 * 使用 `object` 关键字声明单例 —— 全局只有一个 Retrofit 实例。
 * 避免重复创建，节省资源。
 *
 * ### 创建流程
 * 1. 配置 OkHttpClient（底层 HTTP 引擎）→ 添加日志拦截器
 * 2. 构建 Retrofit 实例 → 设置 baseUrl + Gson 转换器
 * 3. 调用 create() → 生成 PostApi 的动态代理实现
 *
 * ### OkHttp 日志拦截器
 * 在 Logcat 中打印请求和响应的详细信息，方便调试。
 * 可以看到：请求 URL、请求头、响应状态码、响应 JSON 内容。
 */
object RetrofitClient {

    /**
     * OkHttp 日志拦截器 — 打印请求/响应详情到 Logcat
     *
     * Level.BODY：打印所有内容（URL + Headers + Body）
     * 正式发布时改为 Level.NONE 或移除
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * OkHttpClient — 底层 HTTP 引擎
     *
     * 配置：
     * - connectTimeout：建立连接的超时时间
     * - readTimeout：读取数据的超时时间
     * - loggingInterceptor：调试日志
     */
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * Retrofit 实例
     *
     * baseUrl 必须以 "/" 结尾，否则 Retrofit 会抛异常。
     * addConverterFactory：Gson 自动将 JSON ↔ Kotlin 对象互转。
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(PostApi.BASE_URL)           // https://jsonplaceholder.typicode.com/
        .client(okHttpClient)                 // 使用上面配置的 OkHttp
        .addConverterFactory(GsonConverterFactory.create())  // JSON ↔ Kotlin
        .build()

    /**
     * PostApi 实例 — 所有网络请求的入口
     *
     * `by lazy`：懒加载，第一次访问时才初始化（不需要主动调用 create）
     */
    val postApi: PostApi by lazy {
        retrofit.create(PostApi::class.java)
    }
}
