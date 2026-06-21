package com.example.kotlinlearn.wxapi

import android.app.Activity
import android.os.Bundle
import com.umeng.socialize.weixin.view.WXCallbackActivity

/**
 * ## WXEntryActivity — 微信分享/登录回调
 *
 * 必须放在 `包名.wxapi` 包下。
 * 如果不需要自定义逻辑，直接继承 Umeng 的 WXCallbackActivity 即可。
 *
 * 注册在 AndroidManifest.xml 中，`android:exported="true"`。
 */
class WXEntryActivity : WXCallbackActivity()
