package com.example.kotlinlearn.ui.profile

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * ## AvatarApi — 头像上传接口
 *
 * TODO: 替换为真实后端 URL 和参数 key
 * 当前 BASE_URL 为假地址，上传会失败但不会崩溃（Repository 有 try-catch）
 */
interface AvatarApi {

    companion object {
        // ⚠️ 假的 URL — 请替换为真实后端地址
        const val BASE_URL = "https://your-server.com/api/"
    }

    /**
     * 上传头像图片
     *
     * TODO: 参数 key "avatar" 请根据后端接口文档替换
     */
    @Multipart
    @POST("user/avatar")
    suspend fun uploadAvatar(
        @Part avatar: MultipartBody.Part
    ): Response<AvatarResponse>
}

/** 后端返回的头像 URL */
data class AvatarResponse(
    val avatarUrl: String
)
