package com.example.kotlinlearn

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinlearn.databinding.ItemScopeFunctionBinding

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  ScopeFunctionAdapter — 主页列表的 RecyclerView 适配器                     ║
// ║                                                                             ║
// ║  负责：                                                                     ║
// ║  - 创建列表项的 View（onCreateViewHolder）                                   ║
// ║  - 将数据绑定到 View（onBindViewHolder → ViewHolder.bind）                   ║
// ║  - 处理点击事件（通过构造参数传入的回调）                                     ║
// ║                                                                             ║
// ║  教学目的：                                                                 ║
// ║  - ViewHolder.bind() 中用 with 批量操作 binding 控件                         ║
// ║  - tvIcon 配置用 apply                                                       ║
// ║  - onCreateViewHolder 中用 also 附加日志                                     ║
// ║  - iconColors 用 mapOf 创建不可变映射                                        ║
// ╚══════════════════════════════════════════════════════════════════════════════╝

/**
 * ## RecyclerView 适配器
 *
 * ### 构造参数
 * @param items  要展示的作用域函数列表
 * @param onItemClick  当用户点击某一项时触发的回调，参数是被点击的 [ScopeFunction]
 *
 * ### Kotlin 语法点
 * 第二个参数 `onItemClick: (ScopeFunction) -> Unit` 是一个**函数类型**：
 * - 表示「接收一个 ScopeFunction，返回 Unit（无返回值）」的函数
 * - 使用时就像传一个 Lambda 表达式
 * - 这是 Kotlin 的「函数是一等公民」特性
 */
class ScopeFunctionAdapter(
    private val items: List<ScopeFunction>,
    private val onItemClick: (ScopeFunction) -> Unit
) : RecyclerView.Adapter<ScopeFunctionAdapter.ViewHolder>() {

    // ═══════════════════════════════════════════════════════════════════════════
    //  常量数据
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * 每个作用域函数的图标颜色。
     *
     * `mapOf(...)` 创建的是只读 Map，编译期确定，运行期不可修改。
     * Key = 函数名（用作 color 查找时的 key）
     * Value = 主题色十六进制字符串（对应 Material Design 配色）
     *
     * 颜色设计意图：
     * - let (紫色 #7B1FA2)：表示转换 / 映射
     * - run (深蓝 #283593)：表示执行 / 计算
     * - with (墨绿 #00695C)：表示伴随 / 辅助
     * - apply (橙色 #E65100)：表示应用 / 构建
     * - also (玫红 #880E4F)：表示附加 / 顺便
     */
    private val iconColors = mapOf(
        "let"   to "#7B1FA2",  // 紫色 — let 的语义：让（映射/转换）
        "run"   to "#283593",  // 深蓝 — run 的语义：运行（执行计算）
        "with"  to "#00695C",  // 墨绿 — with 的语义：伴随（辅助工具）
        "apply" to "#E65100",  // 橙色 — apply 的语义：应用到（构建配置）
        "also"  to "#880E4F"   // 玫红 — also 的语义：也（附加操作）
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  ViewHolder 类
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * ## ViewHolder — 持有列表项的 View 引用
     *
     * 使用 `inner class` 声明：
     * - inner 意味着 ViewHolder 持有外部类 ScopeFunctionAdapter 的引用
     * - 因此可以直接访问 `items` 和 `onItemClick`
     * - 如果不需要访问外部类，应该去掉 inner（静态内部类，防止内存泄漏）
     * - 这里需要 inner 是为了从 bind() 方法内调用 onItemClick
     *
     * 构造参数 `val binding`：
     * - 使用 ViewBinding 而非传统的 findViewById
     * - binding 包含了 item_scope_function.xml 中所有带 id 的 View 引用
     * - 类型安全、空安全，编译期就能发现 id 写错的问题
     */
    inner class ViewHolder(val binding: ItemScopeFunctionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * ## 将数据绑定到 View
         *
         * ### 【实战：with 的用法】
         * `with(binding) { ... }` — 在 binding 的上下文中执行代码块：
         * - 块内的 `tvName` 等价于 `binding.tvName`
         * - 块内的 `tvBrief` 等价于 `binding.tvBrief`
         * - ...依此类推
         *
         * 为什么这里用 with 而不是 run？
         * - binding 已经存在，不需要 ?. 安全调用
         * - 需要对 binding 做多件事（设置多个控件的属性）
         * - with 语义：「用 binding 做以下操作」— 语义最准确
         * - 当然，这里用 `binding.run { }` 也可以，效果完全一样
         *
         * ### 【实战：apply 的用法】
         * `tvIcon.apply { ... }` — 配置 tvIcon 后返回 tvIcon 本身：
         * - text = item.name → 设置文字
         * - background = circle → 设置圆形背景
         * - apply 返回 tvIcon → 这里不需要获取返回值，只利用了「批量配置」的特性
         *
         * 为什么 tvIcon 用 apply 而不是直接赋值？
         * - 需要连续对 tvIcon 做多件事：设置 text + 创建并应用 drawable
         * - 用 apply 把相关操作分组，一眼看出「这是在配置图标」
         */
        fun bind(item: ScopeFunction) {
            // ── with：对 binding 做批量操作 ──
            with(binding) {
                // 文本信息直接赋值
                tvName.text = item.name
                tvBrief.text = item.briefDesc
                tvContextRef.text = item.contextRef
                tvReturn.text = item.returnValue

                // ── apply：配置 tvIcon 的样式 ──
                tvIcon.apply {
                    // 第 1 步：设置函数名文字
                    text = item.name

                    // 第 2 步：动态创建圆形背景，并根据函数名着色
                    //
                    // ResourcesCompat.getDrawable(...)!!.mutate() 的含义：
                    // - ResourcesCompat.getDrawable → 兼容旧版 Android 的加载方式
                    // - !! → 断言 drawable 一定存在（不存在就抛 NPE）
                    // - .mutate() → 创建 drawable 的可变副本
                    //   如果不用 mutate()，这个 drawable 是一个全局单例，
                    //   修改它的颜色会影响所有使用 bg_circle 的控件
                    val circle = ResourcesCompat.getDrawable(
                        resources,                      // 从 itemView 的 context 获取资源
                        R.drawable.bg_circle,           // 圆形 shape drawable
                        null                            // theme，null 表示用默认主题
                    )!!.mutate()                        // 创建独立副本，防止影响其他控件

                    // Color.parseColor() 将 "#RRGGBB" 字符串转为 int 颜色值
                    // iconColors[item.name] 从映射中取颜色，?: 提供默认兜底值
                    circle.setTint(Color.parseColor(
                        iconColors[item.name] ?: "#6200EE"  // 兜底：Material Purple
                    ))

                    // 应用修改后的背景
                    background = circle
                }

                // ── 点击事件 ──
                // setOnClickListener 配合 Lambda → 当用户点击卡片时触发
                root.setOnClickListener { onItemClick(item) }
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  RecyclerView.Adapter 三必须方法
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * ## 创建 ViewHolder
     *
     * 当 RecyclerView 需要一个新的列表项 View 时调用。
     * 这里的 View 会被回收复用（不会每个 item 都调用一次）。
     *
     * ### 【实战：also 的用法】
     * ```kotlin
     * return ViewHolder(binding).also {
     *     Log.d("Adapter", "创建 ViewHolder: ${it.hashCode()}")
     * }
     * ```
     *
     * 拆解分析：
     * 1. `ViewHolder(binding)` → 创建 ViewHolder 实例
     * 2. `.also { ... }` → 在返回之前，顺便执行日志打印
     * 3. also 返回 ViewHolder 本身 → 方法最终返回的是 ViewHolder
     *
     * 为什么用 also 而不是 apply？
     * - 这里不需要修改 ViewHolder，只是记录日志
     * - also 用 it 引用 → 语义上不进入对象内部，保持距离感
     * - 如果用 apply，日志代码和 ViewHolder 的成员混在一起，不清晰
     *
     * 为什么不用 let？
     * - let 返回 Lambda 结果 → 需要显式 return ViewHolder
     * - also 自动返回对象本身 → 更简洁
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // ── 第 1 步：用 LayoutInflater 把 XML 变成 View 对象 ──
        //
        // ItemScopeFunctionBinding.inflate(...) 内部做了三件事：
        // 1. 调用 LayoutInflater.inflate(R.layout.item_scope_function, parent, false)
        // 2. 创建 ItemScopeFunctionBinding 实例
        // 3. 将 inflated View 和 binding 关联起来
        //
        // 参数说明：
        // - inflater: 从 parent.context 获取 LayoutInflater
        // - parent: 列表的父容器（RecyclerView），用于正确计算布局参数
        // - false: 不要立即 attach 到 parent（RecyclerView 会自己管理 attach/detach）
        val binding = ItemScopeFunctionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        // ── 第 2 步：创建 ViewHolder，并用 also 附加日志 ──
        //
        // also 块内的 it 就是刚创建的 ViewHolder 对象
        // hashKey 是 it.hashCode() 的简写 — Kotlin 支持的字符串模板语法
        return ViewHolder(binding).also { holder ->
            android.util.Log.d("Adapter", "创建 ViewHolder: ${holder.hashCode()}")
        }
    }

    /**
     * ## 绑定数据到 ViewHolder
     *
     * 当 RecyclerView 需要显示某个位置的数据时调用。
     * 这个方法调用频率很高（每次滚动都可能触发），所以逻辑要尽量轻量。
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    /**
     * ## 返回列表项总数
     *
     * RecyclerView 通过这个值判断何时到达列表末尾。
     */
    override fun getItemCount(): Int = items.size
}
