package com.example.kotlinlearn.ui.profile

import android.app.Activity
import com.tencent.tauth.Tencent
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb

/**
 * ## UmengShareHelper — 友盟分享管理
 *
 * 两步使用：
 * 1. `UmengShareHelper.init(applicationContext, config)` 一次
 * 2. `UmengShareHelper.share(activity, url, title, desc, thumbnail)` 触发分享
 *
 * 当前支持：微信好友 / 微信朋友圈 / QQ / QQ空间
 */
object UmengShareHelper {

    private var shareApi: UMShareAPI? = null

    /** 在 SplashActivity 中调用一次 */
    fun init(activity: Activity) {
        // 配置各平台 AppKey
        PlatformConfig.setWeixin(UmengShareConfig.WX_APP_ID, UmengShareConfig.WX_APP_SECRET)
        PlatformConfig.setQQZone(UmengShareConfig.QQ_APP_ID, UmengShareConfig.QQ_APP_KEY)

        // QQ 官方 SDK 授权（7.x 版本必须，否则 QQ/QQ空间分享失败）
        Tencent.setIsPermissionGranted(true)

        shareApi = UMShareAPI.get(activity.applicationContext)
    }

    /** 友盟 SDK 下载图片等资源时需调用的 onActivityResult */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: android.content.Intent?) {
        shareApi?.onActivityResult(requestCode, resultCode, data)
    }

    /** 友盟 SDK 内存回收 */
    fun release() {
        shareApi?.release()
        shareApi = null
    }

    // ── 分享 ─────────────────────────────────────────────────────────────────

    /**
     * ## 弹出分享面板
     *
     * @param activity  当前 Activity
     * @param webUrl    分享链接
     * @param title     分享标题
     * @param desc      分享描述
     * @param thumbRes  缩略图资源 ID（如 R.mipmap.ic_launcher）
     * @param platforms 要显示的分享平台，默认微信+QQ
     * @param callback  分享回调（可选）
     */
    fun share(
        activity: Activity,
        webUrl: String,
        title: String,
        desc: String,
        thumbRes: Int,
        platforms: List<SHARE_MEDIA> = listOf(
            SHARE_MEDIA.WEIXIN,
            SHARE_MEDIA.WEIXIN_CIRCLE,
            SHARE_MEDIA.QQ,
            SHARE_MEDIA.QZONE
        ),
        callback: ((SHARE_MEDIA?, Boolean) -> Unit)? = null
    ) {
        val web = UMWeb(webUrl).apply {
            this.title = title
            this.description = desc
            setThumb(UMImage(activity, thumbRes))
        }

        // 构造 ShareAction
        val action = ShareAction(activity).apply {
                setDisplayList(*platforms.toTypedArray())
            withMedia(web)
            setCallback(object : UMShareListener {
                override fun onStart(platform: SHARE_MEDIA) {}

                override fun onResult(platform: SHARE_MEDIA) {
                    callback?.invoke(platform, true)
                }

                override fun onError(platform: SHARE_MEDIA, t: Throwable) {
                    callback?.invoke(platform, false)
                }

                override fun onCancel(platform: SHARE_MEDIA) {
                    callback?.invoke(platform, false)
                }
            })
        }
        action.open()
    }
}
