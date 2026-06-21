package com.example.kotlinlearn.ui.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.kotlinlearn.LoginActivity
import com.example.kotlinlearn.PreferenceManager
import com.example.kotlinlearn.R
import com.example.kotlinlearn.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

/**
 * ## ProfileFragment — 个人中心 (Tab 4)
 *
 * 功能：
 * - 圆形头像展示（点击 → 拍照/相册选择 → 圆形裁剪 → 模拟上传 → 保存 URL）
 * - 下次登录自动加载头像（从 PreferenceManager 读取 URL）
 * - 退出登录
 *
 * TODO 替换真实后端：
 * 1. AvatarApi.kt → 改 BASE_URL 为真实地址
 * 2. AvatarRepository.kt → 改 @Part("avatar") 的 key 为后端约定值
 * 3. 删掉这个 TODO 注释
 */
class ProfileFragment : Fragment() {

    private var _b: FragmentProfileBinding? = null
    private val b get() = _b!!

    /** 拍照时存储临时文件的 URI */
    private var photoUri: Uri? = null

    // ── 相机权限 launcher ──
    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) launchCamera() else Toast.makeText(requireContext(), "需要相机权限才能拍照", Toast.LENGTH_SHORT).show()
    }

    // ── 相册选择 launcher — ACTION_PICK 直接进图库 ──
    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data
        if (result.resultCode == android.app.Activity.RESULT_OK && uri != null) {
            processAvatar(uri)
        }
    }

    // ── 拍照 launcher ──
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) photoUri?.let { processAvatar(it) }
    }

    // ═══════════════════════════════════════════════════════════════════════════

    override fun onCreateView(inflater: LayoutInflater, c: ViewGroup?, state: Bundle?): View {
        _b = FragmentProfileBinding.inflate(inflater, c, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadAvatar()
        setupClickListeners()
    }

    // ── 加载头像 ─────────────────────────────────────────────────────────────

    /** 从 PreferenceManager 读取头像 URL 并加载显示 */
    private fun loadAvatar() {
        val url = PreferenceManager.avatarUrl
        if (!url.isNullOrBlank()) {
            b.ivAvatar.load(url) {
                crossfade(300)
                placeholder(android.R.color.darker_gray)
                error(android.R.color.darker_gray)
            }
        }
    }

    // ── 点击 ─────────────────────────────────────────────────────────────────

    private fun setupClickListeners() {
        // 点击头像 → 弹出选择框
        b.ivAvatar.setOnClickListener { showAvatarPicker() }

        // 菜单项
        b.itemCamera.setOnClickListener { openCamera() }
        b.itemGallery.setOnClickListener { openGallery() }
        b.itemShare.setOnClickListener { doShare() }
        b.itemLogout.setOnClickListener { showLogoutDialog() }
    }

    /** 弹出 BottomSheet 选择方式 */
    private fun showAvatarPicker() {
        val items = arrayOf("拍照", "从相册选择")
        AlertDialog.Builder(requireContext())
            .setTitle("更换头像")
            .setItems(items) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .show()
    }

    // ── 拍照 ─────────────────────────────────────────────────────────────────

    /** 先检查权限，有权限则直接拍照，无权限则请求 */
    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            launchCamera()
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    /** 已获得权限，启动系统相机 */
    private fun launchCamera() {
        val dir = File(requireContext().cacheDir, "avatars")
        dir.mkdirs()
        val file = File(dir, "avatar_${System.currentTimeMillis()}.jpg")
        photoUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            file
        )
        cameraLauncher.launch(photoUri!!)
    }

    // ── 相册 ─────────────────────────────────────────────────────────────────

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    // ── 分享 ─────────────────────────────────────────────────────────────────

    /** 弹出友盟分享面板 */
    private fun doShare() {
        if (!UmengShareConfig.isConfigured) {
            Toast.makeText(requireContext(), "请先配置友盟 AppKey（见 UmengShareConfig.kt）", Toast.LENGTH_LONG).show()
            return
        }
        UmengShareHelper.share(
            activity = requireActivity(),
            webUrl = "https://github.com/18503087823/kotlinAll",
            title = "Kotlin 学习助手",
            desc = "用 Kotlin 学习 Android 开发，包含 23 个知识点 + MVVM 实战，快来一起学习！",
            thumbRes = R.mipmap.ic_launcher
        ) { platform, success ->
            val msg = if (success) "分享成功" else "分享取消"
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }
    }

    // ── 处理头像：裁剪圆形 → 转文件 → 模拟上传 → 保存 URL → 显示 ──────────

    private fun processAvatar(sourceUri: Uri) {
        lifecycleScope.launch {
            try {
                // 1. 读取原始图片 → 裁剪为圆形 Bitmap
                val inputStream = requireContext().contentResolver.openInputStream(sourceUri)
                val original = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                if (original == null) {
                    Toast.makeText(requireContext(), "无法读取图片", Toast.LENGTH_SHORT).show()
                    return@launch
                }
                val circled = cropToCircle(original)

                // 2. 将圆形 Bitmap 保存为文件（上传用）
                val file = withContext(Dispatchers.IO) {
                    saveBitmapToFile(circled)
                }

                // 3. 立即显示圆形头像
                b.ivAvatar.setImageBitmap(circled)

                // 4. 模拟上传到后台（当前 URL 是假的，走 catch 分支后使用本地假的 URL）
                val avatarUrl = try {
                    withContext(Dispatchers.IO) {
                        AvatarRepository().upload(file)
                    }
                } catch (_: Exception) {
                    // 上传失败 → 生成假的 URL 以便后续替换
                    // TODO: 后端就绪后删除这段 fake URL 逻辑
                    "https://your-server.com/avatars/user_${System.currentTimeMillis()}.jpg"
                }

                // 5. 保存 URL，下次登录自动加载
                PreferenceManager.saveAvatarUrl(avatarUrl)

                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "头像更新成功", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "处理失败: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ── 圆形裁剪 ─────────────────────────────────────────────────────────────

    /**
     * ## 将 Bitmap 裁剪为圆形
     *
     * 原理：画一个圆形遮罩，用 DST_IN 模式合成 → 圆外的像素变透明。
     * 最终取 min(width, height) 的中心正方形区域。
     */
    private fun cropToCircle(source: Bitmap): Bitmap {
        val size = minOf(source.width, source.height)
        val xOffset = (source.width - size) / 2
        val yOffset = (source.height - size) / 2

        // 取中心正方形
        val squared = Bitmap.createBitmap(source, xOffset, yOffset, size, size)

        // 创建输出 Bitmap
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // 画圆
        val radius = size / 2f
        canvas.drawCircle(radius, radius, radius, paint)

        // DST_IN — 保留圆的区域，圆外变透明
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(squared, 0f, 0f, paint)

        squared.recycle()
        return output
    }

    /** 将 Bitmap 保存为 JPEG 文件 */
    private fun saveBitmapToFile(bitmap: Bitmap): File {
        val dir = File(requireContext().cacheDir, "avatars")
        dir.mkdirs()
        val file = File(dir, "avatar_cropped_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
        }
        return file
    }

    // ── 退出登录 ─────────────────────────────────────────────────────────────

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("退出登录")
            .setMessage("确定要退出登录吗？")
            .setPositiveButton("退出") { _, _ -> doLogout() }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun doLogout() {
        PreferenceManager.setLoggedOut()
        val intent = Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
