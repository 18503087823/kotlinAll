package com.example.kotlinlearn.mvvm.data.model

/**
 * ## 文章数据模型
 *
 * 对应 JSONPlaceholder API 返回的 JSON：
 * ```json
 * { "userId": 1, "id": 1, "title": "...", "body": "..." }
 * ```
 *
 * @property userId  作者 ID
 * @property id      文章 ID
 * @property title   文章标题
 * @property body    文章正文
 */
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
