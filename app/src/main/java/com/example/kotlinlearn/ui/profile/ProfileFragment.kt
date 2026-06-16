package com.example.kotlinlearn.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.kotlinlearn.LoginActivity
import com.example.kotlinlearn.PreferenceManager
import com.example.kotlinlearn.databinding.FragmentProfileBinding

/**
 * ## ProfileFragment — 个人中心 (Tab 4)
 *
 * 功能：
 * - 展示用户信息（静态 Demo 数据）
 * - 退出登录（带确认弹窗）→ 清除登录态 → 回到登录页
 */
class ProfileFragment : Fragment() {

    private var _b: FragmentProfileBinding? = null
    private val b get() = _b!!

    override fun onCreateView(inflater: LayoutInflater, c: ViewGroup?, state: Bundle?): View {
        _b = FragmentProfileBinding.inflate(inflater, c, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.itemLogout.setOnClickListener { showLogoutDialog() }
    }

    /** 退出登录确认弹窗 */
    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("退出登录")
            .setMessage("确定要退出登录吗？")
            .setPositiveButton("退出") { _, _ -> doLogout() }
            .setNegativeButton("取消", null)
            .show()
    }

    /** 清除登录态 → 返回登录页；清除 Activity 栈 */
    private fun doLogout() {
        PreferenceManager.setLoggedOut()
        val intent = Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        // CLEAR_TASK 会清掉整个任务栈，用户按返回键不会回到主页
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
