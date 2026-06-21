package com.example.kotlinlearn.ui.profile

/**
 * ## 友盟分享密钥配置
 *
 * ⚠️ 以下全是占位符，请申请后替换：
 *
 * | 平台  | 申请地址                         | 替换哪个字段        |
 * |------|---------------------------------|--------------------|
 * | 友盟  | https://mobile.umeng.com/        | UMENG_APP_KEY      |
 * | 微信  | https://open.weixin.qq.com/      | WX_APP_ID / WX_SECRET |
 * | QQ   | https://connect.qq.com/          | QQ_APP_ID / QQ_APP_KEY |
 *
 * 拿到 key 后直接替换下面字段即可，其他代码无需改动。
 */
object UmengShareConfig {

    // ═══ 友盟 AppKey ═══
    const val UMENG_APP_KEY = "6a3647986f259537c7bbcb40"

    // ═══ 微信 ═══
    const val WX_APP_ID = "wx7278936e556bf143"
    const val WX_APP_SECRET = "6d82f90adb743054f5751131dd1b8df9"

    // ═══ QQ ═══
    const val QQ_APP_ID = "1112440639"
    const val QQ_APP_KEY = "I2S3f6mkzmHHSGYS"

    // ═══ 是否已配置真实 Key ═══
    val isConfigured: Boolean get() = UMENG_APP_KEY != "你的友盟AppKey"
}
