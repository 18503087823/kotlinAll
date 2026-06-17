plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.kotlinlearn"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kotlinlearn"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // ── AndroidX 基础 ──
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    // ViewPager2 — 引导页横向滑动切换
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // ── MVVM 架构组件 ──
    // ViewModel：管理 UI 相关数据，横竖屏切换时数据不丢失
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    // LiveData：可观察的数据容器，数据变化时自动通知 UI
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    // Activity KTX：提供 viewModels() 委托和 lifecycleScope
    implementation("androidx.activity:activity-ktx:1.8.2")
    // Fragment KTX：Fragment 中的 viewModels() 委托
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // ── 网络请求：Retrofit + Gson ──
    // Retrofit：类型安全的 HTTP 客户端
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson Converter：自动将 JSON 转为 Kotlin 数据类
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // OkHttp 日志拦截器：调试时查看请求/响应详情
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ── 协程（Kotlin 异步编程核心）──
    // kotlinx-coroutines-android 包含 Dispatchers.Main 等 Android 专用调度器
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // ── 图片加载 ──
    // Coil：Kotlin 优先的图片加载库（轻量，配合协程）
    implementation("io.coil-kt:coil:2.5.0")
}
