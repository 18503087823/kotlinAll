package com.example.kotlinlearn

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.kotlinlearn.databinding.ActivityDetailBinding

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  DetailActivity — 通用知识详情页                                            ║
// ║                                                                             ║
// ║  职责：根据 Intent 传入的 pageId，从 KnowledgeData 中找到对应的 KnowledgePage，║
// ║       然后动态构建所有 UI（概述卡片 + N 个 Section 卡片 + 页面提示卡片）。    ║
// ║                                                                             ║
// ║  为什么动态构建 UI 而不是 XML 写死？                                         ║
// ║  - 每个知识页面的 Section 数量不同（作用域函数 1 个，Object 3 个，空安全 5 个）║
// ║  - 动态构建 = 一个布局文件适配所有页面，代码决定结构                          ║
// ║                                                                             ║
// ║  动态构建的 UI 结构：                                                        ║
// ║  ┌─ 概述卡片（可选）                                                        ║
// ║  │   └─ page.overview 文字                                                  ║
// ║  ├─ Section 卡片 ①                                                          ║
// ║  │   ├─ 标题："① 对象声明 — 单例模式"                                        ║
// ║  │   ├─ 描述文字                                                             ║
// ║  │   ├─ 代码块（等宽字体，横纵滚动）                                          ║
// ║  │   ├─ 关键要点列表                                                         ║
// ║  │   └─ 注意事项（可选，橙色背景）                                            ║
// ║  ├─ Section 卡片 ② ...                                                      ║
// ║  ├─ Section 卡片 ③ ...                                                      ║
// ║  └─ 页面提示卡片（可选）                                                     ║
// ║       └─ page.pageNote                                                      ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
class DetailActivity : AppCompatActivity() {

    // ═══════════════════════════════════════════════════════════════════════════
    //  静态常量
    // ═══════════════════════════════════════════════════════════════════════════

    companion object {
        /**
         * Intent extra key：传递 KnowledgePage.id
         *
         * `const val` = 编译期常量，等价 Java 的 public static final String
         * 用 const 修饰的 val 在编译时直接内联到使用处，零运行时开销
         */
        const val EXTRA_PAGE_ID = "page_id"
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  实例属性
    // ═══════════════════════════════════════════════════════════════════════════

    private lateinit var binding: ActivityDetailBinding

    // ═══════════════════════════════════════════════════════════════════════════
    //  生命周期
    // ═══════════════════════════════════════════════════════════════════════════

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ── 第 1 步：获取数据 ──
        //
        // intent.getStringExtra(key) → 从 Intent 中取 pageId
        // 如果 key 不存在 → 返回 null
        val pageId = intent.getStringExtra(EXTRA_PAGE_ID)

        // ── 第 2 步：根据 pageId 查找 KnowledgePage ──
        //
        // find { } 是 Kotlin 集合的扩展函数，返回第一个匹配的元素
        // 内部 it 指代每个 KnowledgePage
        // ?: 如果没找到 → 显示作用域函数 let 作为兜底
        //    （Elvis 操作符：左边为 null 时取右边）
        val page = KnowledgeData.allPages
            .find { it.id == pageId }
            ?: KnowledgeData.scopeLet  // 兜底

        // ── 第 3 步：动态构建 UI ──
        buildUI(page)
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  UI 构建（核心方法）
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * ## 动态构建整个详情页
     *
     * 从 KnowledgePage 数据出发，逐步创建所有 View 并添加到 contentContainer。
     *
     * ### 构建顺序
     * 1. 配置 Toolbar
     * 2. 添加概述卡片（如果有 overview）
     * 3. 遍历 sections，为每个 section 创建一张卡片
     * 4. 添加页面提示卡片（如果有 pageNote）
     *
     * ### 涉及的 Kotlin 知识点
     * - `with()` — 批量调用 binding 成员
     * - `apply {}` — 配置 Toolbar
     * - `forEachIndexed {}` — 带索引的遍历
     * - `?.let {}` — 只在非空时执行
     * - 扩展函数 — addView 等
     */
    private fun buildUI(page: KnowledgePage) {

        // ── 第 1 步：Toolbar ──
        //
        // 【实战：apply 配置 Toolbar】
        // toolbar 需要设置 3 个属性 → 用 apply 批量配置
        binding.toolbar.apply {
            title = page.title
            // page.iconColor 是 "#RRGGBB" 格式字符串
            setBackgroundColor(Color.parseColor(page.iconColor))
            // 返回箭头 → finish() 回到上一页
            setNavigationOnClickListener { finish() }
        }

        // ── 第 2~4 步：构建内容卡片 ──
        //
        // 【实战：with 批量操作 contentContainer】
        // contentContainer 是 LinearLayout，需要多次 addView
        // 用 with 后所有 addView 都不需要写 binding.contentContainer. 前缀
        with(binding.contentContainer) {

            // 2. 概述卡片（如果有）
            if (page.overview.isNotEmpty()) {
                addView(buildOverviewCard(page.overview))
            }

            // 3. 遍历每个 Section，生成一张卡片
            // forEachIndexed 提供了 index（从 0 开始）和元素
            page.sections.forEachIndexed { index, section ->
                addView(buildSectionCard(index, section))
            }

            // 4. 页面提示卡片（如果有）
            if (page.pageNote.isNotEmpty()) {
                addView(buildPageNoteCard(page.pageNote))
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  View 构建方法（每个方法返回一个 View）
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * ## 构建概述卡片
     *
     * 白色 MaterialCardView，内含 Markdown 风格的概述文字。
     *
     * ### 【实战：apply 链式调用】
     * CardView + TextView 都用 apply 配置，层层嵌套。
     * 外层 CardView 用 apply 设置圆角和边距，
     * 内层 TextView 用 apply 设置文字和样式。
     */
    private fun buildOverviewCard(text: String): View {
        // ── 外层：MaterialCardView ──
        return CardView(this).apply {
            // 卡片外观
            radius = dp(12)
            setCardElevation(dp(2))
            setContentPadding(dp(16), dp(16), dp(16), dp(16))
            // 下边距
            (layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin = dp(16)

            // ── 内层：文字 ──
            addView(TextView(context).apply {
                this.text = text
                textSize = 14f
                setTextColor(Color.parseColor("#444444"))
                lineSpacingMultiplier = 1.6f
            })
        }
    }

    /**
     * ## 构建一个 Section 卡片
     *
     * 一张卡片包含 5 个部分（按顺序）：
     * 1. 标题行（带序号）
     * 2. 描述文字
     * 3. 代码块
     * 4. 关键要点列表
     * 5. 注意事项（如果有 note）
     *
     * ### 参数说明
     * @param index   Section 序号（0-based）→ 显示为 "① / ② / ③ ..."
     * @param section 内容数据
     */
    private fun buildSectionCard(index: Int, section: ContentSection): View {
        // 序号符号：① ② ③ ④ ⑤ ⑥ ⑦ ⑧
        val nums = arrayOf("①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧")
        val num = nums.getOrElse(index) { "${index + 1}." }

        return CardView(this).apply {
            radius = dp(12)
            setCardElevation(dp(2))
            setContentPadding(dp(16), dp(16), dp(16), dp(16))
            (layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin = dp(16)

            // 内容用垂直 LinearLayout
            addView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL

                // ── ① 标题行 ──
                addView(TextView(context).apply {
                    text = "$num  ${section.title}"
                    textSize = 17f
                    setTextColor(Color.parseColor("#333333"))
                    setTypeface(typeface, Typeface.BOLD)
                    setPadding(0, 0, 0, dp(12))
                })

                // ── ② 描述文字 ──
                if (section.description.isNotEmpty()) {
                    addView(TextView(context).apply {
                        text = section.description
                        textSize = 14f
                        setTextColor(Color.parseColor("#555555"))
                        lineSpacingMultiplier = 1.55f
                        setPadding(0, 0, 0, dp(12))
                    })
                }

                // ── ③ 代码块 ──
                if (section.sampleCode.isNotEmpty()) {
                    addView(buildCodeBlock(section.sampleCode))
                    // 代码块下方间距
                    addView(space( dp(12) ))
                }

                // ── ④ 关键要点列表 ──
                if (section.keyPoints.isNotEmpty()) {
                    // 小标题："关键要点"
                    addView(TextView(context).apply {
                        text = "🔑 关键要点"
                        textSize = 14f
                        setTextColor(Color.parseColor("#333333"))
                        setTypeface(typeface, Typeface.BOLD)
                        setPadding(0, dp(4), 0, dp(6))
                    })
                    // 逐条添加
                    section.keyPoints.forEach { point ->
                        addView(TextView(context).apply {
                            text = "▸ $point"
                            textSize = 13.5f
                            setTextColor(Color.parseColor("#444444"))
                            setPadding(dp(8), dp(3), 0, dp(3))
                            lineSpacingMultiplier = 1.4f
                        })
                    }
                }

                // ── ⑤ 注意事项（仅当 note 非空时显示） ──
                if (section.note.isNotEmpty()) {
                    addView(space( dp(8) ))
                    addView(TextView(context).apply {
                        text = "⚠️ ${section.note}"
                        textSize = 13f
                        setTextColor(Color.parseColor("#BF360C"))
                        setBackgroundColor(Color.parseColor("#FFF3E0"))
                        setPadding(dp(12), dp(10), dp(12), dp(10))
                        lineSpacingMultiplier = 1.45f
                    })
                }
            })
        }
    }

    /**
     * ## 构建页面底部提示卡片
     *
     * 比概述卡片更醒目的样式（带边框色条），放总结性内容。
     */
    private fun buildPageNoteCard(text: String): View {
        return CardView(this).apply {
            radius = dp(12)
            setCardElevation(dp(2))
            setCardBackgroundColor(Color.parseColor("#E8F5E9"))  // 浅绿背景
            setContentPadding(dp(16), dp(14), dp(16), dp(14))

            addView(TextView(context).apply {
                this.text = text
                textSize = 13.5f
                setTextColor(Color.parseColor("#2E7D32"))
                lineSpacingMultiplier = 1.55f
            })
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  工具方法
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * ## 构建代码块 View
     *
     * 结构：
     * ```
     * ScrollView（竖向滚动）
     *   └─ HorizontalScrollView（横向滚动）
     *        └─ TextView（等宽字体，选中可复制）
     * ```
     *
     * 为什么需要双层 ScrollView？
     * - 长代码需要竖向滚动（单屏可能显示不下）
     * - 长行代码需要横向滚动（手机屏幕窄）
     * - 两个 ScrollView 嵌套覆盖了两种方向
     *
     * ### 等宽字体设置
     * `typeface = Typeface.MONOSPACE` → 所有字符等宽，代码对齐完美
     */
    private fun buildCodeBlock(code: String): View {
        // 外层容器：带深色背景的 FrameLayout（模拟 IDE 代码区）
        return CardView(this).apply {
            radius = dp(8)
            setCardBackgroundColor(Color.parseColor("#2B2B2B"))
            setContentPadding(dp(4), dp(4), dp(4), dp(4))

            // 竖向滚动容器
            addView(ScrollView(context).apply {
                // 横向滚动容器
                addView(HorizontalScrollView(context).apply {
                    // 代码文字
                    addView(TextView(context).apply {
                        text = code
                        textSize = 12.5f
                        setTextColor(Color.parseColor("#A9B7C6"))  // IDE 风格灰白文字
                        typeface = Typeface.MONOSPACE               // 等宽字体
                        lineSpacingMultiplier = 1.5f
                        setPadding(dp(12), dp(12), dp(12), dp(12))
                        setTextIsSelectable(true)                   // 允许长按复制！
                    })
                })
            })
        }
    }

    /**
     * ## 间距 View
     *
     * 创建一个透明的占位 View，高度 = h dp。
     * 用于卡片内部元素之间的间距。
     */
    private fun space(h: Int): View {
        return View(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, h
            )
        }
    }

    /**
     * ## dp → px 转换工具
     *
     * 代码中创建 View 时，尺寸只能写 px。
     * 但设计师和我们的心智模型是 dp（密度无关像素）。
     * 这个小工具把 dp 转为 px，让代码中的数字是 dp 思维。
     *
     * `resources.displayMetrics.density` 是设备屏幕密度：
     * - mdpi (160dpi) → density = 1.0
     * - hdpi (240dpi) → density = 1.5
     * - xhdpi (320dpi) → density = 2.0
     * - xxhdpi (480dpi) → density = 3.0
     */
    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }
}
