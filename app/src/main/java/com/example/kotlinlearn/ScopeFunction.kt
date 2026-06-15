package com.example.kotlinlearn

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  数据模型：ScopeFunction                                                    ║
// ║                                                                             ║
// ║  这个文件定义了「作用域函数知识点」的数据结构，以及全部 5 个函数的静态数据。   ║
// ║  所有页面（列表 / 详情）都从这里取数据，实现「数据与 UI 分离」。              ║
// ╚══════════════════════════════════════════════════════════════════════════════╝

/**
 * ## 作用域函数的数据模型
 *
 * Kotlin 的 `data class` 会自动生成：
 * - `equals()` / `hashCode()` — 用于比较和集合操作
 * - `toString()` — 调试时可直接打印对象内容
 * - `copy()` — 创建副本的同时修改部分字段
 * - `componentN()` — 支持解构声明，如 `val (name, desc) = scopeFunction`
 *
 * ### 字段说明
 * @property name         函数名，如 "let"、"run"
 * @property briefDesc    一行简介，出现在主页列表卡片中
 * @property isExtension  是否为扩展函数（✅/❌）
 * @property contextRef   在 Lambda 内部如何引用上下文对象（this / it）
 * @property returnValue  返回值类型：Lambda 结果 / 对象本身
 * @property description  详细的多行解释说明
 * @property useCases     典型使用场景列表，详情页逐条渲染
 * @property sampleCode   可直接运行的示例代码（等宽字体展示）
 * @property keyPoints    关键要点列表
 * @property note         注意事项（警告提示样式）
 */
data class ScopeFunction(
    val name: String,
    val briefDesc: String,
    val isExtension: String,
    val contextRef: String,
    val returnValue: String,
    val description: String,
    val useCases: List<String>,
    val sampleCode: String,
    val keyPoints: List<String>,
    val note: String
)

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║  数据仓库：ScopeFunctionData                                                ║
// ║                                                                             ║
// ║  使用 Kotlin 的 `object` 关键字声明单例 —— 全局只有一个实例，                ║
// ║  等价于 Java 的「私有构造 + 静态 final 实例」模式，但写法更简洁。             ║
// ║                                                                             ║
// ║  为什么用 object 而不是 class？                                             ║
// ║  - 这些数据是静态的、无状态的，不需要多个实例                                 ║
// ║  - object 天然线程安全（JVM 类加载时初始化）                                  ║
// ║  - 直接通过 ScopeFunctionData.all 访问，不用写 getInstance()                  ║
// ╚══════════════════════════════════════════════════════════════════════════════╝
object ScopeFunctionData {

    /**
     * 全部作用域函数列表。
     *
     * 这里用 `listOf(...)` 而非 `mutableListOf(...)`：
     * - 返回的是只读 List，防止外部意外修改数据
     * - Kotlin 鼓励默认不可变（immutable-first）
     */
    val all: List<ScopeFunction> = listOf(let, run, with, apply, also)

    // ═══════════════════════════════════════════════════════════════════════════
    // let — 非空处理 + 链式转换
    // ═══════════════════════════════════════════════════════════════════════════

    val let = ScopeFunction(
        name = "let",

        // ── 简介（显示在主页卡片） ──
        briefDesc = "使用 it 引用对象，返回 Lambda 结果，配合 ?. 实现优雅的非空处理",

        // ── 三大属性（详情页顶部展示） ──
        isExtension = "✅ 是",
        contextRef = "it（可自定义命名）",
        returnValue = "Lambda 最后一行的结果",

        // ── 详细解释 ──
        description = """
            |let 是 Kotlin 中最常用的作用域函数之一。
            |
            |## 本质
            |let 是一个扩展函数，它接收一个 Lambda，在 Lambda 内部通过 `it` 引用调用对象，
            |Lambda 最后一行的值作为整个 let 表达式的返回值。
            |
            |## 核心优势
            |1. 配合 `?.` 安全调用操作符 → 只在非空时执行代码块
            |2. `it` 可以重命名为有意义的名字 → 提高代码可读性
            |3. 返回 Lambda 结果 → 适合管道式数据转换
            |
            |## 与其它函数的区别
            |- 与 also 相比：let 返回 Lambda 结果，also 返回对象本身
            |- 与 run 相比：let 用 it 引用，run 用 this 引用
            |- 与 apply 相比：let 用于转换，apply 用于配置
            """.trimMargin(),

        // ── 典型使用场景 ──
        useCases = listOf(
            "⭐ 最经典：`obj?.let { ... }` — 可空对象的安全处理",
            "数据转换流水线：`obj.let { }.let { }.let { }` — 链式映射",
            "限定变量作用域：在 let 块内创建的变量，外面无法访问，避免命名冲突",
            "结合 ?: 做空值兜底：`obj?.let { ... } ?: defaultValue`"
        ),

        // ── 示例代码 ──
        sampleCode = """
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 1：可空处理（let 最经典、最常用的场景）              ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |val name: String? = "Kotlin"        // 声明一个可空字符串
            |
            |// 如果 name 为 null，let 内部的代码根本不会执行
            |// 如果 name 不为 null，it 就是 name 的非空版本
            |val length: Int? = name?.let {
            |    println("名字不为空，值是: ${'$'}it")
            |    it.length              // ← 最后一行，作为 let 的返回值
            |}
            |println("字符串长度: ${'$'}length")  // 输出：字符串长度: 6
            |
            |// 对比：如果没有 let，传统写法需要 if-else：
            |val lengthOld = if (name != null) {
            |    println("名字不为空，值是: ${'$'}name")
            |    name.length             // ⚠️ 这里仍需写 name.length，没有智能转换
            |} else null
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 2：it 重命名，让代码语义更清晰                       ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |data class User(val name: String, val age: Int, val email: String)
            |
            |val user: User? = findUserById(1001)
            |
            |// it 可以显式重命名 → 嵌套时尤其有用
            |user?.let { u ->                     // 把 it 重命名为 u
            |    println("用户: ${'$'}{u.name}")
            |    println("年龄: ${'$'}{u.age}")
            |    println("邮箱: ${'$'}{u.email}")
            |}
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 3：链式数据转换（管道模式）                         ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8)
            |
            |// 每一步 let 的返回值都成为下一步的输入
            |val result = numbers
            |    .let { it.filter { n -> n > 3 } }    // 过滤 → [4,5,6,7,8]
            |    .let { it.map { n -> n * 2 } }       // 翻倍 → [8,10,12,14,16]
            |    .let { it.sum() }                     // 求和 → 60
            |
            |println("最终结果: ${'$'}result")  // 60
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 4：限定变量作用域，避免污染外层命名空间              ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |fun processFile(path: String) {
            |    java.io.File(path).let { file ->
            |        // file 只在这个 let 块内部可见
            |        println("文件名: ${'$'}{file.name}")
            |        println("大小: ${'$'}{file.length()} 字节")
            |        println("内容: ${'$'}{file.readText()}")
            |    }
            |    // 出了 let 块，file 变量就不存在了
            |    // 这样可以避免和外层的其他 file 变量冲突
            |}
            """.trimMargin(),

        // ── 关键要点 ──
        keyPoints = listOf(
            "Lambda 内通过 `it` 引用上下文对象（可显式重命名，如 `u ->`）",
            "返回值 = Lambda 块最后一行的表达式值",
            "配合 `?.` 是实现「非空才执行」的最佳实践",
            "适合数据转换管道：每一步的结果作为下一步的输入",
            "在嵌套作用域中不会遮蔽外部 `this`，比 `run` 更安全"
        ),

        // ── 注意事项 ──
        note = """
            |在嵌套作用域或多线程回调中，let 使用 it 而非 this，因此不会遮蔽外部类的 this。
            |
            |示例：如果在 Fragment 的 onCreate 中同时需要使用 Fragment 自身的 this 和
            |某个对象的 let，let 不会干扰：
            |```kotlin
            |val data = someObject?.let { obj ->
            |    requireActivity()  // ← 这里的 this 仍然是 Fragment
            |    obj.doSomething()
            |}
            |```
            |如果用 run 替代 let，内层的 this 会指向 someObject，导致 requireActivity() 无法访问。
            |结论：需要访问外层 this 时，优先用 let 而不是 run。
            """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    // run — 对象配置 + 返回计算结果
    // ═══════════════════════════════════════════════════════════════════════════

    val run = ScopeFunction(
        name = "run",

        briefDesc = "使用 this 引用对象（可省略），返回 Lambda 结果。兼具 with 的简洁和 let 的调用方式",

        isExtension = "✅ 是（也有非扩展版本）",
        contextRef = "this（可省略，直接访问成员）",
        returnValue = "Lambda 最后一行的结果",

        description = """
            |## 定位
            |run 是 `with` 和 `let` 的结合体：
            |- 像 `with` 一样使用 `this` 访问对象成员（this 可以省略）
            |- 像 `let` 一样是扩展函数，通过 `.` 号调用，可以配合 `?.`
            |
            |## 两种形态
            |1. **扩展 run**：`obj.run { ... }` — 在 obj 上下文中执行
            |2. **非扩展 run**：`run { ... }` — 需要多条语句计算一个值时使用
            |
            |## 什么时候用 run？
            |当你既需要修改对象、又需要返回一个计算结果时，run 是最佳选择。
            |如果只需要配置对象但不需要返回值 → 用 apply
            |如果只需要对对象做一堆操作 → 用 with
            """.trimMargin(),

        useCases = listOf(
            "⭐ 对象配置 + 返回计算结果：修改 Person 属性后返回描述信息",
            "配合 ?. 替代 with：`obj?.run { ... }`（with 做不到）",
            "非扩展 run：需要多条语句来初始化一个 val 变量",
            "Android 中一段逻辑的计算：如根据屏幕宽度计算列数"
        ),

        sampleCode = """
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 1：对象配置 + 返回计算结果（run 最核心的用法）      ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |data class Person(var name: String, var age: Int)
            |
            |// 创建一个 Person，然后用 run 修改它并返回一句话
            |val info = Person("张三", 20).run {
            |    // 下面直接访问 age 和 name，不需要写 this.
            |    age += 1                       // 张三大了一岁
            |    "${'$'}name 明年就 ${'$'}age 岁了！"  // ← 最后一行 = run 的返回值
            |}
            |println(info)  // 张三 明年就 21 岁了！
            |
            |// 如果你用 apply 来做同样的事：
            |val person = Person("张三", 20).apply { age += 1 }
            |// apply 返回的是 Person 对象本身，而不是字符串
            |// 要拿到 "张三 明年就 21 岁了！" 还得再取一次属性
            |// 这就是 run 和 apply 的核心区别
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 2：配合 ?. 安全调用（with 做不到这一点！）           ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |data class User(val name: String, val city: String)
            |
            |val user: User? = findUserById(1001)
            |
            |// run 是扩展函数，可以配合 ?. —— with 不行！
            |val greeting = user?.run {
            |    "Hello, ${'$'}name from ${'$'}city"  // ← this 省略，直接访问 name/city
            |} ?: "用户不存在"                       // ← 如果 user 为 null，走这里
            |
            |println(greeting)
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 3：非扩展 run — 需要多条语句来初始化一个 val        ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |// 当你需要一个 val（不可变变量），但它的值需要多步计算：
            |val rectangleInfo = run {
            |    val width = 100
            |    val height = 200
            |    val area = width * height
            |    val perimeter = 2 * (width + height)
            |    "矩形 ${'$'}width × ${'$'}height，面积 ${'$'}area，周长 ${'$'}perimeter"
            |}
            |// rectangleInfo 是 val，但计算过程涉及多个临时变量
            |// 这些临时变量（width/height/area/perimeter）不会泄漏到外层作用域
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 4：可变列表操作 + 统计结果                           ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |val list = mutableListOf(1, 2, 3)
            |val summary = list.run {
            |    add(4)
            |    add(5)
            |    removeAt(0)                // 删掉第一个元素
            |    "共 ${'$'}{size} 个元素，分别是 ${'$'}{joinToString(", ")}，和 = ${'$'}{sum()}"
            |}
            |println(summary)  // 共 4 个元素，分别是 2, 3, 4, 5，和 = 14
            """.trimMargin(),

        keyPoints = listOf(
            "在 Lambda 内用 `this` 引用对象，`this` 可以省略 → 直接访问属性和方法",
            "返回值 = Lambda 最后一行表达式的值（这是和 apply 的关键区别）",
            "扩展版本 `obj.run { }` 可以配合 `?.` 安全调用",
            "非扩展版本 `run { }` 用于「需要多条语句算出一个值，且这些中间变量不想泄漏」",
            "可以理解为 with 的升级版：调用方式更现代 + 支持可空"
        ),

        note = """
            |使用 this 的代价：嵌套 run 或与 with/apply 混用时，this 的指向容易混淆。
            |
            |```kotlin
            |outerObject.run {
            |    innerObject.run {
            |        // 这里的 this 指向 innerObject，不是 outerObject！
            |        // 如果同时需要访问 outerObject，用 this@OuterClass 显式指定
            |    }
            |}
            |```
            |结论：嵌套作用域中需要访问外层 this 时，使用 let 或 also（它们用 it 而非 this）。
            """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    // with — 批量操作同一个对象
    // ═══════════════════════════════════════════════════════════════════════════

    val with = ScopeFunction(
        name = "with",

        briefDesc = "非扩展函数，传入对象作为参数，用 this 访问其成员，返回 Lambda 结果",

        isExtension = "❌ 否（对象作为第一个参数传入）",
        contextRef = "this（可省略，直接访问成员）",
        returnValue = "Lambda 最后一行的结果",

        description = """
            |## 定位
            |with 是 5 个作用域函数中唯一一个**非扩展函数**。使用时把对象作为参数传入：
            |```kotlin
            |with(obj) { ... }
            |```
            |
            |在 Lambda 内部，obj 成为 `this`，你可以直接访问它的所有成员。
            |
            |## 语义
            |with(obj) 读作「**用**这个对象做以下这些事」。
            |它最适合的场景是：你已经有了一个对象，需要对它做很多操作。
            |
            |## 与其它函数的对比
            |- 和 run 的区别：with 不是扩展函数，不能配合 ?. 安全调用
            |- 和 apply 的区别：with 返回 Lambda 结果，apply 返回对象本身
            |- 和 let 的区别：with 用 this，let 用 it
            """.trimMargin(),

        useCases = listOf(
            "⭐ 对同一对象批量调用多个方法 / 属性",
            "RecyclerView Adapter 的 onBindViewHolder 中设置 UI 属性",
            "将对象作为「工具」来构建计算结果",
            "对控件的多属性设置（但 run 或 apply 通常更流行）"
        ),

        sampleCode = """
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 1：批量操作同一个对象（with 最经典的用法）          ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |val numbers = mutableListOf(10, 20, 30)
            |
            |with(numbers) {
            |    // 在这个块里，numbers 就是 this，所有成员可以直接访问
            |    add(40)                        // 等价于 numbers.add(40)
            |    add(50)                        // 等价于 numbers.add(50)
            |    removeAt(0)                    // 等价于 numbers.removeAt(0)
            |    println("当前元素: ${'$'}{joinToString(", ")}")
            |    println("总和: ${'$'}{sum()}")
            |    println("最大值: ${'$'}{max()}")
            |}
            |// 输出：
            |// 当前元素: 20, 30, 40, 50
            |// 总和: 140
            |// 最大值: 50
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 2：利用对象构建计算结果                              ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |data class Rectangle(val width: Int, val height: Int)
            |
            |val rect = Rectangle(100, 200)
            |
            |// with 返回 Lambda 最后一行的值
            |val description = with(rect) {
            |    "尺寸：${'$'}width × ${'$'}height = ${'$'}{width * height} 平方像素"
            |    // ↑ 最后一行，成为 description 的值
            |}
            |println(description)  // 尺寸：100 × 200 = 20000 平方像素
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 3：RecyclerView Adapter 中的实际应用                 ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |// 这个例子来自实际的 Android 开发场景
            |// override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            |//     val item = dataList[position]
            |//     with(holder.binding) {
            |//         tvTitle.text = item.title
            |//         tvSubtitle.text = item.subtitle
            |//         tvDate.text = formatDate(item.timestamp)
            |//         ivThumbnail.load(item.imageUrl)
            |//         root.setOnClickListener { onItemClick(item) }
            |//     }
            |// }
            |// 不用写 holder.binding.tvTitle、holder.binding.tvSubtitle ...
            |// 用 with 后代码更干净！
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 4：with 的返回值用法                                  ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |val data = mapOf("name" to "张三", "age" to 25, "city" to "北京")
            |
            |val info = with(data) {
            |    // 可以直接用 entries、keys、values 等 Map 的扩展属性
            |    val displayName = get("name")?.uppercase() ?: "未知"
            |    val displayAge = get("age") ?: 0
            |    val displayCity = get("city") ?: "未知"
            |    "姓名: ${'$'}displayName，年龄: ${'$'}displayAge，城市: ${'$'}displayCity"
            |}
            |println(info)  // 姓名: 张三，年龄: 25，城市: 北京
            """.trimMargin(),

        keyPoints = listOf(
            "唯一非扩展函数：对象作为第一个参数传入 `with(obj) { ... }`",
            "在块内用 `this` 引用传入的对象，`this` 可省略",
            "返回值 = Lambda 最后一行表达式的值",
            "语义：`with(obj)` 读作「用这个对象做…」",
            "❌ 不能配合 `?.` 安全调用（因为它不是扩展函数）→ 需要可空处理时用 run"
        ),

        note = """
            |with 最大的局限：**不能配合 `?.` 做安全调用**。
            |
            |```kotlin
            |val user: User? = findUser()
            |// with(user) { ... }  ← 如果 user 是 null 怎么办？with 没办法优雅处理
            |
            |// 替代方案：用 run
            |user?.run {
            |    println(name)     // 只在 user 非空时执行
            |}
            |```
            |结论：如果你需要对可空对象做批量操作 → 用 `obj?.run { }` 而不是 `with(obj)`。
            """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    // apply — 对象初始化配置
    // ═══════════════════════════════════════════════════════════════════════════

    val apply = ScopeFunction(
        name = "apply",

        briefDesc = "使用 this 引用对象，返回对象本身。专为对象初始化而生，Kotlin 版 Builder 模式",

        isExtension = "✅ 是",
        contextRef = "this（可省略，直接赋值属性）",
        returnValue = "调用对象本身 ⭐",

        description = """
            |## 定位
            |apply 是对象初始化的专属函数。它的设计哲学是：
            |**"将以下这些属性赋值应用到对象上"**
            |
            |## 本质特点
            |返回的是**调用对象本身**，而不是 Lambda 的结果！
            |这一点和 let / run / with 完全不同，是和 also 共有的特征。
            |
            |## 为什么用 apply 而不是构造函数？
            |1. Kotlin 没有 Java 的 Builder 模式语法糖
            |2. 构造函数参数过多时可读性差
            |3. apply 可以在构造后立即配置，代码清晰且链式友好
            |
            |## apply vs run vs also
            |- apply：用 this、返回对象本身 → 初始化配置
            |- run：用 this、返回 Lambda 结果 → 配置 + 计算
            |- also：用 it、返回对象本身 → 附加操作（不改对象）
            """.trimMargin(),

        useCases = listOf(
            "⭐ 最经典：对象创建后立即配置属性",
            "Android Intent / Bundle 的参数设置",
            "UI 控件属性的批量设置（TextView、Button 等）",
            "链式调用中的中间配置步骤",
            "替代 Java Builder 模式"
        ),

        sampleCode = """
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 1：对象初始化（apply 最核心的用法）                  ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |data class Person(
            |    var name: String = "",
            |    var age: Int = 0,
            |    var email: String = "",
            |    var city: String = ""
            |)
            |
            |// 不需要 Builder 模式！apply 搞定一切
            |val person = Person().apply {
            |    name = "李四"
            |    age = 25
            |    email = "lisi@example.com"
            |    city = "上海"
            |}
            |// person 变量 → 已经配置完成的 Person 对象
            |// apply 的返回值就是 person 本身！
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 2：Android Intent 配置（最常见的实际场景）          ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |// 不用 apply（传统写法，变量名反复出现）：
            |val intent = Intent(context, DetailActivity::class.java)
            |intent.putExtra("id", userId)
            |intent.putExtra("title", "详情页")
            |intent.putExtra("from", "MainActivity")
            |intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            |
            |// 用 apply（一句话搞定，intent 只写一次）：
            |val intentClean = Intent(context, DetailActivity::class.java).apply {
            |    putExtra("id", userId)
            |    putExtra("title", "详情页")
            |    putExtra("from", "MainActivity")
            |    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            |}
            |// intentClean 就是配置好的 Intent
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 3：UI 控件属性批量设置                              ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |// 不用 apply：
            |val textView = TextView(context)
            |textView.text = "欢迎使用 Kotlin"
            |textView.textSize = 16f
            |textView.setTextColor(Color.BLACK)
            |textView.gravity = Gravity.CENTER
            |textView.setPadding(16, 8, 16, 8)
            |
            |// 用 apply：
            |val textViewClean = TextView(context).apply {
            |    text = "欢迎使用 Kotlin"
            |    textSize = 16f
            |    setTextColor(Color.BLACK)
            |    gravity = Gravity.CENTER
            |    setPadding(16, 8, 16, 8)
            |}
            |// 对比：代码量减半，意图清晰
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 4：链式调用 — apply → apply → apply               ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |// 因为 apply 返回对象本身，所以可以无限链式调用
            |val result = mutableListOf<Int>()
            |    .apply { add(1); add(2) }           // 返回 list
            |    .apply { println("当前: ${'$'}this") }  // 返回 list（顺便打印）
            |    .apply { removeAt(0) }              // 返回 list
            |    .apply { println("修改后: ${'$'}this") } // 返回 list
            |// 每一步都返回同一个对象，链式操作毫无压力
            |// 注意：如果你需要每步返回不同的值，请用 let 而不是 apply！
            """.trimMargin(),

        keyPoints = listOf(
            "Lambda 内部用 `this` 引用对象（可省略），直接访问所有成员",
            "⭐ 返回值 = 调用对象本身（不是 Lambda 最后一行！这是新手最容易踩的坑）",
            "专为「对象初始化配置」设计 → Kotlin 版 Builder 模式",
            "链式友好：因为返回对象本身，可以 .apply{ }.apply{ } 一直链下去",
            "语义：读作「将以下属性应用到对象上」"
        ),

        note = """
            |最常见的误区：以为 apply 返回 Lambda 最后一行。
            |
            |```kotlin
            |val result = Person().apply {
            |    name = "王五"
            |    age = 30
            |    "这是一句话"   // ← 这句话会被忽略！不会赋值给 result
            |}
            |// result 是 Person 对象，不是字符串 "这是一句话"！
            |```
            |
            |如果你需要对象配置的同时返回一个计算结果 → 用 run 代替 apply。
            |如果你需要在配置完后顺便做点什么（不修改对象）→ 用 apply { }.also { }。
            """.trimMargin()
    )

    // ═══════════════════════════════════════════════════════════════════════════
    // also — 副作用操作（日志、验证）
    // ═══════════════════════════════════════════════════════════════════════════

    val also = ScopeFunction(
        name = "also",

        briefDesc = "使用 it 引用对象，返回对象本身。适合日志、验证等不修改对象本身的附加操作",

        isExtension = "✅ 是",
        contextRef = "it（可自定义命名）",
        returnValue = "调用对象本身 ⭐",

        description = """
            |## 定位
            |also 和 apply 一样返回对象本身，但使用 `it` 而非 `this` 来引用对象。
            |
            |它的设计哲学是：
            |**"并且用这个对象做以下这件事"**
            |
            |## 为什么选择 also 而不是 apply？
            |- also 用 it → 不遮蔽外部 this → 嵌套时更安全
            |- also 的语义明确代表「附加操作」，不改对象
            |- apply 的语义是「配置/修改对象」
            |
            |## 典型用途
            |所有「不改变对象本身的附加操作」：
            |打印日志、数据校验、赋值给外部变量、调试断点
            """.trimMargin(),

        useCases = listOf(
            "⭐ 最经典：对象创建后打印日志 `obj.also { Log.d(TAG, \"创建: ${'$'}it\") }`",
            "数据验证 / require 断言：在 also 块中检查对象状态",
            "赋值给外部变量：`val x = obj.also { externalVar = it }`",
            "链式调用中插入检查点：`.also { println(it) }.apply { ... }`",
            "需要访问外层 this 的嵌套代码块"
        ),

        sampleCode = """
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 1：日志打印（also 最经典、最常见的用法）            ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |data class User(val name: String, val age: Int)
            |
            |// 创建对象的同时打印日志 — 对象本身不受影响
            |val user = User("王五", 30).also {
            |    println("✨ 创建了一个用户：${'$'}it")
            |    // 输出：✨ 创建了一个用户：User(name=王五, age=30)
            |}
            |// user 仍然是 User("王五", 30)
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 2：数据验证 — 创建对象后立即检查合法性              ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |fun createUser(name: String, age: Int): User {
            |    return User(name, age).also {
            |        // require 在条件为 false 时抛出 IllegalArgumentException
            |        require(it.name.isNotBlank()) { "名字不能为空！" }
            |        require(it.age in 1..150) { "年龄必须在 1~150 之间！得到: ${'$'}{it.age}" }
            |        // 验证通过 → it（即 user 对象）被返回
            |    }
            |}
            |
            |// createUser("", 30)   → 抛异常：名字不能为空！
            |// createUser("张三", -5) → 抛异常：年龄必须在 1~150 之间！得到: -5
            |// createUser("张三", 25) → 正常返回 User("张三", 25)
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 3：链式调用中插入调试 / 检查点                      ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |val numbers = mutableListOf(1, 2, 3)
            |    .also { println("① 初始状态: ${'$'}it") }     // ① 初始状态: [1, 2, 3]
            |    .apply { add(4); add(5) }                    // mutate
            |    .also { println("② 添加元素: ${'$'}it") }     // ② 添加元素: [1, 2, 3, 4, 5]
            |    .apply { removeAt(0); removeAt(0) }          // mutate
            |    .also { println("③ 删除元素: ${'$'}it") }     // ③ 删除元素: [3, 4, 5]
            |    .apply { sort() }                            // mutate
            |    .also { println("④ 排序后: ${'$'}it") }       // ④ 排序后: [3, 4, 5]
            |
            |// 这种模式在调试数据流时极其有用！
            |// 每个 also 就像一个「检查站」，查看而不改变
            |
            |
            |// ╔══════════════════════════════════════════════════════════════╗
            |// ║  示例 4：赋值给外部变量 — 顺便保存引用                    ║
            |// ╚══════════════════════════════════════════════════════════════╝
            |
            |var latestUser: User? = null    // 外部变量，追踪最新创建的用户
            |
            |fun register(name: String, age: Int): User {
            |    return User(name, age).also {
            |        latestUser = it          // ← 顺便把引用存下来
            |        println("注册成功: ${'$'}it，当前已缓存")
            |    }
            |}
            |
            |val user1 = register("赵六", 28)
            |// latestUser 现在指向 user1
            |val user2 = register("钱七", 35)
            |// latestUser 现在指向 user2
            """.trimMargin(),

        keyPoints = listOf(
            "Lambda 内通过 `it` 引用上下文对象（可显式重命名）",
            "⭐ 返回值 = 调用对象本身（与 apply 相同，与 let/run 不同）",
            "语义：读作「并且用该对象做…」 — 代表附加、不修改的操作",
            "与 apply 的区别：also 用 `it`（不遮蔽外部 this），apply 用 `this`",
            "适合一切不改变对象的副作用：日志、验证、赋值、调试"
        ),

        note = """
            |also 使用 `it` 不会遮蔽外部 `this`，因此在多层嵌套中比 apply 更安全。
            |
            |```kotlin
            |class MyFragment : Fragment() {
            |    fun loadData() {
            |        val data = fetchData().also {
            |            // 这里的 this 仍然是 Fragment！
            |            // 可以安全调用 requireActivity()、requireContext() 等
            |            Log.d(TAG, "数据加载完成: ${'$'}it")
            |        }
            |        // 如果用 apply 代替 also，this 会指向 fetchData() 的返回值
            |        // requireActivity() 就会编译报错
            |    }
            |}
            |```
            |结论：在 Fragment / Activity 等需要访问 this 的场景中，用 also 而不是 apply。
            """.trimMargin()
    )
}
