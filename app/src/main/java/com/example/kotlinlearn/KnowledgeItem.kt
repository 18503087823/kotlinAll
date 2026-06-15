package com.example.kotlinlearn

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  通用知识点数据模型                                                         ║
// ║                                                                             ║
// ║  一个 KnowledgePage = 一个详情页。                                           ║
// ║  一个 ContentSection = 详情页内的一个知识区块。                               ║
// ║                                                                             ║
// ║  设计理念：                                                                 ║
// ║  - 作用域函数：1 个函数 = 1 个 page，page 内 1 个 section                   ║
// ║  - Object：1 个 page，内部 3 个 section（对象声明 / 伴生对象 / 对象表达式）  ║
// ║  - 空安全：1 个 page，内部 5 个 section（? / ?. / ?: / !! / as?）          ║
// ║  - 别名：1 个 page，内部 N 个 section                                       ║
// ║                                                                             ║
// ║  这样无论知识点是「一页一个」还是「一页多个」，模型都能统一表达。              ║
// ╚══════════════════════════════════════════════════════════════════════════════╝

/**
 * ## 内容区块 — 详情页的一个知识小节
 *
 * @property title       小节标题，如 "对象声明（Singleton）"
 * @property description 文字解释（支持多行，会在卡片中渲染）
 * @property sampleCode  代码示例（等宽字体）
 * @property keyPoints   关键要点列表，每行一条
 * @property note        注意事项（可选）
 */
data class ContentSection(
    val title: String,
    val description: String,
    val sampleCode: String,
    val keyPoints: List<String>,
    val note: String = ""
)

/**
 * ## 知识页面 — 代表一个可点击进入的知识点详情页
 *
 * 主页列表中每个卡片对应一个 KnowledgePage。
 *
 * @property id          唯一标识（用于 Intent 传参）
 * @property category    所属分类标签："作用域函数" / "Object" / "空安全" / "类型别名"
 * @property title       页面标题（Toolbar 显示）
 * @property briefDesc   一行简介（卡片副标题）
 * @property iconText    卡片左侧图标上的文字（2-3 字）
 * @property iconColor   图标圆形背景色（#RRGGBB）
 * @property overview    页面顶部的总述文字（可选，空字符串则不显示）
 * @property sections    页面内的知识区块列表（按顺序渲染）
 * @property pageNote    页面底部的通用注意事项（可选）
 */
data class KnowledgePage(
    val id: String,
    val category: String,
    val title: String,
    val briefDesc: String,
    val iconText: String,
    val iconColor: String,
    val overview: String = "",
    val sections: List<ContentSection>,
    val pageNote: String = ""
)
