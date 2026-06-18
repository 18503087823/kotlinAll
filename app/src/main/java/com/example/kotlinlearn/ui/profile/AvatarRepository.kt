package com.example.kotlinlearn.ui.profile

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * ## AvatarRepository — 头像上传仓库
 *
 * 当前为假的 BASE_URL，上传会走 catch 分支返回 Error。
 * 替换真实 URL 后即可正常上传。
 *
 * TODO 替换步骤：
 * 1. 把 AvatarApi.BASE_URL 改成真实地址
 * 2. 把 uploadAvatar() 里的 @Part("avatar") 改成后端约定的 key
 */
class AvatarRepository {

    private val api: AvatarApi by lazy {
        val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
        Retrofit.Builder()
            .baseUrl(AvatarApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AvatarApi::class.java)
    }

    /**
     * 上传头像 → 返回服务器上的头像 URL
     *
     * @param file 本地裁剪后的头像文件
     * @return 成功时返回 avatarUrl，失败时抛异常
     */
    suspend fun upload(file: File): String {
        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("avatar", file.name, requestBody)
        val response = api.uploadAvatar(part)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.avatarUrl
        } else {
            throw Exception("上传失败: ${response.code()}")
        }
    }
}
