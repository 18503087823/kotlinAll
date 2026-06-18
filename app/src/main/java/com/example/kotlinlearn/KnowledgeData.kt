package com.example.kotlinlearn

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  知识数据库 — KnowledgeData                                                 ║
// ║                                                                             ║
// ║  所有知识页面的数据都集中在这里，方便统一管理。                               ║
// ║                                                                             ║
// ║  页面组织结构（按分类）：                                                    ║
// ║  ┌─────────────────┬──────────────────────────────────────────────────┐     ║
// ║  │ 作用域函数 (5页)  │ let / also / with / run / apply                    │     ║
// ║  │ Object   (1页)   │ 对象声明 / 伴生对象 / 对象表达式（3 个 section） │     ║
// ║  │ 空安全    (1页)   │ ? / ?. / ?: / !! / as?（5 个 section）          │     ║
// ║  │ 类型别名  (1页)   │ typealias / import alias（2 个 section）         │     ║
// ║  │ 协程      (1页)   │ 协程vs线程 / 调度器 / Job / async / 挂起函数    │     ║
// ║  │ 类修饰符  (1页)   │ open/final / abstract / data / sealed / enum     │     ║
// ║  │ 成员修饰符(1页)   │ override / open / abstract / lateinit            │     ║
// ║  │ 泛型修饰符(1页)   │ out(协变) / in(逆变)                             │     ║
// ║  │ 循环      (1页)   │ .. / until / downTo / step / repeat / in / 解构 │     ║
// ║  │ 密封类实战(1页)   │ NetworkResult 三层架构 + when 穷尽检查          │     ║
// ║  │ MVVM Demo (1页)   │ 真实 API 列表 → 详情页                          │     ║
// ║  │ 内联函数  (1页)   │ inline / noinline / crossinline / reified       │     ║
// ║  │ 操作符    (1页)   │ get/set/plus/invoke/contains/compareTo          │     ║
// ║  │ 扩展函数  (1页)   │ fun Xxx.方法() / val Xxx.属性                    │     ║
// ║  │ 高阶函数  (1页)   │ 函数类型 / Lambda / SAM / 函数引用              │     ║
// ║  │ 集合操作  (1页)   │ map/filter/reduce/flatMap/groupBy/fold          │     ║
// ║  │ when表达式(1页)   │ 条件分支 / 无参when / 智能转换 / 多重条件       │     ║
// ║  │ 委托 by   (1页)   │ lazy/observable/vetoable/类委托/属性委托        │     ║
// ║  │ Flow      (1页)   │ StateFlow/SharedFlow/collect/combine            │     ║
// ║  └─────────────────┴──────────────────────────────────────────────────┘     ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
object KnowledgeData {

    /**
     * 所有知识页面列表（主页 RecyclerView 的数据源）
     *
     * 顺序即为列表显示顺序，先作用域函数，再 Object，再空安全，最后别名。
     */
    val allPages: List<KnowledgePage> get() = listOf(
        // ── 作用域函数（5 个独立页面） ──
        scopeLet,
        scopeAlso,
        scopeWith,
        scopeRun,
        scopeApply,
        // ── Object 三种用法（1 个页面，3 个 section） ──
        objectPage,
        // ── 空安全操作符（1 个页面，5 个 section） ──
        nullSafetyPage,
        // ── 类型别名（1 个页面，2 个 section） ──
        typeAliasPage,
        // ── 协程入门（1 个页面，5 个 section） ──
        coroutinePage,
        // ── 类修饰符（1 个页面，5 个 section） ──
        classModifiersPage,
        // ── 成员修饰符（1 个页面，2 个 section） ──
        memberModifiersPage,
        // ── 泛型修饰符（1 个页面，3 个 section） ──
        genericModifiersPage,
        // ── Kotlin 循环（1 个页面，7 个 section） ──
        loopsPage,
        // ── 密封类网络请求（1 个页面，3 个 section） ──
        sealedNetworkPage,
        // ── MVVM 真实 API Demo（点击进入实际可用的列表页） ──
        mvvmDemoPage,
        // ── 内联函数（1 个页面，4 个 section） ──
        inlineFunctionsPage,
        // ── 操作符重载（1 个页面，3 个 section） ──
        operatorPage,
        // ── 扩展函数与扩展属性（1 个页面，2 个 section） ──
        extensionFunctionsPage,
        // ── 高阶函数与 Lambda（1 个页面，4 个 section） ──
        higherOrderPage,
        // ── 集合操作函数（1 个页面，2 个 section） ──
        collectionOpsPage,
        // ── when 表达式（1 个页面，3 个 section） ──
        whenExpressionPage,
        // ── 委托 by（1 个页面，3 个 section） ──
        delegationPage,
        // ── Flow 响应式流（1 个页面，3 个 section） ──
        flowPage
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  作用域函数页面（保持独立，每个函数一个 page）
    // ═══════════════════════════════════════════════════════════════════════════

    val scopeLet = KnowledgePage(
        id = "let",
        category = "作用域函数",
        title = "let — 非空处理与链式转换",
        briefDesc = "使用 it 引用对象，返回 Lambda 结果，配合 ?. 实现优雅的非空处理",
        iconText = "let",
        iconColor = "#7B1FA2",
        overview = "let 是 Kotlin 中最常用的作用域函数之一。它是一个**扩展函数**，" +
                "在 Lambda 内部通过 **it** 引用调用对象，Lambda 最后一行的值作为返回值。",
        sections = listOf(
            ContentSection(
                title = "本质与用法",
                description = """
                    |let 接收一个 Lambda，在 Lambda 内 `it` 代表调用对象，
                    |Lambda **最后一行**的值 = 整个 let 表达式的返回值。
                    |
                    |核心优势：
                    |1. 配合 `?.` 安全调用 → 只在非空时执行
                    |2. `it` 可重命名为有意义的名字 → 提高可读性
                    |3. 返回 Lambda 结果 → 适合管道式数据转换
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  示例 1：可空处理（let 最经典的用法！）
                    |// ════════════════════════════════════════════════════
                    |
                    |val name: String? = "Kotlin"    // 可空类型
                    |
                    |// name 不为 null 时，let 才会执行
                    |val length = name?.let {
                    |    println("非空，值是: ${'$'}it")
                    |    it.length              // ← let 的返回值
                    |}
                    |println(length)  // 6
                    |
                    |// 对比传统写法：
                    |val len = if (name != null) name.length else null
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 2：it 重命名，提高可读性
                    |// ════════════════════════════════════════════════════
                    |
                    |data class User(val name: String, val age: Int)
                    |val user: User? = findUserById(1)
                    |
                    |user?.let { u ->           // u 比 it 更有意义
                    |    println(u.name)        // 一看就知道是 user
                    |    println(u.age)
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 3：链式数据转换（管道模式）
                    |// ════════════════════════════════════════════════════
                    |
                    |val nums = listOf(1, 2, 3, 4, 5, 6)
                    |val result = nums
                    |    .let { it.filter { n -> n > 3 } }  // → [4,5,6]
                    |    .let { it.sum() }                   // → 15
                    |println(result)  // 15
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 4：限定作用域，避免变量泄漏
                    |// ════════════════════════════════════════════════════
                    |
                    |java.io.File("test.txt").let { file ->
                    |    println(file.readText())
                    |}
                    |// file 在外面不可见，不污染外层命名空间
                    """.trimMargin(),
                keyPoints = listOf(
                    "用 `it` 引用上下文对象（可重命名为 `u ->`）",
                    "返回值 = Lambda 最后一行表达式的值",
                    "配合 `?.` 是「非空才执行」的最佳实践",
                    "适合数据转换管道",
                    "不遮蔽外部 `this`，嵌套时比 run 更安全"
                ),
                note = "用 let 而非 run，let 的 it 不遮蔽 this"
            )
        ),
        pageNote = """
            |## 🔗 关联知识点
            |👉 可空处理 `?.` → [空安全](app:nullsafety) 页面
            |👉 let 是高阶函数 + inline → [高阶函数](app:higher-order) / [inline](app:inline)
            |👉 let 是扩展函数 → [扩展函数](app:extension)
            |👉 与 run 的对比 → [run](app:run)
            """.trimMargin()
    )

    val scopeRun = KnowledgePage(
        id = "run",
        category = "作用域函数",
        title = "run — 对象配置 + 返回计算结果",
        briefDesc = "使用 this 引用对象，返回 Lambda 结果。兼具 with 的简洁和 let 的调用方式",
        iconText = "run",
        iconColor = "#283593",
        overview = "run 是 **with 和 let 的结合体**：像 with 一样用 `this` 访问成员，" +
                "像 let 一样是扩展函数可配合 `?.`。还有非扩展版本 `run { }`。",
        sections = listOf(
            ContentSection(
                title = "本质与用法",
                description = """
                    |run 适合**既要操作对象、又要返回一个计算结果**的场景。
                    |
                    |两种形态：
                    |1. **扩展 run**：`obj.run { ... }` — this 指向 obj
                    |2. **非扩展 run**：`run { ... }` — 无需对象，只执行代码块
                    |
                    |与 apply 的核心区别：
                    |- apply 返回**对象本身** → 用于初始化配置
                    |- run 返回 **Lambda 结果** → 用于计算并返回一个值
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  示例 1：对象配置 + 返回计算结果
                    |// ════════════════════════════════════════════════════
                    |
                    |data class Person(var name: String, var age: Int)
                    |
                    |val info = Person("张三", 20).run {
                    |    age += 1                      // this 省略
                    |    "${'$'}name 明年 ${'$'}age 岁"  // ← run 的返回值
                    |}
                    |println(info)  // 张三 明年 21 岁
                    |
                    |// 对比 apply：
                    |val p = Person("张三", 20).apply { age += 1 }
                    |// p 是 Person 对象，不是字符串！
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 2：配合 ?. 安全调用（with 做不到！）
                    |// ════════════════════════════════════════════════════
                    |
                    |val user: User? = findUser()
                    |val greeting = user?.run {
                    |    "你好，${'$'}name！"
                    |} ?: "用户不存在"
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 3：非扩展 run — 多语句算一个值
                    |// ════════════════════════════════════════════════════
                    |
                    |val rectInfo = run {
                    |    val w = 100; val h = 200
                    |    val area = w * h
                    |    "${'$'}w×${'$'}h = ${'$'}area px²"
                    |}
                    |// 中间变量 w, h, area 不会泄漏到外层！
                    """.trimMargin(),
                keyPoints = listOf(
                    "用 `this` 引用对象（可省略），直接访问成员",
                    "返回值 = Lambda 最后一行",
                    "有扩展版和非扩展版两种",
                    "扩展版可配合 `?.` 安全调用",
                    "= with 的扩展版 + let 的调用方式"
                ),
                note = "嵌套 run 时 `this` 容易混淆。需要访问外层 this 时用 `this@OuterClass` 显式指定。"
            )
        ),
        pageNote = """
            |## 🔗 关联知识点
            |👉 run 是扩展 with → [with](app:with) 页面
            |👉 配合 `?.` 安全调用 → [空安全](app:nullsafety)
            |👉 `run { }` 非扩展版 → [高阶函数](app:higher-order)
            |👉 协程中的 `viewModelScope.launch { }` → [协程](app:coroutine)
            """.trimMargin()
    )

    val scopeWith = KnowledgePage(
        id = "with",
        category = "作用域函数",
        title = "with — 批量操作同一个对象",
        briefDesc = "非扩展函数，传入对象用 this 访问，返回 Lambda 结果。适合批量操作同一对象",
        iconText = "with",
        iconColor = "#00695C",
        overview = "with 是唯一的**非扩展函数**作用域函数。将对象作为参数传入 `with(obj) { ... }`，" +
                "块内 `this` 指向该对象，适合**批量操作同一个对象**。",
        sections = listOf(
            ContentSection(
                title = "本质与用法",
                description = """
                    |`with(obj) { ... }` 读作「**用**这个对象做以下这些事」。
                    |
                    |块内 `this` 指向 obj，所有成员可直接访问。
                    |
                    |与 run 的核心区别：
                    |- with 不是扩展函数 → **不能配合 ?. 安全调用**
                    |- run 是扩展函数 → 可以 `obj?.run { }`
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  示例 1：批量操作同一个对象
                    |// ════════════════════════════════════════════════════
                    |
                    |val list = mutableListOf(10, 20, 30)
                    |with(list) {
                    |    add(40)                  // = list.add(40)
                    |    add(50)                  // = list.add(50)
                    |    removeAt(0)              // = list.removeAt(0)
                    |    println("元素: ${'$'}{joinToString()}")
                    |    println("和: ${'$'}{sum()}")
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 2：利用对象构建结果
                    |// ════════════════════════════════════════════════════
                    |
                    |data class Rect(val w: Int, val h: Int)
                    |val r = Rect(100, 200)
                    |val desc = with(r) {
                    |    "${'$'}w × ${'$'}h = ${'$'}{w * h} px²"
                    |}
                    |println(desc) // 100 × 200 = 20000 px²
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 3：Adapter 中的实际应用
                    |// ════════════════════════════════════════════════════
                    |
                    |// with(holder.binding) {
                    |//     tvTitle.text = item.title
                    |//     tvDesc.text = item.desc
                    |// }
                    """.trimMargin(),
                keyPoints = listOf(
                    "唯一非扩展函数，对象作为参数传入",
                    "用 `this` 引用（可省略）",
                    "返回值 = Lambda 最后一行",
                    "语义：`with(obj)` = 「用这个对象做...」",
                    "不能配合 `?.` → 可空时用 run 代替"
                ),
                note = "with 不能配合 ?. 安全调用。obj 如果是可空类型，先 ?.let { } 再 with 太啰嗦，直接用 obj?.run { }。"
            )
        ),
        pageNote = """
            |## 🔗 关联知识点
            |👉 with 不能配合 `?.` → 可空时用 [run](app:run)
            |👉 块内 this 可省略 → [高阶函数](app:higher-order) 带接收者的 Lambda
            |👉 with 本身是 inline → [内联函数](app:inline)
            """.trimMargin()
    )

    val scopeApply = KnowledgePage(
        id = "apply",
        category = "作用域函数",
        title = "apply — 对象初始化配置",
        briefDesc = "用 this 引用，返回对象本身。专为对象初始化而生，Kotlin 的 Builder 模式",
        iconText = "app",
        iconColor = "#E65100",
        overview = "apply 是对象初始化的专属函数。返回**对象本身**（非 Lambda 结果），" +
                "适合「把以下属性应用到对象上」。",
        sections = listOf(
            ContentSection(
                title = "本质与用法",
                description = """
                    |apply 返回的是**对象本身**，这是它与 let/run/with 最根本的区别。
                    |
                    |设计哲学：**"将以下属性赋值应用到对象上"**
                    |
                    |因为返回对象本身，所以可以无限链式调用。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  示例 1：对象初始化（替代 Builder）
                    |// ════════════════════════════════════════════════════
                    |
                    |data class Person(
                    |    var name: String = "",
                    |    var age: Int = 0,
                    |    var email: String = ""
                    |)
                    |val p = Person().apply {
                    |    name = "李四"
                    |    age = 25
                    |    email = "lisi@qq.com"
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 2：Android Intent 配置
                    |// ════════════════════════════════════════════════════
                    |
                    |val intent = Intent(ctx, DetailActivity::class.java)
                    |    .apply {
                    |        putExtra("id", userId)
                    |        putExtra("title", "详情")
                    |    }
                    |startActivity(intent)
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 3：UI 控件配置
                    |// ════════════════════════════════════════════════════
                    |
                    |val tv = TextView(context).apply {
                    |    text = "Hello Kotlin"
                    |    textSize = 16f
                    |    setTextColor(Color.BLACK)
                    |}
                    |// tv 就是配置好的控件
                    |
                    |
                    |// ⚠️ 常见陷阱：
                    |val wrong = Person().apply {
                    |    name = "张三"
                    |    "这是返回值"  // ← 被忽略！apply 不返回这个
                    |}
                    |// wrong 仍是 Person 对象，不是字符串！
                    """.trimMargin(),
                keyPoints = listOf(
                    "用 `this` 引用对象（可省略）",
                    "⭐ 返回对象本身（不是 Lambda 最后一行！）",
                    "专为「对象初始化配置」设计",
                    "链式友好：`.apply{ }.apply{ }` 链到底",
                    "语义：读作「将以下属性应用到对象上」"
                ),
                note = "需要配置对象 + 返回计算结果 → 用 run。\n需要配置对象 + 顺便打日志 → apply + also 链。"
            )
        ),
        pageNote = """
            |## 🔗 关联知识点
            |👉 apply 返回对象本身 vs run 返回 Lambda 结果 → [run](app:run)
            |👉 apply 用 this vs also 用 it → [also](app:also)
            |👉 apply 是扩展函数 + inline → [扩展函数](app:extension) / [inline](app:inline)
            |👉 MVVM Demo 中大量用 apply → [MVVM Demo](app:mvvm-demo)
            """.trimMargin()
    )

    val scopeAlso = KnowledgePage(
        id = "also",
        category = "作用域函数",
        title = "also — 附加副作用操作（日志/验证）",
        briefDesc = "用 it 引用，返回对象本身。适合日志、验证等不修改对象的附加操作",
        iconText = "als",
        iconColor = "#880E4F",
        overview = "also 和 apply 一样返回**对象本身**，但用 `it` 引用。适合**不改变对象的附加操作**：" +
                "日志、验证、赋值给外部变量。",
        sections = listOf(
            ContentSection(
                title = "本质与用法",
                description = """
                    |also 的设计哲学：**"并且用这个对象做以下这件事"**
                    |
                    |与 apply 的区别：
                    |- apply 用 `this` → 适合修改对象属性
                    |- also 用 `it` → 适合不修改对象的附加操作
                    |
                    |与 let 的区别：
                    |- let 返回 Lambda 结果，also 返回对象本身
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  示例 1：日志打印（最经典！）
                    |// ════════════════════════════════════════════════════
                    |
                    |val user = User("王五", 30).also {
                    |    println("创建用户: ${'$'}it")
                    |}
                    |// user 仍然是 User("王五", 30)
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 2：数据验证
                    |// ════════════════════════════════════════════════════
                    |
                    |fun createUser(name: String, age: Int) = User(name, age).also {
                    |    require(it.name.isNotBlank()) { "名字不能空" }
                    |    require(it.age in 1..150) { "年龄非法" }
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 3：链式调试检查点
                    |// ════════════════════════════════════════════════════
                    |
                    |val result = mutableListOf(1, 2, 3)
                    |    .also { println("① 初始: ${'$'}it") }
                    |    .apply { add(4) }
                    |    .also { println("② 添加: ${'$'}it") }
                    |    .apply { removeAt(0) }
                    |    .also { println("③ 删除: ${'$'}it") }
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 4：赋值给外部变量
                    |// ════════════════════════════════════════════════════
                    |
                    |var latestUser: User? = null
                    |val newUser = User("赵六", 28).also {
                    |    latestUser = it  // 顺便保存引用
                    |}
                    """.trimMargin(),
                keyPoints = listOf(
                    "用 `it` 引用对象（不遮蔽外部 `this`）",
                    "⭐ 返回对象本身",
                    "适合不改变对象的附加操作：日志、验证、调试",
                    "与 apply 区别：also 用 it，apply 用 this",
                    "链式调试：`.also { println(it) }` 插入检查点"
                ),
                note = "在 Fragment/Activity 中需要访问 this → 用 also 而非 apply，因为 also 的 it 不遮蔽 this。"
            )
        ),
        pageNote = """
            |## 🔗 关联知识点
            |👉 also 返回对象本身 vs let 返回 Lambda 结果 → [let](app:let)
            |👉 also 用 it 不会遮蔽外部 this → [空安全](app:nullsafety)
            |👉 链式调试 `.also { print(it) }` → [集合操作](app:collection-ops)
            """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  Object 三种用法（1 个页面，3 个 section）
    // ═══════════════════════════════════════════════════════════════════════════

    val objectPage = KnowledgePage(
        id = "object",
        category = "Object",
        title = "object — 单例 / 伴生对象 / 匿名对象 三种用法",
        briefDesc = "三种用法：单例声明、伴生对象、匿名对象表达式。一页搞懂 object 的所有用法",
        iconText = "obj",
        iconColor = "#1565C0",
        overview = """
            |Kotlin 的 `object` 关键字有三种完全不同的用法，但核心思想一致：
            |**定义一个类的同时创建一个实例**。
            |
            || 用法 | 关键词 | 等价 Java 概念 |
            ||------|--------|---------------|
            || 对象声明 | `object 名称 { }` | 单例（Singleton） |
            || 伴生对象 | `companion object { }` | 静态成员容器 |
            || 对象表达式 | `object : 接口 { }` | 匿名内部类 |
        """.trimMargin(),
        sections = listOf(

            // ── Section 1：对象声明（单例） ──
            ContentSection(
                title = "① 对象声明 — 单例模式",
                description = """
                    |`object` 关键字声明一个**类并同时创建它的唯一实例**。
                    |这是 Kotlin 实现单例模式的最简单方式。
                    |
                    |**特点：**
                    |- 线程安全（JVM 类加载时初始化，天然线程安全）
                    |- 懒加载（首次访问时才初始化）
                    |- 可以有属性、方法、初始化块
                    |- 可以继承类和实现接口
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  基础用法：最简单的单例
                    |// ════════════════════════════════════════════════════
                    |
                    |object AppConfig {
                    |    // 属性
                    |    var baseUrl: String = "https://api.example.com"
                    |    var timeout: Int = 30
                    |    val version: String = "1.0.0"
                    |
                    |    // 方法
                    |    fun printConfig() {
                    |        println("API: ${'$'}baseUrl, 超时: ${'$'}timeout")
                    |    }
                    |}
                    |
                    |// 使用：直接通过 object 名访问
                    |AppConfig.baseUrl = "https://new-api.com"
                    |AppConfig.printConfig()
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  进阶：object 继承类 / 实现接口
                    |// ════════════════════════════════════════════════════
                    |
                    |interface OnClickListener {
                    |    fun onClick()
                    |}
                    |
                    |object MyClickHandler : OnClickListener {
                    |    override fun onClick() {
                    |        println("被点击了！")
                    |    }
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  对比：同样的功能在 Java 中需要多少代码？
                    |// ════════════════════════════════════════════════════
                    |
                    |// Java 版本（对比用，不用写）：
                    |// public class AppConfig {
                    |//     private static AppConfig INSTANCE;
                    |//     private AppConfig() {}
                    |//     public static AppConfig getInstance() {
                    |//         if (INSTANCE == null) {
                    |//             synchronized (...) { ... }
                    |//         }
                    |//         return INSTANCE;
                    |//     }
                    |// }
                    |//
                    |// Kotlin：一行 object AppConfig 搞定！
                    """.trimMargin(),
                keyPoints = listOf(
                    "`object` 声明一个类的同时创建唯一实例 → 天然单例",
                    "线程安全：利用 JVM 类加载机制，无需加锁",
                    "可继承、可实现接口",
                    "访问方式：`类名.成员`（不需要 getInstance()）",
                    "初始化时机：首次访问时（懒加载）"
                ),
                note = "object 不能有构造函数（因为由 Kotlin 自动创建实例）。如果需要传参构造，考虑用 class + 伴生对象 + 工厂方法。"
            ),

            // ── Section 2：伴生对象（companion object） ──
            ContentSection(
                title = "② 伴生对象 — 类级别的静态成员",
                description = """
                    |Kotlin 没有 `static` 关键字。类的「静态成员」通过 **伴生对象** 实现。
                    |
                    |一个类最多只能有**一个**伴生对象，声明在类内部。
                    |
                    |**特点：**
                    |- 伴生对象的成员可以通过「类名.成员」直接访问
                    |- 伴生对象本身也是一个对象，可以有名字（默认 Companion）
                    |- 可以实现接口
                    |- 编译后就是类的静态成员
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  基础用法：替代 Java 的 static
                    |// ════════════════════════════════════════════════════
                    |
                    |class MyClass {
                    |    // 实例属性
                    |    var instanceName: String = "实例"
                    |
                    |    // 伴生对象 — 静态成员容器
                    |    companion object {
                    |        // 相当于 Java 的 public static final String TAG
                    |        const val TAG = "MyClass"
                    |
                    |        // 相当于 Java 的 static 变量
                    |        var count: Int = 0
                    |
                    |        // 相当于 Java 的 static 方法
                    |        fun create(): MyClass {
                    |            count++
                    |            return MyClass()
                    |        }
                    |    }
                    |}
                    |
                    |// 使用：类名直接访问（像 Java static 一样）
                    |println(MyClass.TAG)        // MyClass
                    |MyClass.count = 5
                    |val obj = MyClass.create()
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  伴生对象可以有名字
                    |// ════════════════════════════════════════════════════
                    |
                    |class Database {
                    |    companion object Factory {   // 命名为 Factory
                    |        fun create(): Database = Database()
                    |    }
                    |}
                    |// 两种调用方式等价：
                    |Database.create()           // 省略伴生对象名
                    |Database.Factory.create()   // 显式写伴生对象名
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  伴生对象实现接口
                    |// ════════════════════════════════════════════════════
                    |
                    |interface Factory<T> {
                    |    fun create(): T
                    |}
                    |class User {
                    |    companion object : Factory<User> {
                    |        override fun create() = User()
                    |    }
                    |}
                    |// 可以把伴生对象当作 Factory 传参！
                    |fun <T> make(factory: Factory<T>): T = factory.create()
                    |val user = make(User)  // User 的伴生对象实现了 Factory
                    """.trimMargin(),
                keyPoints = listOf(
                    "Kotlin 没有 static 关键字 → 用 `companion object` 代替",
                    "一个类最多一个伴生对象",
                    "成员通过 `类名.成员` 访问（像 Java static）",
                    "`const val` = 编译期常量（等价 Java static final）",
                    "伴生对象可有名字、可实现接口",
                    "本质仍是一个 object（单例），但寄宿在类内部"
                ),
                note = "伴生对象成员 != 真正的 Java static。在 Java 中调用 Kotlin 伴生对象需要用 MyClass.Companion.method()，除非加 @JvmStatic 注解。"
            ),

            // ── Section 3：对象表达式（匿名对象） ──
            ContentSection(
                title = "③ 对象表达式 — 匿名内部类",
                description = """
                    |`object : 类型 { }` 创建一个**匿名对象**，等价于 Java 的匿名内部类。
                    |
                    |可以继承类、实现接口、修改成员。常用于设置监听器、临时的接口实现等。
                    |
                    |**与 Java 匿名内部类的区别：**
                    |- Kotlin 的 object 表达式可以实现多个接口
                    |- 可以访问并修改外部变量（不需要 final）
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  基础用法：实现接口的匿名对象
                    |// ════════════════════════════════════════════════════
                    |
                    |interface OnClickListener {
                    |    fun onClick()
                    |    fun onLongClick(): Boolean = false  // 默认实现
                    |}
                    |
                    |// 创建匿名对象
                    |val listener = object : OnClickListener {
                    |    override fun onClick() {
                    |        println("匿名对象被点击！")
                    |    }
                    |}
                    |listener.onClick()
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  Android 中最常见的用法 — 设置监听器
                    |// ════════════════════════════════════════════════════
                    |
                    |button.setOnClickListener(object : View.OnClickListener {
                    |    override fun onClick(v: View?) {
                    |        println("按钮被点击")
                    |    }
                    |})
                    |
                    |// ⬆️ 等价 Java 写法（Kotlin 可以用 Lambda 简化）：
                    |button.setOnClickListener { println("按钮被点击") }
                    |// 前提：接口只有一个抽象方法（SAM 转换）
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  匿名对象访问 / 修改外部变量
                    |// ════════════════════════════════════════════════════
                    |
                    |var counter = 0    // 普通变量，不是 final！
                    |val handler = object : Runnable {
                    |    override fun run() {
                    |        counter++   // ← 可以修改外部变量！
                    |        println("执行了 ${'$'}counter 次")
                    |    }
                    |}
                    |// Java 中 counter 必须声明为 final
                    |// Kotlin 无此限制
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  匿名对象可以直接作为表达式返回
                    |// ════════════════════════════════════════════════════
                    |
                    |fun createPrinter(prefix: String) = object {
                    |    fun print(msg: String) {
                    |        println("${'$'}prefix: ${'$'}msg")
                    |    }
                    |}
                    |val printer = createPrinter("LOG")
                    |printer.print("Hello")   // 输出：LOG: Hello
                    """.trimMargin(),
                keyPoints = listOf(
                    "`object : 类型 { }` = 匿名对象 = Java 的匿名内部类",
                    "可以实现多个接口",
                    "可以访问并修改外部变量（不需要 final）",
                    "SAM 接口（单抽象方法）可用 Lambda 简写",
                    "可直接作为返回值或函数参数"
                ),
                note = "优先用 Lambda，复杂用 object 表达式"
            )
        ),
        pageNote = """
            |总结三种用法的记忆口诀：
            |- **object 名称 { }** → 单例 → 全局唯一 → 像「公司只有一个 CEO」
            |- **companion object { }** → 静态成员 → 属于类不属对象 → 像「人类的定义不属于某个人」
            |- **object : 类型 { }** → 匿名对象 → 临时一次性 → 像「匿名信」
            |
            |## 🔗 关联知识点
            |👉 `companion object` 替代 Java static → [成员修饰符](app:member-modifiers)
            |👉 `object : Interface` 配合 SAM 转换 → [高阶函数](app:higher-order)
            |👉 object 配合 sealed class → [sealed class 实战](app:sealed-network)
            |👉 单例 object 实现 RetrofitClient → [MVVM Demo](app:mvvm-demo)
            """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  空安全操作符（1 个页面，5 个 section）
    // ═══════════════════════════════════════════════════════════════════════════

    val nullSafetyPage = KnowledgePage(
        id = "nullsafety",
        category = "空安全",
        title = "空安全 — ? / ?. / ?: / !! / as? 五大操作符",
        briefDesc = "?  ?.  ?:  !!  as? — Kotlin 空安全五大操作符，从可空声明到安全转换",
        iconText = "?",
        iconColor = "#D84315",
        overview = """
            |Kotlin 从类型系统层面解决了空指针问题。以下是五大空安全操作符的关系：
            |
            || 操作符 | 名称 | 一句话 | 会抛 NPE 吗？ |
            ||--------|------|--------|--------------|
            || `?` | 可空类型 | 声明变量可为 null | ❌ |
            || `?.` | 安全调用 | 非空才执行 | ❌ |
            || `?:` | Elvis | 为 null 时取默认值 | ❌ |
            || `!!` | 非空断言 | "我保证不为 null" | ✅ 会！ |
            || `as?` | 安全转换 | 转换失败返回 null | ❌ |
        """.trimMargin(),
        sections = listOf(

            // ── Section 1：? 可空类型 ──
            ContentSection(
                title = "① ? — 可空类型声明",
                description = """
                    |`?` 放在类型后面，表示「这个变量可以持有 null」。
                    |
                    |默认情况下，Kotlin 的变量**不能为 null**（和 Java 完全不同！）。
                    |只有显式加了 `?` 的类型才能赋值为 null。
                    |
                    |这是 Kotlin 空安全体系的基础——类型系统在编译期就区分了「可空」和「不可空」。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  不可空 vs 可空 — 编译期检查！
                    |// ════════════════════════════════════════════════════
                    |
                    |var name: String = "Kotlin"
                    |// name = null   ← ❌ 编译错误！String 不可为空
                    |
                    |var nickname: String? = "Kotlin"
                    |nickname = null       // ✅ String? 可以为 null
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  不可空类型的优势：放心调用，不会 NPE
                    |// ════════════════════════════════════════════════════
                    |
                    |val len1 = name.length   // ✅ 安全，name 绝不为 null
                    |// val len2 = nickname.length  ← ❌ 编译错误！
                    |//   可空类型不能直接访问成员，
                    |//   必须先用 ?. 或 !! 处理
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  常见场景：函数参数 / 返回值
                    |// ════════════════════════════════════════════════════
                    |
                    |fun findUser(id: Int): User? {   // 可能找不到，返回 null
                    |    return if (id > 0) User("张三") else null
                    |}
                    |
                    |// 类型后加 ? 就是告诉调用者：这个值可能是 null，请小心处理
                    """.trimMargin(),
                keyPoints = listOf(
                    "Kotlin 类型默认**不可空**，`String` ≠ Java 的 `String`（Java 默认可空）",
                    "`类型后 + ?` = 可空类型 = `String?` = 可以是 String 也可以是 null",
                    "可空类型不能直接调用方法/属性，必须先做空检查",
                    "编译期强制检查 → 把 NPE 消灭在编译阶段"
                )
            ),

            // ── Section 2：?. 安全调用 ──
            ContentSection(
                title = "② ?. — 安全调用操作符",
                description = """
                    |`?.` 是最常用的空安全操作符。它的含义是：
                    |**「如果左边不为 null，就调用右边的方法/属性；如果为 null，整个表达式返回 null」**
                    |
                    |可以链式使用 `a?.b?.c?.d`，任何一个环节为 null，整个链返回 null。
                    |常与 `let` 配合使用：`obj?.let { ... }`。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  基本用法
                    |// ════════════════════════════════════════════════════
                    |
                    |val name: String? = "Kotlin"
                    |val length: Int? = name?.length
                    |// name 不为 null → length = 6
                    |
                    |val nothing: String? = null
                    |val len2: Int? = nothing?.length
                    |// nothing 为 null → len2 = null（不抛 NPE！）
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  链式安全调用
                    |// ════════════════════════════════════════════════════
                    |
                    |data class Address(val city: String?)
                    |data class User(val address: Address?)
                    |
                    |val user: User? = User(Address("北京"))
                    |
                    |// 最安全的深层访问：任何一环为 null 都不抛 NPE
                    |val city = user?.address?.city
                    |println(city)  // 北京
                    |
                    |val noAddr = User(null)
                    |println(noAddr?.address?.city)  // null（安全！）
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  结合 let 做非空处理
                    |// ════════════════════════════════════════════════════
                    |
                    |val msg: String? = "Hello"
                    |msg?.let {
                    |    println("长度: ${'$'}{it.length}")  // 非空才执行
                    |}
                    """.trimMargin(),
                keyPoints = listOf(
                    "`a?.b` = 如果 a 不为 null 则取 a.b，否则返回 null",
                    "返回值类型自动变为可空：`String?.length` → `Int?`",
                    "支持链式：`a?.b?.c?.d`，任意一环为 null 则整体为 null",
                    "常与 `let` 搭配：`obj?.let { ... }`"
                )
            ),

            // ── Section 3：?: Elvis 操作符 ──
            ContentSection(
                title = "③ ?: — Elvis 操作符（空值兜底）",
                description = """
                    |`?:` 的含义是：**「如果左边为 null，就取右边的值」**。
                    |
                    |名字由来：顺时针旋转 90° 看 `?:`，像猫王的发型 + 眼睛 👀（Elvis Presley）。
                    |
                    |这是 Kotlin 中最常用的「提供默认值」方式，替代了 Java 的三元运算符。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  基础用法：null 时给默认值
                    |// ════════════════════════════════════════════════════
                    |
                    |val name: String? = null
                    |val displayName: String = name ?: "匿名用户"
                    |println(displayName)   // 匿名用户
                    |
                    |val name2: String? = "Kotlin"
                    |val display2 = name2 ?: "匿名用户"
                    |println(display2)      // Kotlin
                    |
                    |// 注意：?: 右边可以是任意表达式，不限于常量
                    |val result = data ?: loadFromNetwork() ?: "加载失败"
                    |//          ↑ 尝试   ↑ 第一备选       ↑ 最终兜底
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  ?. 和 ?: 的经典组合
                    |// ════════════════════════════════════════════════════
                    |
                    |data class User(val name: String?, val age: Int?)
                    |
                    |fun printUser(user: User?) {
                    |    // ?. ?: 组合：安全取值 + 默认兜底
                    |    val name = user?.name ?: "未知"
                    |    val age  = user?.age  ?: 0
                    |    println("${'$'}name, ${'$'}age 岁")
                    |}
                    |printUser(null)        // 未知, 0 岁
                    |printUser(User("张三", null)) // 张三, 0 岁
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  ?: 配合 return / throw 提前退出
                    |// ════════════════════════════════════════════════════
                    |
                    |fun requireInput(text: String?): String {
                    |    return text ?: throw IllegalArgumentException("不能为空")
                    |}
                    |// 等价于 if (text == null) throw ...
                    """.trimMargin(),
                keyPoints = listOf(
                    "`a ?: b` = 如果 a 不为 null 取 a，否则取 b",
                    "经常与 `?.` 组合：`obj?.prop ?: defaultValue`",
                    "可以链式：`a ?: b ?: c ?: d`，取第一个非空值",
                    "右侧可以是任何表达式（return、throw、函数调用等）",
                    "比 Java 三元表达式更简洁、更安全"
                )
            ),

            // ── Section 4：!! 非空断言 ──
            ContentSection(
                title = "④ !! — 非空断言操作符",
                description = """
                    |`!!` 的含义是：**「我**确定**这个值不为 null，把它当非空类型用！」**
                    |
                    |如果实际为 null → 立刻抛出 `NullPointerException`。
                    |
                    |⚠️ `!!` 是危险的信号——使用它意味着你在手动关闭 Kotlin 的空安全检查。
                    |过度使用是代码坏味道，应优先考虑 `?.`、`?:`、`let` 等安全方式。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  !! 的基本行为
                    |// ════════════════════════════════════════════════════
                    |
                    |val name: String? = "Kotlin"
                    |val len: Int = name!!.length  // ✅ name 非空，OK
                    |// 注意返回类型：len 是 Int（非 Int?），因为 !! 去掉了可空
                    |
                    |val nothing: String? = null
                    |// val crash = nothing!!.length  // 💥 NPE！
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  !! 的合理使用场景（少数情况）
                    |// ════════════════════════════════════════════════════
                    |
                    |// 场景 1：你已验证过非空，但编译器无法推断
                    |val list = listOf(1, 2, null, 4).filterNotNull()
                    |// 编译器仍认为 list 是 List<Int?>，但我们知道已经过滤了
                    |val first: Int = list.first()  // ❌ 类型不匹配
                    |// 用 !! 告诉编译器"我知道没有 null"
                    |
                    |// 更安全的替代方案：
                    |val firstSafe: Int? = list.firstOrNull()  // 不用 !!
                    |
                    |
                    |// 场景 2：测试代码中（测试失败就该崩！）
                    |@Test
                    |fun test() {
                    |    val result: String? = someFunction()
                    |    assertEquals("expected", result!!)
                    |    // 测试中 result 为 null 意味着测试失败 → NPE 合理
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  ❌ !! 的坏味道用法 — 应该避免
                    |// ════════════════════════════════════════════════════
                    |
                    |// Bad：能用 ?. 为什么用 !!？
                    |val bad = user!!.name!!.length    // 随时可能崩
                    |
                    |// Good：用安全操作符替代
                    |val good = user?.name?.length ?: 0
                    """.trimMargin(),
                keyPoints = listOf(
                    "`x!!` = 把可空类型强制转成不可空，null 时抛 NPE",
                    "⚠️ 危险信号 → 优先用 `?.`、`?:`、`let` 等安全方式",
                    "合理场景很少：已验证非空但编译器不知道、测试代码",
                    "链式 `!!` 是代码坏味道 → 重构为安全调用链",
                    "代码审查中看到 `!!` → 停下来问：能否用安全方式代替？"
                ),
                note = "经验法则：生产代码中看到 `!!` 就该警惕。如果确实需要，加注释说明为什么这里一定不为 null。"
            ),

            // ── Section 5：as? 安全类型转换 ──
            ContentSection(
                title = "⑤ as? — 安全类型转换",
                description = """
                    |`as?` 的含义是：**「尝试转换为目标类型，如果失败则返回 null」**
                    |
                    |对比：
                    |- `as` → 强制转换，失败抛 `ClassCastException`
                    |- `as?` → 安全转换，失败返回 `null`
                    |
                    |`as?` 常与 `?.`、`?:`、`let` 组合使用，形成安全的类型判断模式。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  as vs as? 行为对比
                    |// ════════════════════════════════════════════════════
                    |
                    |val obj: Any = "Hello"
                    |
                    |val s: String = obj as String     // ✅ 转换成功
                    |// val n: Int = obj as Int         // 💥 ClassCastException
                    |
                    |val s2: String? = obj as? String   // ✅ → "Hello"
                    |val n2: Int?    = obj as? Int      // ✅ → null（不抛异常！）
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  as? 经典组合：as? + ?. + ?:
                    |// ════════════════════════════════════════════════════
                    |
                    |fun printLength(obj: Any) {
                    |    val length = (obj as? String)
                    |        ?.length          // 是 String 才取 length
                    |        ?: 0              // 否则返回 0
                    |    println("长度: ${'$'}length")
                    |}
                    |
                    |printLength("Kotlin")  // 长度: 6
                    |printLength(123)       // 长度: 0
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  as? + let 组合：if (obj is Type) 的替代
                    |// ════════════════════════════════════════════════════
                    |
                    |fun handle(obj: Any) {
                    |    // 方式 1：传统的 is 判断
                    |    if (obj is String) {
                    |        println(obj.uppercase())
                    |    }
                    |
                    |    // 方式 2：as? + ?.let（函数式风格）
                    |    (obj as? String)?.let { s ->
                    |        println(s.uppercase())
                    |    }
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  when + as? 模式匹配
                    |// ════════════════════════════════════════════════════
                    |
                    |fun describe(obj: Any): String {
                    |    return when {
                    |        (obj as? Int) != null    -> "整数"
                    |        (obj as? String) != null -> "字符串"
                    |        (obj as? List<*>) != null -> "列表"
                    |        else                     -> "未知类型"
                    |    }
                    |}
                    """.trimMargin(),
                keyPoints = listOf(
                    "`as?` = 尝试转换，失败返回 null（不抛异常）",
                    "`as` = 强制转换，失败抛 ClassCastException",
                    "返回类型是 `目标类型?`（可空）",
                    "常与 `?.` `?:` `let` 组合使用",
                    "配合 when 可实现类型模式匹配"
                ),
                note = "能用 as? 就不用 as，能用 is 智能转换就不用 as?"
            )
        ),
        pageNote = """
            |## ⭐ 空安全决策速查
            |
            || 你想... | 用什么 |
            ||---------|--------|
            || 允许变量为 null | `var x: Type? = null` |
            || 非空才调用 | `x?.method()` |
            || null 时给默认值 | `x ?: defaultValue` |
            || 我确定非空，别管了 | `x!!`（少用！） |
            || 安全类型转换 | `x as? Type` |
            |
            |## 🔗 关联知识点
            |👉 `?.let { }` 经典模式 → [let](app:let)
            |👉 `!!` vs `lateinit` → [成员修饰符](app:member-modifiers)
            |👉 `as?` 配合 `when` 类型判断 → [when 表达式](app:when-expr)
            |👉 `?:` 与 sealed class Error 分支 → [sealed class 实战](app:sealed-network)
            """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  类型别名（1 个页面，2 个 section）
    // ═══════════════════════════════════════════════════════════════════════════

    val typeAliasPage = KnowledgePage(
        id = "typealias",
        category = "类型别名",
        title = "类型别名 — typealias 项目级别名 / import as 文件级别名",
        briefDesc = "给复杂类型起个短名字：typealias 类型别名 + import as 导入别名",
        iconText = "as",
        iconColor = "#2E7D32",
        overview = """
            |Kotlin 提供两种「起别名」的方式：
            |
            || 方式 | 语法 | 作用范围 | 典型用途 |
            ||------|------|---------|---------|
            || `typealias` | `typealias 新名 = 原名` | 整个项目 | 给复杂的泛型类型起短名 |
            || `import ... as ...` | `import 包.类 as 别名` | 当前文件 | 解决类名冲突 / 简化调用 |
        """.trimMargin(),
        sections = listOf(

            // ── Section 1：typealias 类型别名 ──
            ContentSection(
                title = "① typealias — 类型别名",
                description = """
                    |`typealias` 给已有类型起一个新名字。**不创建新类型**，只是别名。
                    |
                    |最常用的场景：
                    |- 给复杂的泛型类型起短名（如回调函数类型、集合类型）
                    |- 提高代码可读性：用有意义的别名替代裸类型
                    |- 缩短长类型名
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  场景 1：简化复杂的函数类型
                    |// ════════════════════════════════════════════════════
                    |
                    |// 不用 typealias — 类型签名又长又难读：
                    |fun handleResult(
                    |    callback: (Result<User>, Throwable?) -> Unit
                    |) { ... }
                    |
                    |// 用 typealias — 一看就懂：
                    |typealias UserCallback = (Result<User>, Throwable?) -> Unit
                    |
                    |fun handleResult(callback: UserCallback) { ... }
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  场景 2：简化嵌套泛型集合
                    |// ════════════════════════════════════════════════════
                    |
                    |// 不用 typealias — 嵌套泛型让人头疼：
                    |val cache: Map<String, List<Pair<Int, String>>>
                    |
                    |// 用 typealias — 语义清晰：
                    |typealias TagList = List<Pair<Int, String>>
                    |typealias Cache   = Map<String, TagList>
                    |val cache: Cache
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  场景 3：Android 开发常见用法
                    |// ════════════════════════════════════════════════════
                    |
                    |typealias OnItemClick = (position: Int, item: Any) -> Unit
                    |
                    |class MyAdapter(
                    |    private val onItemClick: OnItemClick  // 清晰！
                    |) : RecyclerView.Adapter<...>()
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  场景 4：给内联类起短名
                    |// ════════════════════════════════════════════════════
                    |
                    |typealias FileTable = MutableMap<String, MutableList<File>>
                    |
                    |fun FileTable.findByName(name: String): List<File> {
                    |    return this[name] ?: emptyList()
                    |}
                    |// FileTable 作为接收者类型 → 语义明确的扩展函数
                    """.trimMargin(),
                keyPoints = listOf(
                    "`typealias 别名 = 原类型` — 不创建新类型，只是别名",
                    "编译后别名被替换为原类型（零运行时开销）",
                    "主要用于提高可读性，缩短复杂泛型签名",
                    "可用于函数类型、集合类型、嵌套泛型等",
                    "适用于整个项目的任意文件（只要 import 对应的包）"
                ),
                note = "typealias 不产生新类型。需真正新类型用 value class"
            ),

            // ── Section 2：import alias 导入别名 ──
            ContentSection(
                title = "② import as — 导入别名",
                description = """
                    |`import ... as ...` 给导入的类/函数起别名。
                    |
                    |主要用途：
                    |- **解决类名冲突**：不同包的同名类
                    |- **简化调用**：给长类名起短名
                    |
                    |作用范围：**仅限当前文件**，不会影响其他文件。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  场景 1：解决类名冲突（最常见！）
                    |// ════════════════════════════════════════════════════
                    |
                    |// 两个不同库各有一个 Date 类，都需要用到
                    |import java.util.Date as JavaDate
                    |import java.sql.Date as SqlDate
                    |
                    |// 现在可以同时使用，不冲突：
                    |val now: JavaDate = JavaDate()   // java.util.Date
                    |val sql: SqlDate = SqlDate(now.time) // java.sql.Date
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  场景 2：给长类名起短名
                    |// ════════════════════════════════════════════════════
                    |
                    |import com.example.very.long.package.path.DeeplyNestedUtil
                    |    as Util  // 当前文件中直接用 Util 代替全名
                    |
                    |fun doSomething() {
                    |    Util.doWork()  // 比写全名简洁多了
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  场景 3：给顶层扩展函数起别名
                    |// ════════════════════════════════════════════════════
                    |
                    |// 项目中惯用的 Log 封装
                    |import android.util.Log as ALog
                    |
                    |fun debug(msg: String) {
                    |    ALog.d("Debug", msg)  // 一看就知道是 Android Log
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  typealias vs import as 对比
                    |// ════════════════════════════════════════════════════
                    |
                    |// typealias — 项目级，定义新名字
                    |typealias Callback<T> = (Result<T>) -> Unit
                    |// 所有文件都可以通过 import 使用 Callback
                    |
                    |// import as — 文件级，仅本文件有效
                    |import com.other.lib.Callback as LibCallback
                    |// 只有这个文件中 Callback 被重命名为 LibCallback
                    """.trimMargin(),
                keyPoints = listOf(
                    "`import 原路径 as 别名` — 仅当前文件有效",
                    "主要用途：解决类名冲突（不同包的同名类）",
                    "可以给类、函数、对象等任何 import 的内容起别名",
                    "作用范围小（单文件）→ 不会污染全局命名空间",
                    "与 typealias 区别：import as 是文件级重命名，typealias 是项目级类型别名"
                ),
                note = "import as 仅当前文件有效"
            )
        ),
        pageNote = """
            |## ⭐ typealias vs import as 选择指南
            |
            || 需求 | 推荐 |
            ||------|------|
            || 给复杂泛型类型起短名（项目多处使用） | `typealias` |
            || 解决两个同名类的冲突 | `import ... as ...` |
            || 在此文件里临时简化长类名 | `import ... as ...` |
            || 定义回调/函数类型的标准化名称 | `typealias` |
            |
            |## 🔗 关联知识点
            |👉 `typealias Callback<T> = (Result<T>) -> Unit` → [高阶函数](app:higher-order)
            |👉 typealias vs import as vs 类委托 → [委托](app:delegation)
            |👉 配合 sealed class 定义网络结果 → [sealed class 实战](app:sealed-network)
            """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  协程入门（1 个页面，5 个 section）
    // ═══════════════════════════════════════════════════════════════════════════

    val coroutinePage = KnowledgePage(
        id = "coroutine",
        category = "协程",
        title = "协程入门 — 协程vs线程 / 调度器 / Job / async / 挂起函数",
        briefDesc = "协程 vs 线程 / 调度器 / Job / async / 挂起函数。用 KB 级内存支撑数十万并发",
        iconText = "协",
        iconColor = "#FF6F00",
        overview = """
            |## 一句话总结
            |
            |**协程是轻量级的用户态线程**，它通过**挂起而非阻塞**的方式，在线程之上实现了极高的并发能力，
            |特别适合 I/O 密集和高并发场景。
            |
            |## 一句话记住协程 vs 线程
            |
            || 维度 | 线程 | 协程 |
            ||------|------|------|
            || 内存 | **MB 级**，抢占式，多核下支撑几千密集运算 | **KB 级**，协作式，支持 I/O 密集和数十万高并发 |
            |
            |线程像是**重型卡车**（拉重货、占整条路），协程像是**电动滑板车**（轻便、灵活、量大不堵）。
        """.trimMargin(),
        sections = listOf(

            // ── Section 1：协程 vs 线程 ──
            ContentSection(
                title = "① 协程 vs 线程 — 核心区别",
                description = """
                    |## 本质区别
                    |
                    |- **线程**：操作系统管理的执行单元，内核态调度，**抢占式**
                    |- **协程**：用户态管理的执行单元，编译器 + 库调度，**协作式**
                    |
                    |## 为什么协程更轻量？
                    |
                    |线程切换需要 CPU 从用户态陷入内核态 → 保存/恢复寄存器 → 切换内存映射 → 再回到用户态。
                    |协程切换只在用户态完成，本质就是**函数调用级别的上下文保存**，开销极低。
                    |
                    |## 阻塞 vs 挂起（这是最核心的区别！）
                    |
                    |- 线程**阻塞**：线程卡住不动，什么也干不了，白白占用 MB 级内存
                    |- 协程**挂起**：协程暂停，但线程可以去执行其他协程，不浪费资源
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  对比：同样的并发任务，线程 vs 协程
                    |// ════════════════════════════════════════════════════
                    |
                    |// ── 线程方式：每个任务一个线程 ──
                    |// 启动 10 万个线程？系统直接 OOM！
                    |repeat(100) {
                    |    thread {
                    |        Thread.sleep(1000)   // 阻塞！线程卡住 1 秒
                    |        println("线程 ${'$'}it 完成")
                    |    }
                    |}
                    |// 100 个线程 ≈ 100 MB 内存
                    |
                    |
                    |// ── 协程方式：挂起而非阻塞 ──
                    |// 启动 10 万个协程？毫无压力！
                    |runBlocking {
                    |    repeat(100_000) {
                    |        launch {
                    |            delay(1000)        // 挂起！线程不卡，去干别的
                    |            println("协程 ${'$'}it 完成")
                    |        }
                    |    }
                    |}
                    |// 10 万个协程 ≈ 几十 MB 内存
                    |// 可能只用了 4 个线程！
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  特性对比总览表
                    |// ════════════════════════════════════════════════════
                    |
                    |//  ┌──────────┬──────────────┬──────────────┐
                    |//  │ 特性     │    线程      │    协程      │
                    |//  ├──────────┼──────────────┼──────────────┤
                    |//  │ 调度     │ 内核态，抢占 │ 用户态，协作 │
                    |//  │ 切换开销 │ 高（μs 级）  │ 极低（ns 级）│
                    |//  │ 内存占用 │ MB 级        │ KB 级        │
                    |//  │ 并发数量 │ 有限（几千） │ 海量（数十万）│
                    |//  │ 阻塞影响 │ 阻塞线程     │ 挂起协程     │
                    |//  │ 适用场景 │ CPU 密集     │ I/O 密集     │
                    |//  └──────────┴──────────────┴──────────────┘
                    """.trimMargin(),
                keyPoints = listOf(
                    "协程 = 用户态、协作式、KB 级内存、挂起不阻塞线程",
                    "线程 = 内核态、抢占式、MB 级内存、阻塞浪费资源",
                    "协程适合 I/O 密集 + 高并发（网络请求、数据库）",
                    "线程适合 CPU 密集 + 多核利用（复杂计算、图像处理）",
                    "挂起 ≠ 阻塞：挂起释放线程，阻塞卡死线程"
                ),
                note = "协程并不能替代线程——协程最终仍运行在线程上。协程解决的是「如何高效使用线程」，而非「消灭线程」。"
            ),

            // ── Section 2：调度器 ──
            ContentSection(
                title = "② 调度器 Dispatchers — 协程在哪个线程运行？",
                description = """
                    |调度器决定了协程**在哪个线程（或线程池）上执行**。
                    |
                    |Kotlin 提供 4 种内置调度器，覆盖绝大多数使用场景。
                    |
                    |## 记忆口诀
                    |- **Main** → 主线程 → UI 更新
                    |- **IO** → I/O 线程池 → 网络 / 文件 / 数据库
                    |- **Default** → CPU 线程池 → 计算 / 排序 / JSON
                    |- **Unconfined** → 不绑定 → 测试专用，开发少用
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  四大调度器速查
                    |// ════════════════════════════════════════════════════
                    |
                    |// ┌─────────────────────┬──────────────────────────┐
                    |// │ 调度器              │ 适用场景                  │
                    |// ├─────────────────────┼──────────────────────────┤
                    |// │ Dispatchers.Main    │ UI 更新、主线程轻量操作  │
                    |// │ Dispatchers.IO      │ 网络、文件、数据库 I/O   │
                    |// │ Dispatchers.Default  │ 排序、JSON、图像 CPU 密集 │
                    |// │ Dispatchers.Unconf.. │ 测试、极简场景（不推荐）  │
                    |// └─────────────────────┴──────────────────────────┘
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 1：Android 中最常用的模式
                    |// ════════════════════════════════════════════════════
                    |
                    |// 在 ViewModel 或 Activity 中：
                    |lifecycleScope.launch {
                    |    // 主线程 — 显示 loading
                    |    showLoading()
                    |
                    |    val data = withContext(Dispatchers.IO) {
                    |        // IO 线程 — 网络请求
                    |        api.fetchUserData()
                    |    }
                    |
                    |    // 自动切回主线程 — 更新 UI
                    |    updateUI(data)
                    |    hideLoading()
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 2：CPU 密集计算用 Default
                    |// ════════════════════════════════════════════════════
                    |
                    |val result = withContext(Dispatchers.Default) {
                    |    // CPU 线程池 — 大 JSON 解析
                    |    val json = Gson().fromJson<BigData>(raw)
                    |    json.items.sortedBy { it.score }
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  示例 3：将 Java 线程池转为协程调度器
                    |// ════════════════════════════════════════════════════
                    |
                    |val singleThread = Executors.newSingleThreadExecutor()
                    |    .asCoroutineDispatcher()   // ← Java 线程池 → 调度器
                    |
                    |// 使用：所有协程串行执行在这个单线程上
                    |withContext(singleThread) {
                    |    // 适合需要严格串行的场景（如文件写入队列）
                    |}
                    |singleThread.close()  // 用完记得关闭
                    """.trimMargin(),
                keyPoints = listOf(
                    "`Dispatchers.Main` → Android 主线程，UI 操作专用",
                    "`Dispatchers.IO` → I/O 线程池，网络 / 文件 / 数据库",
                    "`Dispatchers.Default` → CPU 线程池，计算密集任务",
                    "`Dispatchers.Unconfined` → 不限定线程，测试用",
                    "`Executor.asCoroutineDispatcher()` → Java 线程池转协程调度器"
                ),
                note = "IO 和 Default 底层共用线程池，但调度策略不同。IO 适合「等得多算得少」的任务，Default 适合「算得多等得少」的任务。"
            ),

            // ── Section 3：Job 管理 ──
            ContentSection(
                title = "③ Job 管理 — join 等待 / cancel 取消",
                description = """
                    |`launch {}` 返回一个 `Job` 对象，用于管理协程的生命周期。
                    |
                    |两个最核心的操作：
                    |- **join()** → 等待这个协程执行完毕
                    |- **cancel()** → 取消这个协程
                    |
                    |`join()` 和 `cancel()` 本身都是**挂起函数**，不会阻塞线程。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  join() — 等待协程执行完毕
                    |// ════════════════════════════════════════════════════
                    |
                    |runBlocking {
                    |    // 启动一个协程去下载
                    |    val job = launch {
                    |        delay(2000)
                    |        println("下载完成！")
                    |    }
                    |
                    |    println("等待下载...")
                    |    job.join()   // ← 挂起当前协程，直到 job 执行完毕
                    |    println("可以显示了")
                    |}
                    |// 输出顺序：
                    |// 等待下载...
                    |// 下载完成！         (2 秒后)
                    |// 可以显示了
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  cancel() — 取消协程
                    |// ════════════════════════════════════════════════════
                    |
                    |runBlocking {
                    |    val job = launch {
                    |        repeat(1000) { i ->
                    |            delay(500)              // 挂起点 → 可取消
                    |            println("第 ${'$'}i 次")
                    |        }
                    |    }
                    |
                    |    delay(2000)     // 等 2 秒
                    |    job.cancel()    // ← 取消！协程不再执行
                    |    job.join()      // 等待取消完成
                    |    println("已取消")
                    |}
                    |// 输出：大约 4 行 "第 N 次" 后被取消
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  cancel + join = cancelAndJoin()
                    |// ════════════════════════════════════════════════════
                    |
                    |job.cancelAndJoin()  // 等价于 cancel() + join()
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  取消需要协作！纯计算代码无法取消
                    |// ════════════════════════════════════════════════════
                    |
                    |val bad = launch {
                    |    var i = 0
                    |    while (true) {
                    |        i++   // ← 没有挂起点，cancel() 没用！
                    |    }
                    |}
                    |// 解决方案：手动检查 isActive 或用 yield()
                    |val good = launch {
                    |    var i = 0
                    |    while (isActive) {  // ← 检查是否被取消
                    |        i++
                    |    }
                    |}
                    """.trimMargin(),
                keyPoints = listOf(
                    "`launch {}` 返回 `Job`，管理协程生命周期",
                    "`join()` → 挂起等待协程执行完毕（不阻塞线程）",
                    "`cancel()` → 取消协程，需要协程协作（有挂起点）",
                    "`cancelAndJoin()` = `cancel()` + `join()` 一步到位",
                    "取消是协作的：纯计算循环需要用 `isActive` 检查"
                ),
                note = "协程取消是**协作式**的——协程内的代码必须遇到挂起点（delay / yield 等）或手动检查 isActive，cancel() 才能真正生效。这和线程的 interrupt() 类似。"
            ),

            // ── Section 4：async + await ──
            ContentSection(
                title = "④ async + await — 并发获取多个结果",
                description = """
                    |`launch` 关注的是「执行」，不返回结果（返回 Job）。
                    |`async` 关注的是「计算并返回结果」，返回 `Deferred<T>`。
                    |
                    |**核心模式**：用 `async` 启动多个并发任务 → 用 `await()` 获取每个结果。
                    |多个 `async` **真正并发执行**，总时间 = 最慢的那个任务。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  async + await — 并发执行，总时间 = 最慢者
                    |// ════════════════════════════════════════════════════
                    |
                    |runBlocking {
                    |    // 用 async 同时启动 3 个耗时请求
                    |    val user = async { fetchUser(1001) }      // 1秒
                    |    val orders = async { fetchOrders(1001) }  // 2秒
                    |    val score = async { fetchScore(1001) }    // 1.5秒
                    |
                    |    // 三个请求并发执行！
                    |    // await() 挂起当前协程，等待对应 Deferred 完成
                    |    val result = UserInfo(
                    |        user = user.await(),      // 等 1 秒拿到
                    |        orders = orders.await(),  // 已经完成，立即返回
                    |        score = score.await()     // 已经完成，立即返回
                    |    )
                    |    // 总耗时 ≈ 2 秒（最慢的 orders），不是 1+2+1.5=4.5 秒！
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  launch vs async 对比
                    |// ════════════════════════════════════════════════════
                    |
                    |// launch — 不返回结果，返回 Job
                    |val job: Job = launch {
                    |    doSomething()        // 执行，不关心返回值
                    |}
                    |
                    |// async — 返回结果，返回 Deferred<T>
                    |val deferred: Deferred<String> = async {
                    |    computeResult()      // 计算，需要返回值
                    |}
                    |val result: String = deferred.await()
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  错误处理：async 内的异常在 await() 时抛出
                    |// ════════════════════════════════════════════════════
                    |
                    |val task = async {
                    |    throw RuntimeException("网络错误")
                    |}
                    |try {
                    |    task.await()    // ← 异常在这里抛出！
                    |} catch (e: Exception) {
                    |    println("捕获: ${'$'}e")
                    |}
                    """.trimMargin(),
                keyPoints = listOf(
                    "`launch` → 返回 `Job`，不关心结果 → 适合「去做某事」",
                    "`async` → 返回 `Deferred<T>`，需要返回值 → 适合「计算并返回」",
                    "`await()` → 挂起等待 Deferred 完成，返回结果",
                    "多个 async **真正并发**：总时间 = 最慢的那个",
                    "async 内的异常不会立即抛出，在 `await()` 时抛出"
                ),
                note = "如果 async 启动后从不调用 await()，异常会被静默吃掉。如果不需要结果，用 launch 更合适。"
            ),

            // ── Section 5：常用挂起函数 ──
            ContentSection(
                title = "⑤ 常用挂起函数 + suspend 关键字",
                description = """
                    |挂起函数是协程的核心——它们能**暂停自身而不阻塞线程**。
                    |只有挂起函数才能调用其他挂起函数。
                    |
                    |## 内置挂起函数速查
                    |
                    || 函数 | 作用 | 常用场景 |
                    ||------|------|---------|
                    || `delay(ms)` | 非阻塞延迟 | 等待、定时、模拟耗时 |
                    || `yield()` | 让出执行权 | 公平调度、避免独占 |
                    || `withContext(ctx)` | 切换线程并返回结果 | IO 操作后回主线程 |
                    || `await()` | 等待 async 结果 | async 并发模式 |
                    || `coroutineScope {}` | 结构化并发作用域 | 子协程全完成后继续 |
                    |
                    |## suspend 关键字
                    |
                    |`suspend` 标记一个函数为**挂起函数**，它内部可以调用其他挂起函数。
                    |普通函数只能通过协程构建器（launch / async / runBlocking）进入挂起世界。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  delay() — 非阻塞延迟
                    |// ════════════════════════════════════════════════════
                    |
                    |launch {
                    |    println("开始")
                    |    delay(1000)    // 挂起 1 秒，线程不阻塞！
                    |    println("1 秒后")
                    |}
                    |// 对比 Thread.sleep(1000) — 线程卡死 1 秒
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  yield() — 主动让出执行权
                    |// ════════════════════════════════════════════════════
                    |
                    |launch {
                    |    repeat(5) {
                    |        println("协程 A 第 ${'$'}it 次")
                    |        yield()   // 让给其他协程 → 公平调度
                    |    }
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  withContext() — 切换调度器并返回结果
                    |// ════════════════════════════════════════════════════
                    |
                    |launch(Dispatchers.Main) {
                    |    // 当前在主线程
                    |    val data = withContext(Dispatchers.IO) {
                    |        // 切到 IO 线程执行
                    |        api.fetchData()
                    |    }
                    |    // 自动切回主线程 — 安全更新 UI
                    |    textView.text = data
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  coroutineScope {} — 结构化并发
                    |// ════════════════════════════════════════════════════
                    |
                    |suspend fun loadAllData(): UserInfo {
                    |    return coroutineScope {
                    |        // 同时启动两个请求
                    |        val user = async { fetchUser() }
                    |        val cfg  = async { fetchConfig() }
                    |        // 等两个都完成才返回
                    |        UserInfo(user.await(), cfg.await())
                    |    }
                    |    // coroutineScope 保证：所有子协程完成后才继续
                    |    // 如果任一子协程失败，其他全部取消
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  suspend — 定义你自己的挂起函数
                    |// ════════════════════════════════════════════════════
                    |
                    |// 用 suspend 标记 → 可以在里面调用其他挂起函数
                    |suspend fun fetchAndCache(userId: Int): User {
                    |    val user = withContext(Dispatchers.IO) {
                    |        api.fetchUser(userId)   // 挂起函数
                    |    }
                    |    cache.put(userId, user)     // 普通函数
                    |    return user                   // 普通返回
                    |}
                    |
                    |// 调用挂起函数的方式 1：在协程里调用
                    |launch {
                    |    val user = fetchAndCache(1001)
                    |}
                    |// 调用方式 2：在另一个挂起函数里调用
                    |suspend fun refresh() {
                    |    val user = fetchAndCache(1001)  // ✅ OK
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  suspendCoroutine — 将回调 API 转为挂起函数
                    |// ════════════════════════════════════════════════════
                    |
                    |// 旧的回调风格 API：
                    |// api.login(name, pwd, object : Callback {
                    |//     override fun onSuccess(user: User) { ... }
                    |//     override fun onError(e: Exception) { ... }
                    |// })
                    |
                    |// 用 suspendCancellableCoroutine 包装成挂起函数：
                    |suspend fun login(name: String, pwd: String): User {
                    |    return suspendCancellableCoroutine { continuation ->
                    |        api.login(name, pwd, object : Callback {
                    |            override fun onSuccess(user: User) {
                    |                continuation.resume(user)
                    |                // ↑ 恢复协程，user 作为返回值
                    |            }
                    |            override fun onError(e: Exception) {
                    |                continuation.resumeWithException(e)
                    |                // ↑ 恢复协程并抛异常
                    |            }
                    |        })
                    |    }
                    |}
                    |// 现在可以像同步代码一样调用：
                    |// val user = login("张三", "123456")
                    """.trimMargin(),
                keyPoints = listOf(
                    "`delay(ms)` → 非阻塞延迟，线程可去做其他事",
                    "`yield()` → 主动让出执行权，实现公平调度",
                    "`withContext(ctx)` → 切线程执行 + 自动切回，最常用的切换方式",
                    "`coroutineScope {}` → 创建结构化作用域，等所有子协程完成",
                    "`await()` → 等待 Deferred 完成并返回结果",
                    "`suspend` 关键字 → 定义自己的挂起函数",
                    "`suspendCoroutine()` / `suspendCancellableCoroutine()` → 回调转协程"
                ),
                note = "`withContext` 和 `coroutineScope` 都会挂起等待。区别：withContext 可以切换调度器，coroutineScope 保持当前上下文不变。"
            )
        ),
        pageNote = """
            |## ⭐ 协程知识速查
            |
            || 需求 | 用什么 |
            ||------|--------|
            || 启动协程不需要返回值 | `launch { }` |
            || 启动协程需要返回值 | `async { }.await()` |
            || 在 IO 线程执行耗时操作 | `withContext(Dispatchers.IO) { }` |
            || 等待协程完成 | `job.join()` |
            || 取消协程 | `job.cancel()` |
            || 非阻塞延迟 | `delay(ms)` |
            || 定义挂起函数 | `suspend fun xxx()` |
            || 回调转挂起 | `suspendCancellableCoroutine { }` |
            |
            |## 一行记忆
            |**线程是卡车，协程是滑板车。卡车拉重货走大路（CPU 密集），滑板车灵活量大（I/O 密集）。**
            |
            |## 🔗 关联知识点
            |👉 `suspend` = 挂起函数，在协程内部调用 → [高阶函数](app:higher-order)
            |👉 seal class 封装网络请求结果 → [sealed class 实战](app:sealed-network)
            |👉 Flow 是协程原生响应式流 → [Flow](app:flow)
            |👉 `repeat(100_000) { launch { } }` → [循环](app:loops)
            |👉 MVVM 中实际运行 → [MVVM Demo](app:mvvm-demo)
            """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  类修饰符（1 个页面，5 个 section）
    //  open / final / abstract / data / sealed / enum / annotation
    // ═══════════════════════════════════════════════════════════════════════════

    val classModifiersPage = KnowledgePage(
        id = "class-modifiers",
        category = "修饰符",
        title = "类修饰符 — open / final / abstract / data / sealed / enum / annotation",
        briefDesc = "open / final / abstract / data / sealed / enum / annotation。决定类能被谁继承、能干什么",
        iconText = "类",
        iconColor = "#00838F",
        overview = """
            |Kotlin 类的修饰符决定了**这个类能被怎样使用**。
            |
            |最关键的差异：Java 中类默认 `public` 且可被继承；Kotlin 中类默认 `final`（不可继承）。
            |这是一个**设计哲学差异**——Kotlin 认为「不被设计为继承的类，就不该被继承」。
            |
            || 修饰符 | 能否被继承 | 能否实例化 | 典型用途 |
            ||--------|----------|----------|---------|
            || `final`（默认） | ❌ 不能 | ✅ 能 | 普通类 |
            || `open` | ✅ 能 | ✅ 能 | 为继承而设计的类 |
            || `abstract` | ✅ 必须 | ❌ 不能 | 模板类 |
            || `data` | ❌ 不能 | ✅ 能 | 数据容器 |
            || `sealed` | ❌ 不能（外部） | ❌ 不能 | 受限类层次 |
            || `enum` | ❌ 不能 | ❌ 不能（手动） | 固定常量集合 |
            || `annotation` | ❌ 不能 | ❌ 不能 | 自定义注解 |
        """.trimMargin(),
        sections = listOf(

            // ── Section 1：open vs final ──
            ContentSection(
                title = "① open vs final — 继承控制",
                description = """
                    |这是 Kotlin 和 Java 在类设计上**最大的差异**。
                    |
                    |**Java**：类默认可以被继承（需要 `final` 来禁止）
                    |**Kotlin**：类默认 `final`（需要 `open` 来允许）
                    |
                    |Kotlin 的设计哲学来自《Effective Java》的核心建议：
                    |**"要么为继承而设计并写好文档，要么禁止继承"**
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  Kotlin 类默认 final → 不能被继承
                    |// ════════════════════════════════════════════════════
                    |
                    |class Animal {                     // 默认 final
                    |    fun eat() = println("吃...")
                    |}
                    |
                    |// class Dog : Animal()  ← ❌ 编译错误！
                    |// Animal 是 final 的，不能继承
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  加 open → 明确声明"可以被继承"
                    |// ════════════════════════════════════════════════════
                    |
                    |open class Animal {                // ← open 修饰
                    |    fun eat() = println("吃...")
                    |    open fun sound() = "..."       // 方法也需 open 才能重写
                    |}
                    |
                    |class Dog : Animal() {             // ✅ 现在可以继承了
                    |    override fun sound() = "汪汪"  // ✅ 重写 open 方法
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  对比：Java 默认 open，Kotlin 默认 final
                    |// ════════════════════════════════════════════════════
                    |
                    |// Java：
                    |// class Animal {}          ← 默认可以被继承
                    |// final class Animal {}    ← 需要显式禁止
                    |
                    |// Kotlin：
                    |// class Animal {}          ← 默认 final，不可继承
                    |// open class Animal {}     ← 需要显式允许
                    |
                    |// 这就是为什么 Android 中继承 AppCompatActivity 时
                    |// AppCompatActivity 是用 open 修饰的（或 abstract）
                    """.trimMargin(),
                keyPoints = listOf(
                    "Kotlin 类**默认 `final`**——这是和 Java 最大的不同",
                    "想让人继承 → 加 `open` 关键字",
                    "方法也默认 final → 想让人重写也要加 `open`",
                    "设计意图：防止意外的继承破坏封装",
                    "如果一个类既没加 open 也没加 abstract → 它就是 final 的"
                ),
                note = "如果不确定一个类将来是否需要被继承，先别加 open。Kotlin 鼓励「先封闭，确认需求后再开放」。"
            ),

            // ── Section 2：abstract ──
            ContentSection(
                title = "② abstract — 抽象类",
                description = """
                    |`abstract` 类**不能直接实例化**，只能被继承。用于定义「模板」——规定子类必须做什么。
                    |
                    |抽象类可以包含抽象方法（无实现）和具体方法（有实现）。
                    |抽象类默认是 `open` 的，不需要重复加 open。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  abstract 类：定义模板 + 部分实现
                    |// ════════════════════════════════════════════════════
                    |
                    |abstract class Shape {
                    |    // 抽象属性 — 子类必须提供
                    |    abstract val name: String
                    |
                    |    // 抽象方法 — 子类必须实现（默认 open）
                    |    abstract fun area(): Double
                    |
                    |    // 具体方法 — 子类可用可重写
                    |    fun describe() = "${'$'}name 的面积是 ${'$'}{area()}"
                    |}
                    |
                    |class Circle(val r: Double) : Shape() {
                    |    override val name = "圆形"
                    |    override fun area() = Math.PI * r * r
                    |}
                    |
                    |val c = Circle(5.0)
                    |println(c.describe())  // 圆形 的面积是 78.53...
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  abstract vs open 对比
                    |// ════════════════════════════════════════════════════
                    |
                    |open class Parent       // 可继承、可实例化
                    |abstract class Template // 可继承、**不能**实例化
                    |
                    |// val p = Parent()      ✅ 可以
                    |// val t = Template()    ❌ 编译错误！抽象类不能实例化
                    """.trimMargin(),
                keyPoints = listOf(
                    "不能实例化 → `val s = Shape()` 会报错",
                    "抽象方法必须在子类中 `override` 实现",
                    "抽象类天然 `open`，不需要加 open",
                    "可以混搭：有抽象方法也有具体方法",
                    "和 Java 的 abstract 语义一致"
                )
            ),

            // ── Section 3：data class ──
            ContentSection(
                title = "③ data — 数据类",
                description = """
                    |`data class` 是专门为**持有数据**而设计的类。
                    |
                    |编译器自动生成：`equals()` / `hashCode()` / `toString()` / `copy()` / `componentN()`。
                    |
                    |一个 data class 就像打包好的数据容器——开箱即用，不用手写上述样板代码。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  data class — 一行代码搞定数据容器
                    |// ════════════════════════════════════════════════════
                    |
                    |data class User(
                    |    val id: Int,
                    |    val name: String,
                    |    val age: Int
                    |)
                    |
                    |// 自动获得的功能：
                    |
                    |// ① toString() — 可读的字符串表示
                    |val u = User(1, "张三", 25)
                    |println(u)  // User(id=1, name=张三, age=25)
                    |
                    |// ② equals() — 按属性值比较（而非引用地址）
                    |val u2 = User(1, "张三", 25)
                    |println(u == u2)  // true（值相等！）
                    |
                    |// ③ copy() — 复制一份，可修改部分字段
                    |val older = u.copy(age = 26)
                    |println(older)  // User(id=1, name=张三, age=26)
                    |
                    |// ④ componentN() — 支持解构声明
                    |val (id, name, age) = u
                    |println("${'$'}name ${'$'}age 岁")  // 张三 25 岁
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  data class 的限制
                    |// ════════════════════════════════════════════════════
                    |
                    |// data class 是 final 的 → 不能被继承
                    |// data class User2(val name: String)
                    |// data class Admin(...) : User2(...)  ← ❌ 编译错误！
                    |
                    |// 主构造器至少有一个参数
                    |// data class Empty  ← ❌ 编译错误！
                    """.trimMargin(),
                keyPoints = listOf(
                    "`data class` 自动生成 `equals/hashCode/toString/copy/componentN`",
                    "按**属性值**比较相等（不是引用地址）",
                    "`copy()` 创建副本，可一次性修改部分字段",
                    "支持解构：`val (a, b) = obj`",
                    "限制：不能继承、至少一个构造参数、隐式 final"
                )
            ),

            // ── Section 4：sealed class ──
            ContentSection(
                title = "④ sealed — 密封类（受限类层次）",
                description = """
                    |`sealed class` 限制了「哪些类可以继承它」——只能在同一文件内定义子类。
                    |
                    |这使得编译器知道所有可能的子类型，配合 `when` 表达式可以实现**穷尽检查**：
                    |如果 when 漏掉了某个子类，编译器会警告你。
                    |
                    |这非常适合表示**有限的状态集合**——如网络请求的加载中/成功/失败。
                    |👉 配合 [`when` 表达式](作用域函数页面)可实现完美的穷尽分支。参见 [let](app:let) 页面中 `?.let { }` 的空安全模式。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  sealed class — 最适合表示「有限状态」
                    |// ════════════════════════════════════════════════════
                    |
                    |// 定义网络请求的结果状态（所有子类必须在同一文件）
                    |sealed class NetworkResult<out T> {
                    |    data class Success<T>(val data: T) : NetworkResult<T>()
                    |    data class Error(val code: Int, val msg: String) : NetworkResult<Nothing>()
                    |    object Loading : NetworkResult<Nothing>()
                    |}
                    |
                    |// 使用时 — when 穷尽检查：
                    |fun handleResult(result: NetworkResult<User>) {
                    |    when (result) {
                    |        is NetworkResult.Success -> showUser(result.data)
                    |        is NetworkResult.Error   -> showError(result.msg)
                    |        is NetworkResult.Loading -> showSpinner()
                    |    }
                    |    // ✅ 编译器确认：所有分支都覆盖了
                    |    // 如果漏掉一个分支 → 编译器报 warning！
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  sealed vs enum 对比
                    |// ════════════════════════════════════════════════════
                    |
                    |// enum：每个值都是单例常量（不能携带不同数据）
                    |enum class Color { RED, GREEN, BLUE }
                    |
                    |// sealed：子类可以携带不同形状的数据
                    |sealed class PaymentResult {
                    |    data class Paid(val amount: Double, val time: Long) : PaymentResult()
                    |    data class Refused(val reason: String) : PaymentResult()
                    |    object Pending : PaymentResult()  // 无数据 → object
                    |}
                    |// sealed 比 enum 强大太多！
                    """.trimMargin(),
                keyPoints = listOf(
                    "所有子类必须**定义在同一文件内**",
                    "配合 `when` 实现穷尽检查，编译器保证不遗漏",
                    "子类可以是 `data class`、`object`、普通 `class`",
                    "比 enum 更强大：每个子类可携带不同形状的数据",
                    "常见用途：网络结果 / UI 状态 / 事件类型",
                    "是 final 的：外部文件不能继承 sealed class"
                ),
                note = "sealed class 隐式 abstract，不能直接实例化。子类不想被外部继承 → 子类也是 final 的（默认）。👉 另见 [Object](app:object) 页面中 `object` 配合 sealed class 的用法。"
            ),

            // ── Section 5：enum + annotation ──
            ContentSection(
                title = "⑤ enum / annotation — 枚举与注解",
                description = """
                    |**enum class**：定义一组固定的常量。比 Java 的枚举更强大——可以带属性、方法、实现接口。
                    |
                    |**annotation class**：定义自定义注解。用于元编程（给代码添加元数据）。
                    |在 Android 中常用于标注需要特殊处理的类或方法。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  enum class — 比 Java 枚举更强
                    |// ════════════════════════════════════════════════════
                    |
                    |enum class HttpStatus(val code: Int, val desc: String) {
                    |    OK(200, "成功"),
                    |    NOT_FOUND(404, "未找到"),
                    |    SERVER_ERROR(500, "服务器错误");
                    |
                    |    fun isSuccess() = code in 200..299
                    |}
                    |
                    |val status = HttpStatus.NOT_FOUND
                    |println(status.desc)       // 未找到
                    |println(status.isSuccess()) // false
                    |
                    |// 遍历所有枚举值
                    |HttpStatus.entries.forEach {
                    |    println("${'$'}{it.code}: ${'$'}{it.desc}")
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  annotation class — 自定义注解
                    |// ════════════════════════════════════════════════════
                    |
                    |// 定义注解
                    |@Target(AnnotationTarget.CLASS)
                    |@Retention(AnnotationRetention.RUNTIME)
                    |annotation class RequiresPermission(val value: String)
                    |
                    |// 使用注解
                    |@RequiresPermission("android.permission.CAMERA")
                    |class CameraActivity : AppCompatActivity() { ... }
                    |
                    |// 运行时通过反射读取注解
                    |val annotation = CameraActivity::class
                    |    .annotations
                    |    .find { it is RequiresPermission } as? RequiresPermission
                    |println(annotation?.value)  // android.permission.CAMERA
                    """.trimMargin(),
                keyPoints = listOf(
                    "`enum class` 可以带属性、方法、构造函数",
                    "`entries` 获取所有枚举值（Kotlin 1.9+）",
                    "`annotation class` 定义自定义注解",
                    "注解需配合 `@Target` / `@Retention` 元注解使用",
                    "和 Java 的枚举/注解语法类似，但写法更精简"
                )
            )
        ),
        pageNote = """
            |## ⭐ 类修饰符速查
            |
            || 你想... | 用什么 |
            ||---------|--------|
            || 写一个普通类 | 默认（`final`）|
            || 允许类被继承 | `open class` |
            || 定义模板，强制子类实现 | `abstract class` |
            || 存储数据 | `data class` |
            || 限制子类范围，配合 when | `sealed class` |
            || 固定常量集合 | `enum class` |
            || 自定义注解 | `annotation class` |
            |
            |👉 继承后的方法重写 → 见 [成员修饰符](app:member-modifiers) 页面
            |👉 sealed class 配合 when 穷尽 → [let](app:let) · [Object](app:object) · [when 表达式](app:when-expr)
            |👉 `out T` 协变密封类 → [泛型 out/in](app:generic-modifiers)
            |👉 `data class` 的 `copy()` → 配合 StateFlow → [Flow](app:flow)
            """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  成员修饰符（1 个页面，2 个 section）
    //  override / open / final / abstract / lateinit
    // ═══════════════════════════════════════════════════════════════════════════

    val memberModifiersPage = KnowledgePage(
        id = "member-modifiers",
        category = "修饰符",
        title = "成员修饰符 — override / open / abstract / lateinit",
        briefDesc = "override / open / final / abstract / lateinit。控制方法重写和属性初始化时机",
        iconText = "员",
        iconColor = "#00897B",
        overview = """
            |类的成员（方法、属性）也可以用修饰符来控制**重写行为**和**初始化时机**。
            |
            || 修饰符 | 作用对象 | 含义 |
            ||--------|---------|------|
            || `override` | 方法 / 属性 | 重写父类的 open 成员 |
            || `open` | 方法 / 属性 | 允许被子类重写 |
            || `final` | 方法 / 属性 | 禁止被子类重写（默认） |
            || `abstract` | 方法 / 属性 | 没有实现，子类必须重写 |
            || `lateinit` | var 属性 | 延迟初始化，稍后赋值 |
        """.trimMargin(),
        sections = listOf(

            // ── Section 1：重写四件套 ──
            ContentSection(
                title = "① override / open / final / abstract — 重写控制",
                description = """
                    |这四个修饰符构成了 Kotlin 的重写规则体系。核心原则与类修饰符一致：
                    |**默认不可重写，必须显式声明**。
                    |
                    |在抽象类中，`abstract` 成员默认 `open`；在普通类中，所有成员默认 `final`。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  完整示例：重写体系的四个角色
                    |// ════════════════════════════════════════════════════
                    |
                    |open class Animal {
                    |    // final 方法 — 子类不能重写（默认，可省略 final）
                    |    fun eat() = println("吃东西")
                    |
                    |    // open 方法 — 子类可以选择重写
                    |    open fun sound(): String = "..."
                    |
                    |    // open 属性 — 子类可以重写 getter
                    |    open val legCount: Int = 0
                    |}
                    |
                    |abstract class Bird : Animal() {
                    |    // abstract 方法 — 子类必须实现
                    |    abstract fun fly()
                    |
                    |    // 重写父类的 open 方法
                    |    override fun sound(): String = "叽叽"
                    |
                    |    // 可以继续声明为 open，让更下层的子类重写
                    |    open override fun sound(): String = "叽叽"
                    |    //  ↑ open + override 可以同时出现
                    |}
                    |
                    |class Sparrow : Bird() {
                    |    override val legCount = 2
                    |    override fun fly() = println("麻雀飞")
                    |    override fun sound() = "啾啾"  // Bird 的 sound 是 open 的
                    |}
                    |
                    |// val s = Sparrow()
                    |// s.eat()     // 吃东西（继承自 Animal）
                    |// s.sound()   // 啾啾 （重写链：Animal → Bird → Sparrow）
                    |// s.fly()     // 麻雀飞
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  修饰符组合规则
                    |// ════════════════════════════════════════════════════
                    |
                    |// open override — 重写且允许再被重写
                    |// final override — 重写且禁止再被重写（终结重写链）
                    |// abstract override — 重写抽象方法（在抽象类中）
                    """.trimMargin(),
                keyPoints = listOf(
                    "`override` → 重写父类 open 成员（必须写，不像 Java 用 @Override 可选）",
                    "`open` → 允许子类重写这个成员",
                    "`final` → 禁止重写（默认行为，通常不需要显式写）",
                    "`abstract` → 无实现，子类必须 override（只能在抽象类中）",
                    "只有 open 或 abstract 的成员才能被 override",
                    "Kotlin 的 `override` 是强制关键字，Java 的 `@Override` 只是注解（可选）"
                ),
                note = "Kotlin 要求 override 必须写——好处是你不会意外重写父类方法（比如父类新增了一个同名方法，编译器会报错让你确认）。Java 中这种场景可能静默覆盖导致 bug。"
            ),

            // ── Section 2：lateinit ──
            ContentSection(
                title = "② lateinit — 延迟初始化",
                description = """
                    |`lateinit` 告诉编译器：「这个 var 我现在不初始化，但使用前一定会赋值」。
                    |
                    |只能修饰 `var`（可变属性），不能修饰 `val`（不可变属性）。
                    |只能修饰**非空类型**，不能修饰可空类型（`String?` 不行）。
                    |
                    |**常见场景**：Android 中 `lateinit var binding`、依赖注入的属性、单元测试的 setUp 中初始化的属性。
                    |
                    |👉 与空安全的关系：`lateinit` 避免了「明明会初始化却要声明为可空」的尴尬。另见 [空安全](app:nullsafety) 页面。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  lateinit 解决的问题
                    |// ════════════════════════════════════════════════════
                    |
                    |// 不用 lateinit — 被迫声明为可空或给假默认值：
                    |class MyActivity1 {
                    |    private var binding: ActivityMainBinding? = null
                    |    // 每次使用都要 binding?.xxx ?: return
                    |    // 或者 binding!!.xxx（危险！）
                    |}
                    |
                    |// 用 lateinit — 声明为非空，延迟初始化：
                    |class MyActivity2 {
                    |    private lateinit var binding: ActivityMainBinding
                    |    // 直接 binding.xxx，不需要 ?. 或 !!
                    |    // 承诺：onCreate 时一定会赋值
                    |
                    |    fun onCreate() {
                    |        binding = ActivityMainBinding.inflate(...)
                    |        binding.textView.text = "Hello"  // ✅ 直接用
                    |    }
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  lateinit 的限制
                    |// ════════════════════════════════════════════════════
                    |
                    |class Demo {
                    |    lateinit var name: String   // ✅ String（非空）
                    |    // lateinit var age: Int    // ❌ 基本类型不行！
                    |    // lateinit val fixed: String // ❌ val 不行！
                    |    // lateinit var x: String?  // ❌ 可空类型不行！
                    |
                    |    fun check() {
                    |        // 检查是否已初始化
                    |        if (::name.isInitialized) {
                    |            println(name)
                    |        } else {
                    |            println("还没初始化")
                    |        }
                    |    }
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  忘记初始化的后果
                    |// ════════════════════════════════════════════════════
                    |
                    |// val demo = Demo()
                    |// demo.name  ← 💥 UninitializedPropertyAccessException！
                    |// demo.check() 先用 ::name.isInitialized 安全判断
                    """.trimMargin(),
                keyPoints = listOf(
                    "只能修饰 `var`（可变），不能修饰 `val`",
                    "只能修饰非空引用类型，不能修饰基本类型（Int、Boolean 等）",
                    "访问未初始化的 lateinit → 抛 `UninitializedPropertyAccessException`",
                    "用 `::属性名.isInitialized` 检查是否已初始化",
                    "避免了「明明会初始化却要声明为可空并到处 ?.」的尴尬",
                    "Android 中最常用于 ViewBinding 和依赖注入"
                ),
                note = "如果属性类型是基本类型或 val，不能用 lateinit。替代方案：`lazy { }`（val 的延迟初始化）或声明为可空类型配合 `?.`。👉 详见 [空安全](app:nullsafety) 页面。"
            )
        ),
        pageNote = """
            |## ⭐ 成员修饰符速查
            |
            || 场景 | 用什么 |
            ||------|--------|
            || 重写父类方法 | `override fun xxx()` |
            || 允许子类重写 | `open fun xxx()` |
            || 子类必须实现 | `abstract fun xxx()` |
            || 稍后初始化（var） | `lateinit var xxx` |
            || 懒加载初始化（val） | `val xxx by lazy { ... }` |
            |
            |👉 类级的 `open` / `abstract` → 见 [类修饰符](app:class-modifiers) 页面
            |👉 `lateinit` 与可空类型的取舍 → 见 [空安全](app:nullsafety) 页面
            |👉 `by lazy` 对比 (val 的延迟初始化) → [委托](app:delegation)
            |👉 `open override` 组合 → [类修饰符](app:class-modifiers)
            """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  泛型修饰符（1 个页面，3 个 section）
    //  out (协变) / in (逆变)
    // ═══════════════════════════════════════════════════════════════════════════

    val genericModifiersPage = KnowledgePage(
        id = "generic-modifiers",
        category = "泛型",
        title = "泛型 out / in — 协变(producer)与逆变(consumer)",
        briefDesc = "out（协变）≈ Java extends / in（逆变）≈ Java super。控制泛型类型的子类型关系",
        iconText = "型",
        iconColor = "#4527A0",
        overview = """
            |Kotlin 的 `out` 和 `in` 是**声明处型变**（declaration-site variance）的关键字。
            |与 Java 的**使用处型变**（`? extends T`、`? super T`）不同，Kotlin 在**定义泛型类时**就声明了型变。
            |
            || 修饰符 | 等价 Java | 含义 | 只能用于 |
            ||--------|----------|------|---------|
            || `out T` | `? extends T` | 生产者 — 只读 | 返回值（输出）位置 |
            || `in T` | `? super T` | 消费者 — 只写 | 参数（输入）位置 |
            || 无修饰符 | 不变 | 既读又写 | 任意位置 |
            |
            |👉 泛型与 `data class` 的配合 → 见 [类修饰符](app:class-modifiers) 页面的 data class。
        """.trimMargin(),
        sections = listOf(

            // ── Section 1：out 协变 ──
            ContentSection(
                title = "① out — 协变（生产者，只读）",
                description = """
                    |`out` 表示「这个泛型类型只出现在**输出位置**（返回值）」。
                    |
                    |**协变的含义**：如果 `Dog` 是 `Animal` 的子类，那么 `Box<Dog>` 也是 `Box<Animal>` 的子类。
                    |即：泛型参数的子类型关系「向外」传递到了泛型类。
                    |
                    |**为什么只能用于输出位置？**
                    |因为 Kotlin 在**定义类时**就承诺「T 只会被读取，不会被写入」→ 编译器可以安全地允许协变。
                    |如果你把 `out T` 用在输入位置，编译器会报错。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  out 协变 = 生产者 = 只取不存
                    |// ════════════════════════════════════════════════════
                    |
                    |// 定义一个「只读容器」 — T 只出现在返回值位置
                    |interface Production<out T> {
                    |    fun produce(): T           // ✅ 输出位置 → 返回值
                    |    // fun consume(item: T) {} // ❌ 编译错误！T 出现在输入位置
                    |}
                    |
                    |// 协变的效果：
                    |open class Animal
                    |class Dog : Animal()
                    |
                    |class DogBreeder : Production<Dog> {
                    |    override fun produce(): Dog = Dog()
                    |}
                    |
                    |// 关键：Production<Dog> 可以赋值给 Production<Animal>！
                    |val breeder: Production<Animal> = DogBreeder()
                    |//  ↑ 因为 out 声明了「只生产 Dog」
                    |//  而 Dog 是 Animal 的子类 → 安全！
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  实际案例：Kotlin 标准库大量使用 out
                    |// ════════════════════════════════════════════════════
                    |
                    |// List 的定义（简化）：
                    |// interface List<out E> : Collection<E> { ... }
                    |
                    |// 因为 List 是只读的 → 可以声明为 out
                    |val dogs: List<Dog> = listOf(Dog(), Dog())
                    |val animals: List<Animal> = dogs  // ✅ 协变！
                    |
                    |// 对比：MutableList 不能声明为 out（因为可以 add）
                    |// interface MutableList<E> : List<E>, MutableCollection<E>
                    |// 注意 E 前面没有 out！因为 MutableList 既可以读也可以写
                    |val mDogs: MutableList<Dog> = mutableListOf(Dog())
                    |// val mAnimals: MutableList<Animal> = mDogs  ← ❌ 编译错误
                    """.trimMargin(),
                keyPoints = listOf(
                    "`out T` = 生产者 = T 只出现在**返回值**位置",
                    "等价 Java 的 `? extends T`",
                    "协变：`Box<Dog>` 是 `Box<Animal>` 的子类型",
                    "编译器保证：`out T` 不会被写入（只读安全）",
                    "Kotlin 标准库实例：`List<out E>`、`Sequence<out T>`"
                ),
                note = "`out` 是「声明处型变」— 类作者在定义时就声明。Java 的 `? extends` 是「使用处型变」— 调用者每次使用时声明。Kotlin 的方式减少了调用者的负担。"
            ),

            // ── Section 2：in 逆变 ──
            ContentSection(
                title = "② in — 逆变（消费者，只写）",
                description = """
                    |`in` 表示「这个泛型类型只出现在**输入位置**（参数）」。
                    |
                    |**逆变的含义**：如果 `Dog` 是 `Animal` 的子类，那么 `Handler<Animal>` 是 `Handler<Dog>` 的子类。
                    |注意——子类型关系**反转**了！这称为「逆变」。
                    |
                    |直觉理解：一个能处理 `Animal` 的处理器，当然也能处理 `Dog`（Dog 也是 Animal）。
                    |因此 `Handler<Animal>` 可以安全地当作 `Handler<Dog>` 使用。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  in 逆变 = 消费者 = 只存不取
                    |// ════════════════════════════════════════════════════
                    |
                    |// 定义一个「只写容器」 — T 只出现在参数位置
                    |interface Consumer<in T> {
                    |    fun consume(item: T)     // ✅ 输入位置 → 参数
                    |    // fun produce(): T {}   // ❌ 编译错误！T 出现在输出位置
                    |}
                    |
                    |// 逆变的效果：
                    |open class Animal
                    |class Dog : Animal()
                    |
                    |class AnimalFeeder : Consumer<Animal> {
                    |    override fun consume(item: Animal) {
                    |        println("喂食: ${'$'}item")
                    |    }
                    |}
                    |
                    |// 关键：Consumer<Animal> 可以赋值给 Consumer<Dog>！
                    |val dogFeeder: Consumer<Dog> = AnimalFeeder()
                    |//  ↑ 因为 in 声明了「只消费 Animal」
                    |//  而 Dog 是 Animal 的子类 → 安全！
                    |//  能消费 Animal 的，肯定能消费 Dog
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  实际案例：Comparable
                    |// ════════════════════════════════════════════════════
                    |
                    |// Comparable 的定义：
                    |// interface Comparable<in T> { operator fun compareTo(other: T): Int }
                    |
                    |// T 只出现在参数位置 → 可以声明为 in
                    |val animalComp: Comparable<Animal> = object : Comparable<Animal> {
                    |    override fun compareTo(other: Animal): Int = 0
                    |}
                    |// 逆变的威力：Comparable<Animal> 可以当作 Comparable<Dog>
                    |val dogComp: Comparable<Dog> = animalComp  // ✅ 逆变！
                    |// 能比较 Animal 的，当然能比较 Dog
                    """.trimMargin(),
                keyPoints = listOf(
                    "`in T` = 消费者 = T 只出现在**参数**位置",
                    "等价 Java 的 `? super T`",
                    "逆变：`Handler<Animal>` 是 `Handler<Dog>` 的子类型（关系反转！）",
                    "编译器保证：`in T` 不会被读取（只写安全）",
                    "Kotlin 标准库实例：`Comparable<in T>`、`Comparator<in T>`"
                ),
                note = "逆变是泛型中最难理解的概念。记忆口诀：**消费者 in，生产者 out**（Consumer in, Producer out = C I P O）。"
            ),

            // ── Section 3：总结对比 ──
            ContentSection(
                title = "③ out vs in — 对比总结",
                description = """
                    |一句话记住：**生产者 out，消费者 in**（PECS 原则的 Kotlin 版）。
                    |
                    |如果泛型类**只生产** T 类型值（返回值）→ 用 `out`
                    |如果泛型类**只消费** T 类型值（参数）→ 用 `in`
                    |如果既要生产又要消费 → 不用修饰符（不变）
                    |
                    |👉 这个知识点与 [sealed class](app:class-modifiers) 中「密封类 + 泛型」的 NetworkResult 示例紧密相关。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  out 和 in 对比一览
                    |// ════════════════════════════════════════════════════
                    |
                    |// ┌──────────┬────────────┬──────────────────────┐
                    |// │ 修饰符   │ out        │ in                   │
                    |// ├──────────┼────────────┼──────────────────────┤
                    |// │ 角色     │ 生产者     │ 消费者               │
                    |// │ 方向     │ 只读（取） │ 只写（存）           │
                    |// │ Java 等价 │ ? extends T │ ? super T            │
                    |// │ 子类型   │ Box<Dog>   │ Handler<Animal>      │
                    |// │ 关系     │  → 是      │  → 是 Handler<Dog>   │
                    |// │          │ Box<Animal>│ 的子类型（反转！）   │
                    |// │          │ 的子类型   │                      │
                    |// │ 允许位置 │ 返回值     │ 参数                 │
                    |// │ 禁止位置 │ 参数       │ 返回值               │
                    |// │ 典型例子 │ List<out E>│ Comparable<in T>     │
                    |// └──────────┴────────────┴──────────────────────┘
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  使用处型变（与 Java 一致）
                    |// ════════════════════════════════════════════════════
                    |
                    |// Kotlin 也支持 Java 风格的使用处型变
                    |fun copyAll(source: MutableList<out Animal>,
                    |            dest: MutableList<in Animal>) {
                    |    // source: 只读 → out（生产者）
                    |    // dest:   只写 → in（消费者）
                    |    for (item in source) {
                    |        dest.add(item)
                    |    }
                    |}
                    |
                    |// 调用：
                    |val dogs: MutableList<Dog> = mutableListOf(Dog())
                    |val animals: MutableList<Animal> = mutableListOf()
                    |copyAll(dogs, animals)  // ✅ dogs as source (out), animals as dest (in)
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  记忆口诀：CIPO（Consumer In, Producer Out）
                    |// ════════════════════════════════════════════════════
                    |
                    |// 消费者 = in  （只吃不吐 → 只写）
                    |// 生产者 = out （只吐不吃 → 只读）
                    |
                    |// interface Consumer<in T>  { fun consume(t: T) }
                    |// interface Producer<out T> { fun produce(): T }
                    """.trimMargin(),
                keyPoints = listOf(
                    "`out T` ↔ Java `? extends T` ↔ 生产者 ↔ 只读 ↔ 协变",
                    "`in T` ↔ Java `? super T` ↔ 消费者 ↔ 只写 ↔ 逆变",
                    "Kotlin 的型变在**定义处**声明（类作者决定），Java 在**使用处**声明（调用者决定）",
                    "既读又写 → 不加修饰符（不变）",
                    "记忆口诀：**CIPO — Consumer In, Producer Out**",
                    "`List<out E>` 是只读的，所以可以用 out；`MutableList<E>` 可读写所以不变"
                ),
                note = "型变是 Kotlin 泛型中最抽象的概念。不用一次理解透——先记住 `List<out E>` 和 `Comparable<in T>` 两个标准库例子，用多了自然理解。"
            )
        ),
        pageNote = """
            |## ⭐ out / in 速查
            |
            || 场景 | 修饰符 | 口诀 |
            ||------|--------|------|
            || 只读取泛型值（返回值） | `out T` | **生产者 out** |
            || 只写入泛型值（参数） | `in T` | **消费者 in** |
            || 读写都有 | 无修饰符 | 不变 |
            |
            |👉 `out T` 实际应用 → [sealed class 实战](app:sealed-network) · [类修饰符](app:class-modifiers)
            |👉 CIPO 口诀 (Consumer In, Producer Out) → [高阶函数](app:higher-order)
            """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  Kotlin 循环语法（1 个页面，7 个 section）
    // ═══════════════════════════════════════════════════════════════════════════

    val loopsPage = KnowledgePage(
        id = "loops",
        category = "循环",
        title = "7 种循环 — .. / until / downTo / step / repeat / in / 解构遍历",
        briefDesc = "7 种 Kotlin 循环方式：点点递增 / until / downTo / step / repeat / 遍历 / 解构遍历",
        iconText = "循",
        iconColor = "#5C6BC0",
        overview = """
            |Kotlin 的循环语法比 Java 更简洁、更表达力强。
            |下表覆盖了 7 种常用循环方式：
            |
            || 方式 | 语法 | 等价 Java |
            ||------|------|----------|
            || ① 点点递增 | `for (i in 0..10)` | `for (int i = 0; i <= 10; i++)` |
            || ② until 递增 | `for (i in 0 until 10)` | `for (int i = 0; i < 10; i++)` |
            || ③ downTo 递减 | `for (i in 10 downTo 0)` | `for (int i = 10; i >= 0; i--)` |
            || ④ step 间隔 | `for (i in 0..10 step 2)` | `for (int i = 0; i <= 10; i+=2)` |
            || ⑤ repeat 重复 | `repeat(5) { }` | 无直接等价（最简单的循环 N 次） |
            || ⑥ list 遍历 | `for (item in list)` | `for (T item : list)` |
            || ⑦ 解构遍历 | `for ((i, v) in list.withIndex())` | `for (int i = 0; i < list.size(); i++)` |
        """.trimMargin(),
        sections = listOf(

            // ── Section 1：点点递增 ──
            ContentSection(
                title = "① .. — 点点递增（包含终点）",
                description = """
                    |`..` 是 Kotlin 的**范围操作符**，创建一个闭区间 `[start, end]`。
                    |
                    |`for (i in 0..10)` 读作「i 从 0 到 10（含）」。
                    |
                    |与 Java 的关键区别：Java 的 `for (int i = 0; i <= 10; i++)` 需要分三部分写，
                    |Kotlin 一行搞定。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  .. 范围操作符 — 闭区间 [start, end]
                    |// ════════════════════════════════════════════════════
                    |
                    |// 基础用法：0 到 5（包含 0 和 5）
                    |for (i in 0..5) {
                    |    println("第 ${'$'}i 次")   // 0, 1, 2, 3, 4, 5
                    |}
                    |
                    |// 等价 Java：
                    |// for (int i = 0; i <= 5; i++)
                    |
                    |
                    |// Kotlin 的 .. 也可以用于字符
                    |for (ch in 'a'..'e') {
                    |    print("${'$'}ch ")       // a b c d e
                    |}
                    |
                    |
                    |// 可以判断某个值是否在范围内：
                    |val age = 25
                    |if (age in 18..60) {
                    |    println("成年人")        // ✅ 25 在 [18, 60] 内
                    |}
                    """.trimMargin(),
                keyPoints = listOf(
                    "`a..b` = 闭区间 `[a, b]`，包含两端",
                    "`for (i in 0..5)` = 0 到 5（共 6 次）",
                    "也可用于字符：`'a'..'z'`",
                    "配合 `in` 关键字判断是否在范围内"
                )
            ),

            // ── Section 2：until ──
            ContentSection(
                title = "② until — 递增（不含终点）",
                description = """
                    |`until` 创建一个**左闭右开区间** `[start, end)`。
                    |等价于 Java 中最常见的 `for (int i = 0; i < n; i++)`。
                    |
                    |`until` 比 `..` 更适合数组/列表的下标遍历（因为列表下标是 0 到 size-1）。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  until — 左闭右开 [start, end)
                    |// ════════════════════════════════════════════════════
                    |
                    |// 0 到 4（不包含 5）
                    |for (i in 0 until 5) {
                    |    println(i)   // 0, 1, 2, 3, 4
                    |}
                    |
                    |// 等价 Java：
                    |// for (int i = 0; i < 5; i++)
                    |
                    |
                    |// 最常用：遍历列表下标
                    |val items = listOf("A", "B", "C", "D")
                    |for (i in 0 until items.size) {
                    |    println("第 ${'$'}i 个: ${'$'}{items[i]}")
                    |}
                    |// 第 0 个: A
                    |// 第 1 个: B
                    |// 第 2 个: C
                    |// 第 3 个: D
                    |
                    |
                    |// .. vs until 对比
                    |// for (i in 0..5)     → 0,1,2,3,4,5  (6 次)
                    |// for (i in 0 until 5) → 0,1,2,3,4    (5 次)
                    """.trimMargin(),
                keyPoints = listOf(
                    "`a until b` = 左闭右开 `[a, b)`",
                    "`for (i in 0 until n)` = Java 的 `for (int i = 0; i < n; i++)`",
                    "遍历列表下标的首选方式",
                    "比 `0..size-1` 更易读"
                )
            ),

            // ── Section 3：downTo ──
            ContentSection(
                title = "③ downTo — 递减循环",
                description = """
                    |`downTo` 创建一个**递减区间**，从大到小遍历。
                    |等价于 Java 的 `for (int i = n; i >= 0; i--)`。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  downTo — 递减遍历
                    |// ════════════════════════════════════════════════════
                    |
                    |// 从 5 倒数到 0
                    |for (i in 5 downTo 0) {
                    |    println("倒计时: ${'$'}i")  // 5, 4, 3, 2, 1, 0
                    |}
                    |
                    |// 等价 Java：
                    |// for (int i = 5; i >= 0; i--)
                    |
                    |
                    |// 实际场景：倒序遍历列表
                    |val items = listOf("A", "B", "C")
                    |for (i in items.size - 1 downTo 0) {
                    |    println("倒序: ${'$'}{items[i]}")
                    |}
                    |// 倒序: C → B → A
                    |
                    |
                    |// 从大到小的范围也可以用 in 判断
                    |if (3 in 5 downTo 1) println("3 在范围内")  // ✅
                    """.trimMargin(),
                keyPoints = listOf(
                    "`a downTo b` = 从 a 递减到 b（含两端）",
                    "Java 的 `i--` 在 Kotlin 中就是 `downTo`",
                    "也可配合 in 做范围判断"
                )
            ),

            // ── Section 4：step ──
            ContentSection(
                title = "④ step — 间隔循环",
                description = """
                    |`step` 指定每次循环的**步长**。可以和 `..`、`until`、`downTo` 组合使用。
                    |等价于 Java 的 `for (int i = 0; i <= 10; i += 2)`。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  step — 设置步长
                    |// ════════════════════════════════════════════════════
                    |
                    |// 正向 + 步长 2：0, 2, 4, 6, 8, 10
                    |for (i in 0..10 step 2) {
                    |    print("${'$'}i ")   // 0 2 4 6 8 10
                    |}
                    |
                    |// 递减 + 步长 3：9, 6, 3, 0
                    |for (i in 9 downTo 0 step 3) {
                    |    print("${'$'}i ")   // 9 6 3 0
                    |}
                    |
                    |// until + 步长：0, 3, 6（不包含 9）
                    |for (i in 0 until 9 step 3) {
                    |    print("${'$'}i ")   // 0 3 6
                    |}
                    |
                    |
                    |// step 必须是正数，不能为 0 或负数
                    |// for (i in 0..10 step -1)  ← ❌ 编译错误
                    |// 负数步长用 downTo 代替
                    """.trimMargin(),
                keyPoints = listOf(
                    "`step N` 配合 `..`、`until`、`downTo` 使用",
                    "步长必须是正数",
                    "要负数步长 → 用 `downTo`",
                    "等价 Java：`for (int i = 0; i < 10; i += N)`"
                )
            ),

            // ── Section 5：repeat ──
            ContentSection(
                title = "⑤ repeat — 重复执行 N 次",
                description = """
                    |`repeat(n) { }` 是最简洁的「做 N 次」循环。不需要下标变量，不需要范围。
                    |它是一个标准库函数（非关键字），内部就是 for 循环的封装。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  repeat — 最简单的 N 次循环
                    |// ════════════════════════════════════════════════════
                    |
                    |// 打印 3 次（it 从 0 开始计数）
                    |repeat(3) {
                    |    println("第 ${'$'}it 次")  // 第 0 次 / 第 1 次 / 第 2 次
                    |}
                    |
                    |// 如果不需要计数，可以忽略 it：
                    |repeat(5) {
                    |    println("Hello!")
                    |}
                    |
                    |
                    |// repeat 的实际场景：生成测试数据
                    |val testUsers = mutableListOf<User>()
                    |repeat(10) { index ->
                    |    testUsers.add(User("用户${'$'}index", index * 10))
                    |}
                    |
                    |
                    |// repeat 本质就是内联函数，编译后等价于：
                    |// for (i in 0 until n) { action(i) }
                    |
                    |// 源码定义：
                    |// inline fun repeat(times: Int, action: (Int) -> Unit) {
                    |//     for (index in 0 until times) {
                    |//         action(index)
                    |//     }
                    |// }
                    """.trimMargin(),
                keyPoints = listOf(
                    "`repeat(n) { }` = 执行 n 次，it 从 0 到 n-1",
                    "不需要控制循环变量时最简洁",
                    "内联函数，零性能开销",
                    "适合生成测试数据、批量初始化等场景"
                )
            ),

            // ── Section 6：list 遍历 ──
            ContentSection(
                title = "⑥ in — 遍历集合元素",
                description = """
                    |`for (item in list)` 是 Kotlin 最常用的遍历方式，等价于 Java 的 for-each。
                    |适用于所有实现了 `Iterable` 接口的类型：List、Set、Array、Map 等。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  遍历 List
                    |// ════════════════════════════════════════════════════
                    |
                    |val names = listOf("张三", "李四", "王五")
                    |for (name in names) {
                    |    println(name)
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  遍历 Map
                    |// ════════════════════════════════════════════════════
                    |
                    |val map = mapOf("a" to 1, "b" to 2, "c" to 3)
                    |for ((key, value) in map) {
                    |    println("${'$'}key → ${'$'}value")
                    |}
                    |// a → 1, b → 2, c → 3
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  遍历 Array
                    |// ════════════════════════════════════════════════════
                    |
                    |val arr = arrayOf("A", "B", "C")
                    |for (s in arr) {
                    |    println(s)
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  加上下标：withIndex()
                    |// ════════════════════════════════════════════════════
                    |
                    |for ((index, value) in names.withIndex()) {
                    |    println("${'$'}index: ${'$'}value")
                    |}
                    |// 0: 张三, 1: 李四, 2: 王五
                    """.trimMargin(),
                keyPoints = listOf(
                    "`for (item in list)` = Java for-each",
                    "Map 支持 `for ((k, v) in map)` 解构",
                    "`withIndex()` 同时获取下标和值",
                    "适用于所有 Iterable 类型"
                )
            ),

            // ── Section 7：解构遍历 ──
            ContentSection(
                title = "⑦ 解构遍历 — withIndex / forEachIndexed",
                description = """
                    |Kotlin 的**解构声明**让遍历代码更加优雅。同时获取下标和元素，一行搞定。
                    |
                    |两种方式：
                    |- `for ((i, v) in list.withIndex())` — for 循环风格
                    |- `list.forEachIndexed { i, v -> }` — 函数式 Lambda 风格
                    |
                    |👉 解构声明依赖 data class 的 `componentN()` 函数，见 [类修饰符](app:class-modifiers) 的 data class。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  方式 A：withIndex() + for 循环
                    |// ════════════════════════════════════════════════════
                    |
                    |val fruits = listOf("苹果", "香蕉", "橘子", "葡萄")
                    |
                    |// 解构：index 是下标，fruit 是元素
                    |for ((index, fruit) in fruits.withIndex()) {
                    |    println("#${'$'}index → ${'$'}fruit")
                    |}
                    |// #0 → 苹果
                    |// #1 → 香蕉
                    |// #2 → 橘子
                    |// #3 → 葡萄
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  方式 B：forEachIndexed — 函数式风格
                    |// ════════════════════════════════════════════════════
                    |
                    |fruits.forEachIndexed { index, fruit ->
                    |    println("第 ${'$'}index 个是 ${'$'}fruit")
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  进阶：配合 filter / map 链式操作
                    |// ════════════════════════════════════════════════════
                    |
                    |val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                    |
                    |numbers
                    |    .filter { it % 2 == 0 }       // 只留偶数
                    |    .forEachIndexed { i, n ->     // 遍历 + 下标
                    |        println("第 ${'$'}i 个偶数: ${'$'}n")
                    |    }
                    |// 第 0 个偶数: 2
                    |// 第 1 个偶数: 4
                    |// ...
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  7 种循环方式汇总对比
                    |// ════════════════════════════════════════════════════
                    |
                    |// ① for (i in 1..5)           → 1,2,3,4,5
                    |// ② for (i in 1 until 5)      → 1,2,3,4
                    |// ③ for (i in 5 downTo 1)     → 5,4,3,2,1
                    |// ④ for (i in 1..5 step 2)    → 1,3,5
                    |// ⑤ repeat(5) { }             → 执行 5 次（it: 0~4）
                    |// ⑥ for (item in list)        → 依次取元素
                    |// ⑦ for ((i,v) in list.withIndex()) → 下标 + 元素
                    """.trimMargin(),
                keyPoints = listOf(
                    "`withIndex()` 将 List 转为 `IndexedValue` 序列，支持解构",
                    "`forEachIndexed { i, v -> }` 是函数式等价写法",
                    "解构 = Kotlin 的语法糖，依赖 `componentN()`",
                    "配合 `filter` / `map` 实现函数式链式操作",
                    "7 种循环覆盖了 Java 中所有常见循环模式"
                )
            )
        ),
        pageNote = """
            |## ⭐ 循环选择指南
            |
            || 需求 | 推荐 |
            ||------|------|
            || 遍历列表下标 | `for (i in 0 until list.size)` |
            || 包含终点的范围 | `for (i in a..b)` |
            || 倒数 | `for (i in n downTo 0)` |
            || 隔一个取一个 | `for (i in 0..n step 2)` |
            || 只执行 N 次 | `repeat(n) { }` |
            || 遍历元素不用下标 | `for (item in list)` |
            || 同时要下标和元素 | `for ((i, v) in list.withIndex())` |
            |
            |👉 循环 + 协程 bulk 并发 → [协程](app:coroutine)
            |👉 解构依赖 data class componentN() → [类修饰符](app:class-modifiers)
            |👉 `forEach` / `filter` 等集合操作 → [集合操作](app:collection-ops)
            |👉 `repeat` 是 inline 函数 → [内联函数](app:inline)
        """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  密封类网络请求（1 个页面，3 个 section）
    //  实战 sealed class 在 MVVM 网络层中的应用
    // ═══════════════════════════════════════════════════════════════════════════

    val sealedNetworkPage = KnowledgePage(
        id = "sealed-network",
        category = "密封类",
        title = "sealed class 实战 — 封装网络请求 Loading/Success/Error 三状态",
        briefDesc = "密封类封装网络三状态（Loading/Success/Error），配合 when 穷尽检查 + MVVM 架构",
        iconText = "密",
        iconColor = "#6A1B9A",
        overview = """
            |`sealed class` 最经典的实际应用：**封装网络请求的三种状态**。
            |
            |每个网络请求都有三种可能：正在加载、请求成功、请求失败。
            |用 sealed class 建模这三个状态，配合 `when` 的穷尽检查，
            |编译器保证你不会漏掉任何一个状态的处理。
            |
            || 状态 | sealed 子类 | 携带数据 | 子类类型 |
            ||------|-----------|---------|---------|
            || 加载中 | `Loading` | 无 | `object` 单例 |
            || 成功 | `Success<T>` | `T`（响应数据） | `data class` |
            || 失败 | `Error` | `Throwable` | `data class` |
            |
            |👉 完整可运行的代码 → 见 **MVVM Demo** 入口，其中 [PostRepository.kt](mvvm/data/repository/PostRepository.kt)
            |和 [PostListViewModel.kt](mvvm/ui/list/PostListViewModel.kt) 完整实现了本页介绍的架构。
        """.trimMargin(),
        sections = listOf(

            // ── Section 1：定义密封类 ──
            ContentSection(
                title = "① 定义 — sealed class 建模网络状态",
                description = """
                    |用 `sealed class` 定义三种网络状态，**所有子类必须在同一文件**。
                    |
                    |关键设计决策：
                    |- `Loading` 用 `object` 而非 `class` → 所有请求共用一个加载态实例
                    |- `Success<out T>` 用泛型 + out → 支持协变（见泛型章节）
                    |- `Error` 携带 `Throwable` → 保留完整异常信息
                    |
                    |👉 `out T` 的协变含义 → 见 [泛型 out/in](app:generic-modifiers) 页面
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  定义：网络请求结果密封类
                    |// ════════════════════════════════════════════════════
                    |
                    |sealed class NetworkResult<out T> {
                    |    // 加载中 — object 单例，所有请求复用一个实例
                    |    object Loading : NetworkResult<Nothing>()
                    |
                    |    // 成功 — data class，携带实际数据
                    |    data class Success<out T>(val data: T) : NetworkResult<T>()
                    |
                    |    // 失败 — data class，携带异常信息
                    |    data class Error(val exception: Throwable) : NetworkResult<Nothing>()
                    |}
                    |
                    |// Nothing 是 Kotlin 的底部类型（所有类型的子类型）
                    |// NetworkResult<Nothing> 可以赋值给 NetworkResult<任意类型>
                    |// 配合 out 协变，Loading 和 Error 可以被任何 Success<T> 替代
                    """.trimMargin(),
                keyPoints = listOf(
                    "`sealed class` 限制子类必须在同一文件内",
                    "`object Loading` = 无状态单例（所有请求共享）",
                    "`data class Success<T>` = 携带任意类型的成功数据",
                    "`data class Error` = 携带异常对象",
                    "泛型 `out T` 让 Loading/Error 可以赋值给任何 `NetworkResult<T>`"
                )
            ),

            // ── Section 2：Repository 中使用 ──
            ContentSection(
                title = "② 使用 — Repository 中包装 API 调用",
                description = """
                    |Repository 层负责调用 API，用 `try-catch` 将原始响应或异常包装为 `NetworkResult`。
                    |
                    |上层（ViewModel）不需要知道数据来自 Retrofit、数据库还是缓存。
                    |也不需要自己处理异常——Repository 已经包好了。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  Repository：将 API 结果包装为密封类
                    |// ════════════════════════════════════════════════════
                    |
                    |class PostRepository {
                    |    private val api = RetrofitClient.postApi
                    |
                    |    // suspend 函数 — 在协程中调用
                    |    suspend fun getPosts(): NetworkResult<List<Post>> {
                    |        return try {
                    |            // ① 调用 Retrofit（自动在 IO 线程执行）
                    |            val posts = api.getPosts()
                    |            // ② 成功 → 包装为 Success
                    |            NetworkResult.Success(posts)
                    |        } catch (e: Exception) {
                    |            // ③ 失败 → 包装为 Error
                    |            // 网络异常 / JSON 解析错误 / 超时 等都走这里
                    |            NetworkResult.Error(e)
                    |        }
                    |    }
                    |}
                    |// 数据源细节被完全封装，ViewModel 只看到 NetworkResult
                    """.trimMargin(),
                keyPoints = listOf(
                    "`try-catch` 在 Repository 中统一处理所有异常",
                    "成功 → `NetworkResult.Success(data)`",
                    "失败 → `NetworkResult.Error(exception)`",
                    "ViewModel 不直接接触 Retrofit / OkHttp",
                    "Repository 可替换数据源（网络 → 本地数据库），ViewModel 无需改动"
                )
            ),

            // ── Section 3：ViewModel 中使用 ──
            ContentSection(
                title = "③ 消费 — when 穷尽检查处理三种状态",
                description = """
                    |ViewModel 中调用 Repository，用 `when` 处理所有状态。
                    |
                    |**这是 sealed class 最核心的价值**：
                    |编译器知道 `NetworkResult` 只有三个子类，`when` 必须覆盖所有分支。
                    |如果你漏掉 `Loading`、`Success` 或 `Error` 中的任意一个 → **编译警告**。
                    |
                    |这意味着你**永远不会**因为忘记处理某个状态而导致 UI 卡在加载中或崩溃。
                    |
                    |👉 **可运行的完整代码** → 点击主页的 **MVVM Demo** 卡片，
                    |查看 [PostListViewModel.kt](mvvm/ui/list/PostListViewModel.kt) 中的实际实现。
                    """.trimMargin(),
                sampleCode = """
                    |// ════════════════════════════════════════════════════
                    |//  ViewModel：用 when 处理三种状态
                    |// ════════════════════════════════════════════════════
                    |
                    |class PostListViewModel : ViewModel() {
                    |    private val _posts = MutableLiveData<List<Post>>()
                    |    val posts: LiveData<List<Post>> get() = _posts
                    |
                    |    private val _isLoading = MutableLiveData(false)
                    |    val isLoading: LiveData<Boolean> get() = _isLoading
                    |
                    |    private val _error = MutableLiveData<String?>()
                    |    val error: LiveData<String?> get() = _error
                    |
                    |    fun loadPosts() {
                    |        viewModelScope.launch {
                    |            _isLoading.value = true
                    |
                    |            // ── when 穷尽检查（sealed class 的核心价值）──
                    |            when (val result = repository.getPosts()) {
                    |                is NetworkResult.Loading -> {
                    |                    // 加载中 — 显示 spinner
                    |                }
                    |
                    |                is NetworkResult.Success -> {
                    |                    // 成功 — 更新列表
                    |                    _isLoading.value = false
                    |                    _posts.value = result.data  // ← 取出 Post 列表
                    |                }
                    |
                    |                is NetworkResult.Error -> {
                    |                    // 失败 — 显示错误
                    |                    _isLoading.value = false
                    |                    _error.value = result.exception.message
                    |                }
                    |                // ← 如果你漏掉任何一个分支，编译器报 warning！
                    |            }
                    |        }
                    |    }
                    |}
                    |
                    |
                    |// ════════════════════════════════════════════════════
                    |//  UI 层 (Activity) 的观察代码
                    |// ════════════════════════════════════════════════════
                    |
                    |// viewModel.isLoading.observe(this) { loading ->
                    |//     progressBar.isVisible = loading
                    |// }
                    |// viewModel.error.observe(this) { error ->
                    |//     error?.let { showError(it) }
                    |// }
                    |// viewModel.posts.observe(this) { posts ->
                    |//     adapter.submitList(posts)
                    |// }
                    """.trimMargin(),
                keyPoints = listOf(
                    "`when (result)` 必须覆盖所有 sealed class 子类",
                    "编译器穷尽检查 → 永远不会「忘记处理某个状态」",
                    "三个 LiveData 分别对应三种 UI 状态",
                    "`viewModelScope.launch` 在 ViewModel 清除时自动取消协程",
                    "完整的 MVVM 数据流：API → Repository → ViewModel → LiveData → UI"
                ),
                note = "sealed class+when 是 Kotlin 最强大的类型安全模式"
            )
        ),
        pageNote = """
            |## ⭐ 网络请求三层架构总结
            |
            |```
            |┌──────────────┐     ┌────────────────┐     ┌──────────────────┐
            |│  Retrofit    │ →   │  Repository    │ →   │  ViewModel       │
            |│  (网络层)    │     │  (数据仓库)    │     │  (业务逻辑)      │
            |│              │     │                │     │                  │
            |│  API 调用    │     │  try-catch     │     │  when (result) { │
            |│  JSON→Kotlin │     │  → Success     │     │    Loading → ... │
            |│              │     │  → Error       │     │    Success → ... │
            |│              │     │                │     │    Error   → ... │
            |└──────────────┘     └────────────────┘     │  }               │
            |                                           │  ↓ LiveData      │
            |                                           └──────┬───────────┘
            |                                                  ↓
            |                                           ┌──────────────────┐
            |                                           │  Activity        │
            |                                           │  (UI 层)         │
            |                                           │                  │
            |                                           │  observe()       │
            |                                           │  → 更新 UI       │
            |                                           └──────────────────┘
            |```
            |
            |👉 实际可运行的完整代码 → 点击主页的 **MVVM Demo** 卡片体验！
            |
            |## 🔗 关联知识点
            |👉 sealed class 定义 → [类修饰符](app:class-modifiers) · [Object](app:object)
            |👉 `when` 穷尽检查 → [when 表达式](app:when-expr)
            |👉 `out T` + `Nothing` 协变 → [泛型 out/in](app:generic-modifiers)
            |👉 `suspend` 挂起函数 → [协程](app:coroutine)
            |👉 Repository 中用 `try-catch` 包装 → [Flow](app:flow)
        """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  MVVM Demo 入口（点击跳转到真实的 API 列表页）
    // ═══════════════════════════════════════════════════════════════════════════

    val mvvmDemoPage = KnowledgePage(
        id = "mvvm-demo",
        category = "Demo",
        title = "MVVM Demo — 真实 API",
        briefDesc = "体验完整的 MVVM 架构：Retrofit 请求 JSONPlaceholder API → Repository 密封类 → ViewModel → LiveData → 列表+详情页",
        iconText = "⇄",
        iconColor = "#BF360C",
        overview = "此页面不在详情页中展示——点击卡片会直接打开一个**真实可交互的文章列表页**，从 JSONPlaceholder API 获取数据。",
        sections = emptyList()  // 不需要 section — 直接在 Activity 中处理
        // 【注意】KnowledgePageAdapter 的 category "Demo" 需要 tagBgMap 中配置
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  内联函数（1 个页面，4 个 section）
    //  inline / noinline / crossinline / reified
    // ═══════════════════════════════════════════════════════════════════════════

    val inlineFunctionsPage = KnowledgePage(
        id = "inline",
        category = "内联函数",
        title = "inline — 消除 Lambda 对象开销 / noinline / crossinline / reified",
        briefDesc = "inline / noinline / crossinline / reified。消除 Lambda 对象开销，编译时展开",
        iconText = "内", iconColor = "#C62828",
        overview = """
            |## Java 程序员需要知道的事
            |
            |在 Java 中，每次使用 Lambda 表达式，JVM 都会创建一个匿名内部类对象。
            |Kotlin 的 `inline` 让编译器把函数体直接复制到调用处，不创建对象——零开销！
            |
            || 修饰符 | 作用 | 通俗理解 |
            ||--------|------|---------|
            || `inline` | 函数体 + Lambda 都内联 | 「整个搬过去」 |
            || `noinline` | 标记某个 Lambda 不内联 | 「这个参数别搬」 |
            || `crossinline` | 禁止 Lambda 中 non-local return | 「不能从里面 return 出去」 |
            || `reified` | 泛型在运行时保留类型（必须配合 inline） | 「泛型不擦除」 |
        """.trimMargin(),
        sections = listOf(
            ContentSection(
                title = "① inline — 消除 Lambda 对象",
                description = """
                    |`inline` 函数在**编译时**把函数体直接复制到调用处，不生成额外的类文件。
                    |Kotlin 标准库中 `let`、`apply`、`run`、`also`、`repeat`、`forEach` 等高频函数全是 inline。
                    |👉 这些作用域函数都是 inline → 见 [let](app:let) 页面
                    """.trimMargin(),
                sampleCode = """
                    |// 定义一个 inline 函数（类似 synchronized 块）
                    |inline fun <T> lock(l: Any, block: () -> T): T {
                    |    synchronized(l) { return block() }
                    |}
                    |// 调用 — 编译后 block() 的代码直接展开在这里，不创建 Lambda 对象
                    |val result = lock(someLock) { sharedCounter++; 42 }
                    |// Java 的 Lambda 每次调用都 new 一个对象，inline 没有这个开销
                    """.trimMargin(),
                keyPoints = listOf("编译时展开 → 零 Lambda 对象开销", "Kotlin 标准库 let/apply/run 等都是 inline", "只对「高频调用 + 参数有 Lambda」的小函数使用"),
                note = "大函数加 inline 会让编译产物膨胀（代码体积变大）。只对高频调用的小函数加 inline。"
            ),
            ContentSection(
                title = "② noinline — 标记某个 Lambda 不内联",
                description = "默认 inline 函数的所有 Lambda 参数都会被内联。用 `noinline` 阻止某个参数被内联，当你需要把这个 Lambda 当对象传给其他非 inline 函数时用。",
                sampleCode = """
                    |inline fun doBoth(block1: () -> Unit, noinline block2: () -> Unit) {
                    |    block1()                // block1 被内联展开
                    |    saveToCache(block2)     // block2 不被内联 → 保留为对象 → 可以作为参数传递
                    |}
                    |fun saveToCache(callback: () -> Unit) { cache.put("k", callback) }
                    """.trimMargin(),
                keyPoints = listOf("`noinline` 阻止某个 Lambda 被内联 → 保留为对象", "当 Lambda 需要作为参数传给另一函数时使用", "使用场景不多")
            ),
            ContentSection(
                title = "③ crossinline — 禁止 non-local return",
                description = "`crossinline` 禁止在 Lambda 内直接用 `return`（非局部返回），只允许 `return@标签`。当 inline Lambda 可能在另一个上下文中被调用（如协程/Handler）时使用。",
                sampleCode = """
                    |inline fun postTask(crossinline block: () -> Unit) {
                    |    handler.post { block() }  // block 在别的线程执行
                    |    // 如果用普通 inline，block 里写 return 会直接退出调用者
                    |    // crossinline 禁止这事 → 编译器强制报错
                    |}
                    |// 调用时只能 return@postTask，不能直接 return
                    """.trimMargin(),
                keyPoints = listOf("禁止 Lambda 内的 `return`（non-local return）", "当 inline Lambda 被传给另一个执行上下文时使用")
            ),
            ContentSection(
                title = "④ reified — 泛型在运行时不被擦除",
                description = """
                    |**Java 程序员最需要的特性！** Java 泛型在运行时会类型擦除（`List<String>` 变成 `List`）。
                    |`reified` + `inline` 在编译展开时保留了 T 的真实类型，运行时可以 `T::class.java`！
                    |最经典：`Gson().fromJson<T>(json)` — 不需要传 `Class<T>` 参数。
                    """.trimMargin(),
                sampleCode = """
                    |// Java：Gson gson = new Gson(); User u = gson.fromJson(json, User.class);
                    |// ↑ 必须传 User.class（泛型擦除）
                    |
                    |// Kotlin + reified：不需要传 Class！
                    |inline fun <reified T> fromJson(json: String): T {
                    |    return Gson().fromJson(json, T::class.java)  // T 在运行时保留！
                    |}
                    |val user: User = fromJson(jsonStr)  // 编译器自动推断 T = User
                    """.trimMargin(),
                keyPoints = listOf("`reified` 必须配合 `inline`", "运行时泛型不被擦除 → 可用 `T::class`", "Java 做不到 → 这是 Kotlin 的独特优势", "常用于 Gson 反序列化、Intent 取 extra 等"),
                note = "无 reified → 需要手动传 Class 参数。有 reified → 编译器展开时知道具体类型，零额外参数。"
            )
        ),
        pageNote = """
            |## ⭐ inline 四兄弟速查
            || 修饰符 | 作用 | 何时用 |
            ||--------|------|--------|
            || `inline` | 展开函数体 | Lambda 参数高频调用时 |
            || `noinline` | 阻止某个 Lambda 展开 | 需要把 Lambda 当对象传递 |
            || `crossinline` | 禁止 return | Lambda 在别处被执行 |
            || `reified` | 泛型不擦除 | 运行时需要知道 T 的类型 |
            |👉 `apply`/`let`/`run` 都是 inline → 见 [let](app:let) 页面
            |
            |## 🔗 关联知识点
            |👉 `reified` + inline → 运行时泛型不擦除 → [泛型 out/in](app:generic-modifiers)
            |👉 `crossinline` 禁止 Lambda return → [高阶函数](app:higher-order)
            |👉 所有集合操作函数都是 inline → [集合操作](app:collection-ops)
        """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  操作符重载（1 个页面，3 个 section）
    // ═══════════════════════════════════════════════════════════════════════════

    val operatorPage = KnowledgePage(
        id = "operator",
        category = "操作符",
        title = "操作符 operator — 让自定义类支持 + - [] in () 等操作符",
        briefDesc = "让自定义类支持 + - [] in == > 等操作符。Java 做不到的事，Kotlin 轻松搞定",
        iconText = "符", iconColor = "#EF6C00",
        overview = """
            |## Java 程序员需要知道的事
            |Java**不支持**操作符重载。要给自定义类加运算只能定义 `add()`、`subtract()` 方法。
            |Kotlin 用 `operator` 关键字让任何类支持内置操作符语法。
            |
            || 操作符 | 对应函数 | 示例 |
            ||--------|---------|------|
            || `+` | `plus` | `a + b` → `a.plus(b)` |
            || `-` | `minus` | `a - b` |
            || `[]` 取值 | `get` | `a[i]` → `a.get(i)` |
            || `()` 调用 | `invoke` | `a()` → `a.invoke()` |
            || `in` | `contains` | `x in a` → `a.contains(x)` |
            || `>` `<` | `compareTo` | `a > b` → `a.compareTo(b) > 0` |
            || `==` | `equals` | data class 自动生成 |
        """.trimMargin(),
        sections = listOf(
            ContentSection(
                title = "① 算数与比较 — + - * / > <",
                description = "最常用的操作符重载。每个操作符对应一个固定名称的函数，用 `operator` 修饰。",
                sampleCode = """
                    |data class Money(val amount: Int) {
                    |    operator fun plus(other: Money) = Money(amount + other.amount)
                    |    operator fun minus(other: Money) = Money(amount - other.amount)
                    |    operator fun times(n: Int) = Money(amount * n)
                    |    operator fun compareTo(other: Money) = amount - other.amount // > < >= <=
                    |}
                    |val total = Money(100) + Money(50)  // 150，等价 Money(100).plus(Money(50))
                    |println(total > Money(30))            // true（compareTo 自动生效）
                    """.trimMargin(),
                keyPoints = listOf("`operator fun plus(...)` → `+` 可用", "每个操作符 = 一个固定名称的函数", "`compareTo` 实现后 `>` `<` `>=` `<=` 全可用", "编译后 `a+b` 就是 `a.plus(b)`，无性能开销")
            ),
            ContentSection(
                title = "② 下标与调用 — [] () in",
                description = "日常使用频率最高的三个：`get/set` → 像数组一样访问；`invoke` → 像函数一样调用；`contains` → `in` 关键字可用。",
                sampleCode = """
                    |// get/set：grid[r, c] 像二维数组
                    |class Grid(val rows: Int, val cols: Int) {
                    |    private val d = IntArray(rows * cols)
                    |    operator fun get(r: Int, c: Int) = d[r * cols + c]
                    |    operator fun set(r: Int, c: Int, v: Int) { d[r * cols + c] = v }
                    |}
                    |grid[0, 1] = 42           // 等价 grid.set(0, 1, 42)
                    |println(grid[0, 1])       // 等价 grid.get(0, 1)
                    |
                    |// invoke：让对象像函数一样调用
                    |class Greeter(val g: String) {
                    |    operator fun invoke(name: String) = println("${'$'}g, ${'$'}name!")
                    |}
                    |val hello = Greeter("你好")
                    |hello("张三")              // 等价 hello.invoke("张三")
                    |// Kotlin 中 () 就是调用 invoke()
                    """.trimMargin(),
                keyPoints = listOf("`operator fun get(i)` → `obj[i]`", "`operator fun set(i, v)` → `obj[i] = v`", "`operator fun invoke()` → `obj()`", "`operator fun contains(x)` → `x in obj`", "List 的 `list[0]` 就是用 get 实现的")
            ),
            ContentSection(
                title = "③ 实战模式 — 轻量 DSL",
                description = "操作符重载不是为了炫技，而是让 API 更自然。标准库大量使用 operator。",
                sampleCode = """
                    |// 模式1：自定义「范围」（in 关键字）
                    |class DateRange(val s: Int, val e: Int) {
                    |    operator fun contains(v: Int) = v in s..e
                    |}
                    |println(3 in DateRange(1, 5))  // true
                    |
                    |// 模式2：invoke + 带接收者 Lambda → 轻量 DSL
                    |class Builder {
                    |    private val actions = mutableListOf<String>()
                    |    operator fun invoke(block: Builder.() -> Unit): Builder { block(); return this }
                    |    fun add(a: String) { actions.add(a) }
                    |    fun build() = actions
                    |}
                    |val b = Builder()
                    |b { add("第一步"); add("第二步") }  // b.invoke { ... }
                    |println(b.build())  // [第一步, 第二步]
                    """.trimMargin(),
                keyPoints = listOf("`contains` → `in` 关键字可用于你的类", "`invoke` + 带接收者 Lambda → 构建简洁 DSL", "标准库 List/MutableList 的 `[]` 就是用 `get/set` operator 实现的")
            )
        ),
        pageNote = """
            |## ⭐ 操作符速查
            || 你想… | 重写哪个函数 | 语法 |
            ||--------|------------|------|
            || 相加 | `operator fun plus(o)` | `a + b` |
            || 下标 | `operator fun get(i)` | `a[i]` |
            || 像函数调用 | `operator fun invoke()` | `a()` |
            || 判断包含 | `operator fun contains(x)` | `x in a` |
            || 比较 | `operator fun compareTo(o)` | `a > b` |
            |
            |## 🔗 关联知识点
            |👉 `list[0]` = `list.get(0)` → [集合操作](app:collection-ops)
            |👉 `invoke()` = 像函数一样调用 → [高阶函数](app:higher-order)
            |👉 `..` 范围操作符 = `rangeTo()` → [循环](app:loops)
            |👉 `data class` 自动生成 `equals()` → [类修饰符](app:class-modifiers)
        """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  扩展函数与扩展属性（1 个页面，2 个 section）
    // ═══════════════════════════════════════════════════════════════════════════

    val extensionFunctionsPage = KnowledgePage(
        id = "extension",
        category = "扩展",
        title = "扩展 — fun Type.方法() / val Type.属性 给别人的类加功能",
        briefDesc = "给别人的类加方法/属性，不修改源码、不继承。fun Type.方法() / val Type.属性",
        iconText = "展", iconColor = "#2E7D32",
        overview = """
            |## Java 程序员需要知道的事
            |Java 中要给 `String` 加方法只能写 `StringUtils.isEmail(str)`。
            |Kotlin 让你写 `"abc@x.com".isEmail()` —— 就像 String 本来就有这方法一样。
            |编译后本质是静态方法——不修改原类字节码，只是语法更自然。
        """.trimMargin(),
        sections = listOf(
            ContentSection(
                title = "① 扩展函数 — fun Type.函数名()",
                description = "语法：`fun 被扩展的类型.方法名(参数): 返回值 { 函数体 }`。函数体内 `this` = 调用者对象。Android 最常用：`fun View.show()` 替代 ViewUtils 工具类。",
                sampleCode = """
                    |// 给 String 加方法（等价 Java StringUtils）
                    |fun String.isEmail(): Boolean = this.contains("@") && this.contains(".")
                    |fun String.mask(): String = if (length <= 4) "****" else take(2) + "****" + takeLast(2)
                    |println("test@x.com".isEmail())  // true
                    |println("13812345678".mask())     // 13****78
                    |
                    |// Android 最常用：View 扩展
                    |fun View.show() { visibility = View.VISIBLE }
                    |fun View.hide() { visibility = View.GONE }
                    |// 然后任意 View 直接 .show() .hide()，不用 ViewUtils 工具类
                    """.trimMargin(),
                keyPoints = listOf("`fun Type.方法()` = 给 Type 加方法", "函数体内 `this` = 调用者", "编译后变成静态方法，不修改原类", "不能访问 private 成员", "Android 常用：`fun View.show()` 替代 Utils 类")
            ),
            ContentSection(
                title = "② 扩展属性 — val Type.属性名",
                description = "只能定义计算属性（getter/setter），不能有背后存储字段。因为编译后是静态 getter/setter 方法。",
                sampleCode = """
                    |// 只读扩展属性
                    |val Context.screenWidth: Int
                    |    get() = resources.displayMetrics.widthPixels
                    |// val w = context.screenWidth ← 像 Context 自带的属性
                    |
                    |// 读写扩展属性
                    |var View.isGone: Boolean
                    |    get() = visibility == View.GONE
                    |    set(v) { visibility = if (v) View.GONE else View.VISIBLE }
                    |// binding.progress.isGone = true  → 等价 setter
                    |
                    |// var String.myProp: Int = 0  ← ❌ 编译错误！扩展属性不能有 backing field
                    """.trimMargin(),
                keyPoints = listOf("`val Type.属性: T get() = ...` = 只读扩展属性", "`var Type.属性: T get/set` = 读写扩展属性", "不能有 backing field → 只能是计算属性", "编译后本质是静态 getter/setter")
            )
        ),
        pageNote = """
            |Kotlin 扩展 = Java 静态工具方法 + 语法糖。
            |
            |## 🔗 关联知识点
            |👉 `let`/`apply` 等作用域函数就是扩展函数 → [let](app:let) · [apply](app:apply)
            |👉 配合 `?.` 安全调用 → [空安全](app:nullsafety)
            |👉 `fun View.show()` 操作符替代 → [操作符 operator](app:operator)
            """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  高阶函数与 Lambda（1 个页面，4 个 section）
    // ═══════════════════════════════════════════════════════════════════════════

    val higherOrderPage = KnowledgePage(
        id = "higher-order",
        category = "高阶函数",
        title = "高阶函数 — (参数)->返回值 / Lambda / SAM 转换 / 函数引用",
        briefDesc = "函数作为参数/返回值、Lambda 表达式、SAM 转换、函数引用 ::。函数是一等公民",
        iconText = "高", iconColor = "#1565C0",
        overview = """
            |## Java 程序员需要知道的事
            |Java 8 有 Lambda 和函数式接口，但 Lambda 本质上还是一个接口实例。
            |Kotlin 中**函数是一等公民**：可以赋值给变量、作为参数、作为返回值。
            || Java | Kotlin | 语法 |
            ||------|--------|------|
            || `Function<T,R>` | 函数类型 | `(T) -> R` |
            || Lambda 表达式 | Lambda | `{ x -> x * 2 }` |
            || `String::length` | 函数引用 | `String::length` |
            || `@FunctionalInterface` | SAM 转换 | 自动的 |
        """.trimMargin(),
        sections = listOf(
            ContentSection(
                title = "① 函数类型 — (参数) -> 返回值",
                description = "在 Kotlin 中，函数本身有类型。`(Int) -> String` 就是一种类型，可以赋值、传参、返回。",
                sampleCode = """
                    |val doubler: (Int) -> Int = { x -> x * 2 }     // 1参数
                    |val adder: (Int, Int) -> Int = { a, b -> a + b } // 2参数
                    |val action: () -> Unit = { println("执行") }     // 无参数
                    |// 带接收者的函数类型（DSL 用）
                    |val config: StringBuilder.() -> Unit = { append("Hello") }
                    |println(doubler(5))    // 10
                    |println(adder(3, 7))   // 10
                    """.trimMargin(),
                keyPoints = listOf("`(A, B) -> R` = 函数类型", "函数可以赋值、传参、返回 → 一等公民", "比 Java `Function<T,R>` 更简洁")
            ),
            ContentSection(
                title = "② Lambda 简化规则",
                description = "Kotlin Lambda 的 4 条简化规则（从最完整到最简洁）。",
                sampleCode = """
                    |val list = listOf(1,2,3,4,5)
                    |// 完整版：         list.filter({ item: Int -> item > 2 })
                    |// 尾随Lambda（最后参数放外面）： list.filter { item: Int -> item > 2 }
                    |// 省略类型：        list.filter { item -> item > 2 }
                    |// 单参数用 it：     list.filter { it > 2 }       ← 最常用！
                    |// 不用参数用 _：    map.forEach { (_, v) -> }    ← 忽略不用的参数
                    |// 最后一行 = 返回值：list.map { n -> n * 2; n + 1 }  返回 n+1
                    """.trimMargin(),
                keyPoints = listOf("尾随 Lambda：最后参数放 `()` 外面", "单参数默认名 `it`", "`_` = 忽略参数", "Lambda 最后一行 = 返回值（不需要 return）")
            ),
            ContentSection(
                title = "③ SAM 转换 + 函数引用",
                description = "SAM 转换：Java 单抽象方法接口 → 自动接受 Lambda。函数引用 :: → 把已有方法当 Lambda 传递。",
                sampleCode = """
                    |// SAM 转换：setOnClickListener 只需要一个方法 → 自动接受 Lambda
                    |button.setOnClickListener { v -> println("clicked") }
                    |// 等价 Java：button.setOnClickListener(v -> System.out.println("clicked"));
                    |
                    |// 函数引用 ::（四种形式）
                    |fun isPositive(n: Int) = n > 0
                    |listOf(-1,0,1,2).filter(::isPositive)   // 顶层函数引用
                    |listOf("a","bb").map(String::length)     // 成员引用
                    |val factory: (String) -> User = ::User   // 构造器引用
                    """.trimMargin(),
                keyPoints = listOf("SAM 转换：Java 单方法接口 → 自动 Lambda", "`::函数名` = 函数引用", "`::类名` = 构造器引用")
            ),
            ContentSection(
                title = "④ 高阶函数实战 — 三个经典模式",
                description = "高阶函数 = 参数或返回值是函数的函数。`let/apply/run` 本身就是高阶函数。👉 见 [let](app:let) 页面",
                sampleCode = """
                    |// 模式1：替代单方法接口回调
                    |fun View.onClick(action: (View) -> Unit) { setOnClickListener { action(it) } }
                    |
                    |// 模式2：资源管理（等价 Java try-with-resources）
                    |fun <T : Closeable> T.useIt(block: (T) -> Unit) { try { block(this) } finally { close() } }
                    |
                    |// 模式3：懒加载缓存（by lazy 的底层原理）
                    |fun <T> cache(provider: () -> T): () -> T {
                    |    var v: T? = null; return { if (v == null) v = provider(); v!! }
                    |}
                    """.trimMargin(),
                keyPoints = listOf("高阶函数 = 接收/返回函数的函数", "用 Lambda 替代单方法接口", "`let/apply/run/also` 全是高阶函数")
            )
        ),
        pageNote = """
            |## ⭐ Lambda 规则速查
            || 规则 | 示例 |
            ||------|------|
            || 最后参数 → 写外面 | `list.filter { }` |
            || 单参数 → it | `list.filter { it > 0 }` |
            || 未用 → _ | `map.forEach { (_, v) -> }` |
            || 最后行 = 返回值 | `list.map { 2 * it; it + 1 }` |
            |👉 函数类型 + 协程 = `suspend () -> T` → 见 [协程](app:coroutine)
            |
            |## 🔗 关联知识点
            |👉 `let`/`apply`/`run` 本身是高阶函数 → [let](app:let) · [apply](app:apply)
            |👉 `::函数名` = 函数引用 → 配合集合操作 [集合操作](app:collection-ops)
            |👉 SAM 转换 + `setOnClickListener { }` → MVVM Demo 实际应用 [MVVM Demo](app:mvvm-demo)
            |👉 inline 函数参数 Lambda → [内联函数](app:inline)
        """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  集合操作函数（1 个页面，2 个 section）
    // ═══════════════════════════════════════════════════════════════════════════

    val collectionOpsPage = KnowledgePage(
        id = "collection-ops",
        category = "集合",
        title = "集合操作 — map / filter / reduce / flatMap / groupBy / fold",
        briefDesc = "map / filter / reduce / flatMap / groupBy / fold。告别 Java Stream 的 .stream() .collect()",
        iconText = "集", iconColor = "#00838F",
        overview = """
            |## Java 程序员需要知道的事
            |Java: `stream.filter().map().collect(Collectors.toList())`
            |Kotlin: `list.filter { }.map { }` ← 不需要 stream()，不需要 collect()，函数全是 inline（零开销）
            || Kotlin | Java Stream | 说明 |
            ||--------|------------|------|
            || `filter { }` | `filter().collect(toList())` | 过滤 |
            || `map { }` | `map().collect(toList())` | 映射 |
            || `flatMap { }` | `flatMap().collect(toList())` | 展平 |
            || `fold(0) { }` | `reduce(0, (a,b)->a+b)` | 聚合 |
            || `groupBy { }` | `collect(groupingBy())` | 分组 |
        """.trimMargin(),
        sections = listOf(
            ContentSection(
                title = "① 映射与过滤 — map / filter / flatMap",
                description = "覆盖 80% 数据处理场景：filter = SQL WHERE、map = SQL SELECT、flatMap = 嵌套展平。",
                sampleCode = """
                    |val nums = listOf(1,2,3,4,5,6,7,8,9,10)
                    |val evens = nums.filter { it % 2 == 0 }             // [2,4,6,8,10]  ← 保留满足条件的
                    |val doubled = nums.map { it * 2 }                    // [2,4,6,...]     ← 每个元素转换
                    |val big = nums.filter { it > 3 }.map { it * 2 }     // [8,10,12,14,16,18,20] ← 链式！
                    |// filterNotNull = 自动去掉 null
                    |listOf("A", null, "B").filterNotNull()              // [A, B]
                    |// flatMap = 嵌套展平（订单→商品）
                    |orders.flatMap { it.items }                         // 所有订单的所有商品
                    """.trimMargin(),
                keyPoints = listOf("`filter { }` = 保留满足条件的 → SQL WHERE", "`map { }` = 元素转换 → SQL SELECT", "`flatMap { }` = 嵌套展平为一层", "可以链式：`.filter{ }.map{ }.filter{ }`", "全部是 inline 函数 → 零开销")
            ),
            ContentSection(
                title = "② 聚合与分组 — reduce / fold / groupBy",
                description = "聚合把集合「合并」为一个值，分组把集合「分类」为 Map。fold 比 reduce 更安全（有空集合兜底）。",
                sampleCode = """
                    |val nums = listOf(1,2,3,4,5)
                    |val sum = nums.reduce { acc, n -> acc + n }      // 15（逐步合并）
                    |// ⚠️ 空集合 reduce 会抛异常！用 fold：
                    |val safeSum = emptyList<Int>().fold(0) { a,b -> a+b } // 0
                    |
                    |// groupBy = 按条件分组 → Map<K, List<T>>
                    |data class P(val name: String, val city: String)
                    |val byCity = people.groupBy { it.city }
                    |// {北京=[P("张三"), P("王五")], 上海=[P("李四")]}
                    |
                    |// 常用判断 — 不用手写 for 循环：
                    |list.any { it > 0 }    // 是否有任意一个满足    ← 等价 Java anyMatch
                    |list.all { it > 0 }    // 是否全部满足          ← 等价 Java allMatch
                    |list.none { it > 0 }   // 是否全不满足          ← 等价 Java noneMatch
                    |list.firstOrNull { }   // 第一个满足的，没有则 null ← 安全！
                    |list.sortedBy { it }   // 按条件升序
                    |list.take(3)           // 取前 3 个
                    |list.distinct()        // 去重
                    """.trimMargin(),
                keyPoints = listOf("`reduce` → 逐步合并，空集合抛异常", "`fold(初始值) { }` → 比 reduce 安全", "`groupBy { key }` → `Map<K, List<T>>`", "`any/all/none` → 条件判断三件套", "`firstOrNull` → 找不到返回 null 不抛异常", "全部 inline → 零开销")
            )
        ),
        pageNote = """
            |## ⭐ 集合操作速查
            || 需求 | Kotlin | Java Stream |
            ||------|--------|------------|
            || 过滤 | `.filter { }` | `.filter().collect(toList())` |
            || 映射 | `.map { }` | `.map().collect(toList())` |
            || 聚合 | `.fold(0) { }` | `.reduce(0, (a,b)->a+b)` |
            || 分组 | `.groupBy { }` | `.collect(groupingBy())` |
            |> Kotlin：不需要 .stream()、不需要 .collect()、函数全是 inline（零开销）
            |
            |## 🔗 关联知识点
            |👉 所有集合操作都是 inline → [内联函数](app:inline)
            |👉 `for (item in list)` 循环遍历 → [循环](app:loops)
            |👉 `?.let { it.filter { } }` 链式组合 → [let](app:let) · [空安全](app:nullsafety)
            |👉 Flow 有同样的操作符语法 → [Flow](app:flow)
        """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  when 表达式（1 个页面，3 个 section）
    // ═══════════════════════════════════════════════════════════════════════════

    val whenExpressionPage = KnowledgePage(
        id = "when-expr",
        category = "分支",
        title = "when 表达式 — 增强版 switch、类型判断+智能转换+返回值",
        briefDesc = "Kotlin 增强版 switch：类型判断+智能转换+返回值+无参when。告别 break 和 NPE",
        iconText = "wh", iconColor = "#4E342E",
        overview = """
            |## Java 程序员需要知道的事
            |Java switch 三大痛点：1)只支持 int/String/enum 2)必须写 break，忘了就穿透 3)不是表达式
            |Kotlin when 全部解决：支持任意类型、不需 break、是表达式（可直接赋值）
        """.trimMargin(),
        sections = listOf(
            ContentSection(
                title = "① 基础 — when 替换 switch + if-else",
                description = "when 是表达式，可以直接赋值给变量。支持范围、多重匹配、任意表达式。",
                sampleCode = """
                    |// when 作为表达式 — 直接赋值！
                    |fun grade(score: Int) = when (score / 10) {
                    |    10, 9 -> "A"      // 多个值用逗号（Java 需要 case 10: case 9:）
                    |    8  -> "B"
                    |    7  -> "C"
                    |    6  -> "D"
                    |    else -> "F"       // = Java 的 default
                    |}
                    |println(grade(95))  // A
                    |
                    |// 无参 when — 替代 if-else if 链
                    |fun life(age: Int) = when {
                    |    age < 0  -> "未出生"
                    |    age < 13 -> "儿童"
                    |    age < 60 -> "成年"
                    |    else     -> "老年"
                    |}
                    """.trimMargin(),
                keyPoints = listOf("when 是**表达式** → 可以直接 `val x = when { }`", "多值匹配用逗号：`1, 2, 3 ->`", "不需要 `break`", "无参 when 替代 if-else if 链")
            ),
            ContentSection(
                title = "② 类型判断 — is + 智能转换（Java 做不到！）",
                description = "when + is = instanceof + 自动类型转换。这是比 Java switch 强最多的地方。",
                sampleCode = """
                    |fun describe(obj: Any): String = when (obj) {
                    |    is String  -> "字符串长度=${'$'}{obj.length}"   // obj 自动转为 String
                    |    is Int     -> "两倍=${'$'}{obj * 2}"          // obj 自动转为 Int
                    |    is Boolean -> if (obj) "true" else "false"
                    |    is List<*> -> "列表有${'$'}{obj.size}个元素"
                    |    else       -> "未知类型"
                    |}
                    |// 对比 Java：需要 instanceof + 强制转换 + 三元运算符，至少 10 行
                    |// 而且 Java switch 根本没法做这种事！
                    """.trimMargin(),
                keyPoints = listOf("`is Type` → 判断类型，分支内自动智能转换", "比 Java 的 `instanceof` + 强制转换简洁太多", "配合 sealed class → 编译器穷尽检查")
            ),
            ContentSection(
                title = "③ 实战 — 替代复杂的条件链",
                description = "Android MVVM 中最常见的 when 用法：处理 sealed class 的 UI 状态。👉 见 [sealed class 实战](app:sealed-network) 页面",
                sampleCode = """
                    |// when 处理 sealed class 网络状态（编译器保证不遗漏）
                    |when (val result = repository.getPosts()) {
                    |    is NetworkResult.Loading -> showLoading()
                    |    is NetworkResult.Success -> showList(result.data)
                    |    is NetworkResult.Error   -> showError(result.exception.message)
                    |} // 漏任何一个分支 → 编译器告警！
                    |
                    |// 多重条件组合
                    |fun discount(user: User) = when {
                    |    user.age < 0 || user.age > 150 -> error("无效年龄")
                    |    user.age < 12                  -> "儿童票半价"
                    |    user.isVip                     -> "VIP八折"
                    |    else                           -> "原价"
                    |}
                    """.trimMargin(),
                keyPoints = listOf("sealed class + when = 编译期穷尽检查", "无参 when 可组合任意复杂条件", "每个分支从上到下依次匹配")
            )
        ),
        pageNote = """
            |## ⭐ when vs Java switch
            || 特性 | Java | Kotlin |
            ||------|------|--------|
            || 匹配类型 | int/String/enum | 任意类型 |
            || break | 需要 | 不需要 |
            || 表达式 | ❌ | ✅ |
            || 类型判断 | ❌ | ✅ is + 智能转换 |
            || 范围 | ❌ | ✅ in 1..10 |
            |👉 when + sealed class 在 MVVM 中的用法 → [sealed class 实战](app:sealed-network)
            |
            |## 🔗 关联知识点
            |👉 `is Type` 智能转换 → [空安全](app:nullsafety) · [类修饰符](app:class-modifiers)
            |👉 无参 `when { }` 替代 if-else if → [高阶函数](app:higher-order)
            |👉 `data class` 解构 + when → [委托](app:delegation)
            |👉 ViewModel 中 when(result) 分支 → [MVVM Demo](app:mvvm-demo)
        """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  委托 by（1 个页面，3 个 section）
    // ═══════════════════════════════════════════════════════════════════════════

    val delegationPage = KnowledgePage(
        id = "delegation",
        category = "委托",
        title = "委托 by — lazy 懒加载 / observable 监听 / vetoable 校验 / 类装饰器",
        briefDesc = "by lazy / Delegates.observable / vetoable / 类委托。1 行替代 Java 10 行的模式代码",
        iconText = "委", iconColor = "#4A148C",
        overview = """
            |## Java 程序员需要知道的事
            |Java 写双重检查锁单例需要 10 行。Kotlin：`val x by lazy { ... }` 一行搞定。
            |Java 实现观察者模式需要 Observable + Observer 整套。Kotlin：`Delegates.observable()` 一行搞定。
            || 委托 | 关键字 | Java 等价 |
            ||------|--------|----------|
            || 懒加载 | `by lazy { }` | 双重检查锁单例 |
            || 可观察 | `Delegates.observable()` | Observable + Observer |
            || 校验 | `Delegates.vetoable()` | setter 校验 |
            || 类委托 | `class A(b: B) : I by b` | 装饰器模式 |
        """.trimMargin(),
        sections = listOf(
            ContentSection(
                title = "① by lazy — val 的延迟初始化",
                description = "第一次访问时初始化，只初始化一次，线程安全（默认 SYNCHRONIZED）。和 `lateinit var` 的对比：lazy 用于 val，lateinit 用于 var。👉 lateinit 见 [成员修饰符](app:member-modifiers)",
                sampleCode = """
                    |// Java 双重检查锁单例（10行）：
                    |// private volatile T instance;  ... synchronized(this) { ... } ...
                    |
                    |// Kotlin by lazy（1行）：
                    |val heavyData: String by lazy {
                    |    println("初始化...（只执行一次）")
                    |    Thread.sleep(1000)
                    |    "初始化完成"  // ← 最后一行是返回值
                    |}
                    |println(heavyData)  // 第一次 → 打印"初始化..." + 返回"初始化完成"
                    |println(heavyData)  // 第二次 → 直接返回缓存值，不打印
                    |// 线程安全！默认 SYNCHRONIZED 模式（等价 Java 双重检查锁）
                    """.trimMargin(),
                keyPoints = listOf("`val x by lazy { }` = 首次访问初始化，只一次", "线程安全：默认 SYNCHRONIZED", "只能用于 val", "Lambda 最后一行 = 返回值")
            ),
            ContentSection(
                title = "② Delegates.observable / vetoable — 属性监听器",
                description = "observable：属性变化时自动回调。vetoable：赋值前校验，不通过则拒绝。",
                sampleCode = """
                    |import kotlin.properties.Delegates
                    |
                    |class User {
                    |    // observable：值变化时自动回调（三个参数=属性,旧值,新值）
                    |    var name: String by Delegates.observable("<无>") { _, old, new ->
                    |        println("'${'$'}old' → '${'$'}new'")
                    |    }
                    |}
                    |val u = User(); u.name = "张三"; u.name = "李四"
                    |// '<无>' → '张三' / '张三' → '李四'
                    |
                    |// vetoable：返回 false = 拒绝这次修改
                    |class Account {
                    |    var balance: Int by Delegates.vetoable(0) { _, old, new ->
                    |        if (new < 0) { println("负数非法！保留${'$'}old"); false }
                    |        else true
                    |    }
                    |}
                    |val a = Account(); a.balance = 100; a.balance = -50
                    |println(a.balance)  // 100（-50 被拒绝）
                    """.trimMargin(),
                keyPoints = listOf("`observable(初始) { _, 旧, 新 -> }` → 变化时回调", "`vetoable(初始) { _, 旧, 新 -> 布尔 }` → 返回 false 拒绝修改", "比 Java Observable + Observer 简单太多")
            ),
            ContentSection(
                title = "③ 类委托 + viewModels + map 委托",
                description = "类委托 = 编译器自动生成装饰器转发代码；viewModels() = Android 委托实现；map 委托 = 属性从 Map 读取。",
                sampleCode = """
                    |// 类委托：实现装饰器模式不需要手写所有转发方法！
                    |interface Printer { fun print(msg: String); fun getHistory(): List<String> }
                    |class SimplePrinter : Printer { ... }
                    |class LoggingPrinter(base: Printer) : Printer by base {
                    |    override fun print(msg: String) { /* 只重写需要改的方法 */ }
                    |    // getHistory() 自动委托给 base，不需要手写转发！
                    |}
                    |
                    |// Android：val vm: MyVM by viewModels()  ← 委托隐藏了 ViewModelProvider 模板代码
                    |
                    |// Map 委托：属性 = map 的 value
                    |class Config(map: Map<String, Any>) { val host: String by map; val port: Int by map }
                    |val c = Config(mapOf("host" to "localhost", "port" to 8080))
                    |println(c.host)  // localhost（从 map 自动取）
                    """.trimMargin(),
                keyPoints = listOf("类委托 = 装饰器模式 → 只需重写要改的方法", "`viewModels()` 本身就是委托", "Map 委托 = 属性值自动关联 map key")
            )
        ),
        pageNote = """
            |## ⭐ by 委托速查
            || 想要... | 写法 |
            ||---------|------|
            || val 延迟初始化 | `val x by lazy { }` |
            || var 延迟初始化 | `lateinit var x` |
            || 属性变化通知 | `var x by Delegates.observable(v) { _,o,n -> }` |
            || 属性赋值校验 | `var x by Delegates.vetoable(v) { _,o,n -> bool }` |
            || 装饰器 | `class A(b:B): I by b` |
            |👉 lazy vs lateinit 选择 → [成员修饰符](app:member-modifiers)
            |
            |## 🔗 关联知识点
            |👉 `by lazy` 底层原理 = 高阶函数 + 缓存 → [高阶函数](app:higher-order)
            |👉 `viewModels()` 委托 → [MVVM Demo](app:mvvm-demo)
            |👉 类委托 = 装饰器模式 → [Object 页面](app:object) 的单例
            |👉 Flow collect 在 `repeatOnLifecycle` 中 → [Flow](app:flow)
        """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    //  Flow 响应式流（1 个页面，3 个 section）
    // ═══════════════════════════════════════════════════════════════════════════

    val flowPage = KnowledgePage(
        id = "flow",
        category = "Flow",
        title = "Flow — StateFlow 替代 LiveData / SharedFlow 一次性事件 / combine",
        briefDesc = "StateFlow / SharedFlow / collect / combine。LiveData 的协程升级版，RxJava 的轻量替代",
        iconText = "流", iconColor = "#0D47A1",
        overview = """
            |## Java 程序员需要知道的事
            |用过 RxJava → Flow 就是协程版的 Observable。用过 LiveData → Flow 就是支持 map/filter 的 LiveData。
            || 特性 | LiveData | Flow |
            ||------|---------|------|
            || 操作符(map/filter) | 需 Transformations | ✅ 丰富 |
            || 协程原生 | ❌ | ✅ |
            || 初始值 | 不要 | StateFlow 必须 |
            || 更适合 | 简单 UI | 复杂数据流 |
        """.trimMargin(),
        sections = listOf(
            ContentSection(
                title = "① StateFlow — MVVM 中替代 LiveData",
                description = "StateFlow 始终持有最新值（必须有初始值）。和 LiveData 的核心区别：StateFlow 需要初始值、不感知生命周期（需配合 repeatOnLifecycle）。",
                sampleCode = """
                    |class MyVM : ViewModel() {
                    |    data class UiState(val data: List<Post> = emptyList(), val loading: Boolean = true, val error: String? = null)
                    |    private val _state = MutableStateFlow(UiState())
                    |    val state: StateFlow<UiState> = _state.asStateFlow()  // 只读版
                    |    fun load() { viewModelScope.launch {
                    |        _state.update { it.copy(loading = true) }
                    |        try { val d = repo.getData(); _state.update { it.copy(data = d, loading = false) } }
                    |        catch (e: Exception) { _state.update { it.copy(error = e.message, loading = false) } }
                    |    }}
                    |}
                    |// Activity 中收集（lifecycle-safe）：
                    |// lifecycleScope.launch { repeatOnLifecycle(STARTED) { vm.state.collect { updateUI(it) } } }
                    """.trimMargin(),
                keyPoints = listOf("`MutableStateFlow(初始值)` = 可写热流", "`asStateFlow()` = 只读版", "`update { it.copy() }` = 原子更新", "StateFlow 必须有初始值（LiveData 可以不设）", "Activity 中用 `repeatOnLifecycle` 安全收集")
            ),
            ContentSection(
                title = "② SharedFlow — 一次性事件（Toast、导航）",
                description = "SharedFlow 用于一次性事件（Toast/SnackBar/导航），新订阅者不收到历史事件。和 StateFlow 的区别：StateFlow 持有值 → 适合 UI 状态；SharedFlow 不持有值 → 适合事件。",
                sampleCode = """
                    |class EventVM : ViewModel() {
                    |    private val _events = MutableSharedFlow<UiEvent>()
                    |    val events: SharedFlow<UiEvent> = _events.asSharedFlow()
                    |    fun onSave() { viewModelScope.launch { _events.emit(UiEvent.ShowToast("保存成功")) } }
                    |}
                    |sealed class UiEvent { data class ShowToast(val msg: String) : UiEvent(); object NavBack : UiEvent() }
                    |// 旋转屏幕后事件不会重复（和 StateFlow 的关键区别）
                    """.trimMargin(),
                keyPoints = listOf("`MutableSharedFlow()` = 一次性事件流", "新订阅者不收到历史事件", "`emit()` 是挂起函数", "用于 Toast/导航/SnackBar 等一次性事件")
            ),
            ContentSection(
                title = "③ Flow 操作符 — map / filter / combine",
                description = "Flow 操作符和集合操作语法一样，但 Flow 是异步的。combine 合并多个 Flow，任一变则自动重新计算。",
                sampleCode = """
                    |// 冷流构建器
                    |fun numbers(): Flow<Int> = flow { for (i in 1..5) { delay(500); emit(i) } }
                    |// 操作符链（和集合操作一样，但是异步的！）
                    |numbers().filter { it % 2 != 0 }.map { it * it }.collect { println(it) }  // 1, 9, 25
                    |
                    |// combine — 合并多个 Flow（LiveData 需要 MediatorLiveData 才能做到）
                    |val name = MutableStateFlow("张三"); val age = MutableStateFlow(25); val score = MutableStateFlow(0)
                    |val info: StateFlow<UserInfo> = combine(name, age, score) { n, a, s -> UserInfo(n, a, s) }
                    |    .stateIn(viewModelScope, SharingStarted.Eagerly, UserInfo("",0,0))
                    |// name/age/score 任一变化 → info 自动重新计算
                    |
                    |// 常用操作符：.filter/.map/.combine/.catch/.onStart/.onEach/.flowOn(IO)/.stateIn(scope)
                    """.trimMargin(),
                keyPoints = listOf("`flow { emit(x) }` = 冷流构建器", "操作符语法 = 集合操作语法，但异步执行", "`combine(a,b) { }` = 合并多流，任一变则重算", "`catch { }` = 异常处理", "操作符全是 inline → 零开销")
            )
        ),
        pageNote = """
            |## ⭐ StateFlow vs LiveData vs SharedFlow
            || 场景 | 推荐 |
            ||------|------|
            || UI 状态（有最新值） | StateFlow |
            || 一次性事件（Toast/导航） | SharedFlow |
            || 简单 UI 无需操作符 | LiveData |
            || 复杂数据流处理 | Flow |
            |👉 协程基础 → 见 [协程](app:coroutine) 页面
            |👉 sealed class 事件封装 → [sealed class 实战](app:sealed-network) 页面
            |
            |## 🔗 关联知识点
            |👉 Flow vs LiveData 的对比 + MVVM → [MVVM Demo](app:mvvm-demo)
            |👉 `collect` 在协程作用域中 → [协程](app:coroutine)
            |👉 `combine` / `filter` / `map` 等操作符 → [集合操作](app:collection-ops)
            |👉 `stateIn` + `shareIn` + `SharingStarted` → [委托](app:delegation)
        """.trimMargin()
    )
}
