# Add project specific ProGuard rules here.

# ═══ 友盟 SDK 混淆规则 ═══
-keep class com.umeng.** { *; }
-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ═══ 微信 SDK 混淆规则 ═══
-keep class com.tencent.mm.opensdk.** { *; }
-keep class com.tencent.wxop.** { *; }
-keep class com.tencent.mm.sdk.** { *; }

# ═══ QQ SDK 混淆规则 ═══
-keep class com.tencent.** { *; }
-dontwarn com.tencent.**
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

# ═══ OkHttp / Retrofit ═══
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keep class retrofit2.** { *; }

# ═══ Gson ═══
-keep class com.google.gson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
