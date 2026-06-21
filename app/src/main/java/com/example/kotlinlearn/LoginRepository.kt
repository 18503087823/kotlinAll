package com.example.kotlinlearn

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  LoginRepository — 登录数据仓库                                            ║
// ║                                                                             ║
// ║  职责：                                                                     ║
// ║  1. 管理 Retrofit 实例（登录专用 baseUrl）                                   ║
// ║  2. 发出登录 POST 请求                                                      ║
// ║  3. 如果请求失败（网络不通），本地校验账号密码作为兜底                        ║
// ║                                                                             ║
// ║  设计思路：                                                                 ║
// ║  - 优先走真实服务器 POST 请求                                                ║
// ║  - 网络不通 / 服务器未部署时，本地比对账号密码：mql493536775 / 520xxx314     ║
// ║  - 这样 Demo 在任何环境都能跑通                                              ║
// ╚══════════════════════════════════════════════════════════════════════════════╝

class LoginRepository {

    // ── 正确的登录凭证（本地兜底校验用） ──
    companion object {
        const val CORRECT_USERNAME = "mql493536775"
        const val CORRECT_PASSWORD = "520xxx1314"
    }

    /**
     * ## 执行登录
     *
     * 流程：
     * 1. 尝试向服务器发送 POST 请求
     * 2. 成功 → 返回服务器结果
     * 3. 失败 → 本地校验账号密码 → 匹配就返回登录成功
     *
     * ### 参数
     * @param username  用户输入的账号
     * @param password  用户输入的密码
     *
     * ### 返回值
     * LoginResponse.success = true 代表登录成功
     */
    suspend fun login(username: String, password: String): LoginResponse {
        return try {
            // ── 第 1 步：尝试真实 POST 请求 ──
            val api = buildRetrofit().create(LoginApi::class.java)
            api.login(username, password)
        } catch (e: Exception) {
            // ── 第 2 步：网络不通 → 本地校验兜底 ──
            if (username == CORRECT_USERNAME && password == CORRECT_PASSWORD) {
                LoginResponse(
                    success = true,
                    message = "登录成功（本地验证）",
                    token = "local_token_demo"
                )
            } else {
                LoginResponse(
                    success = false,
                    message = "账号或密码错误"
                )
            }
        }
    }

    /** 构建 Retrofit 实例（每次调用时新建，简单可靠） */
    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(LoginApi.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
