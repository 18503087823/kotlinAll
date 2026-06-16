package com.example.kotlinlearn

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  LoginApi — 登录 POST 请求接口                                             ║
// ║                                                                             ║
// ║  @FormUrlEncoded + @Field = application/x-www-form-urlencoded 格式           ║
// ║  等价于 HTML 表单提交：<form method="POST">                                  ║
// ║                                                                             ║
// ║  账号：mql493536775   密码：520xxx314                                       ║
// ╚══════════════════════════════════════════════════════════════════════════════╝

/** 登录响应数据模型 */
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String = ""
)

/**
 * ## Retrofit 登录接口
 *
 * `@FormUrlEncoded` — 表示请求体为表单格式
 * `@Field("key")` — 表单字段，对应 HTML 的 `<input name="key">`
 *
 * ### 与 PostApi 的区别
 * - PostApi 的 `getPosts()` — GET 请求，无需参数体
 * - LoginApi 的 `login()` — POST 请求，带表单参数体
 */
interface LoginApi {

    companion object {
        /** 登录 API 基础地址 — 替换为你的真实服务器地址 */
        const val BASE_URL = "https://example.com/api/"
    }

    /**
     * ## 登录请求
     *
     * 请求示例：
     * ```
     * POST https://example.com/api/login
     * Content-Type: application/x-www-form-urlencoded
     *
     * username=mql493536775&password=520xxx314
     * ```
     *
     * 响应示例（JSON）：
     * ```json
     * { "success": true, "message": "登录成功", "token": "xxx" }
     * ```
     *
     * @param username  账号
     * @param password  密码
     * @return LoginResponse 登录结果
     */
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse
}
