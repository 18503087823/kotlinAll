package com.example.kotlinlearn

data class CodeExamplePage(
    val summary: String,
    val improvements: List<String>,
    val sampleTitle: String,
    val sampleCode: String,
    val note: String = ""
)

object CodeExampleData {
    fun hasPage(pageId: String): Boolean = pageFor(pageId) != null

    fun pageFor(pageId: String): CodeExamplePage? = when (pageId) {
        "let" -> CodeExamplePage(
            summary = "let 适合非空处理和链式转换，重点是让对象只在非空时进入作用域。",
            improvements = listOf(
                "用 `?.let {}` 代替手写空判断",
                "把 `it` 重命名成业务变量",
                "最后一行直接作为返回值"
            ),
            sampleTitle = "推荐写法：非空处理 + 结果回传",
            sampleCode = """
                data class User(val name: String, val age: Int)

                fun render(user: User?) {
                    val text = user?.let { u ->
                        "${'$'}{u.name} · ${'$'}{u.age}岁"
                    } ?: "游客"
                    println(text)
                }
            """.trimIndent()
        )
        "run" -> CodeExamplePage(
            summary = "run 适合“配置对象后顺手产出结果”的场景，既能访问成员，也能返回计算值。",
            improvements = listOf(
                "对象配置时直接写成员",
                "返回值来自块内最后一行",
                "可配合 `?.run {}` 做安全调用"
            ),
            sampleTitle = "推荐写法：配置对象并返回描述",
            sampleCode = """
                data class Person(var name: String, var age: Int)

                val label = Person("张三", 20).run {
                    age += 1
                    "${'$'}name 明年 ${'$'}age 岁"
                }
                println(label)
            """.trimIndent()
        )
        "with" -> CodeExamplePage(
            summary = "with 适合批量操作同一个对象，不需要反复写对象名。",
            improvements = listOf(
                "对象集中到一个作用域里处理",
                "适合构建字符串和批量调用",
                "返回值同样来自最后一行"
            ),
            sampleTitle = "推荐写法：批量构建结果",
            sampleCode = """
                val result = with(StringBuilder()) {
                    append("Kotlin")
                    append(" Learn")
                    toString()
                }
                println(result)
            """.trimIndent()
        )
        "apply" -> CodeExamplePage(
            summary = "apply 更适合初始化和配置对象，最后返回的还是对象本身。",
            improvements = listOf(
                "初始化属性时更清晰",
                "适合 Builder、Intent、Request",
                "对象本身可以继续链式使用"
            ),
            sampleTitle = "推荐写法：初始化配置",
            sampleCode = """
                data class Request(var url: String = "", var timeout: Int = 0)

                val request = Request().apply {
                    url = "https://example.com"
                    timeout = 5
                }
                println(request)
            """.trimIndent()
        )
        "also" -> CodeExamplePage(
            summary = "also 适合顺手做日志、校验、埋点等副作用，不打断主链路。",
            improvements = listOf(
                "主流程继续传递原对象",
                "把日志和调试逻辑拆出去",
                "适合观察中间状态"
            ),
            sampleTitle = "推荐写法：链路中插入副作用",
            sampleCode = """
                val ids = mutableListOf(1, 2, 3)
                    .also { println("before = ${'$'}it") }
                    .map { it * 10 }
                println(ids)
            """.trimIndent()
        )
        "object" -> CodeExamplePage(
            summary = "object 适合做单例、伴生对象和对象表达式，能把共享逻辑收拢起来。",
            improvements = listOf(
                "单例避免重复创建",
                "companion object 放工厂方法和常量",
                "对象表达式可直接实现接口"
            ),
            sampleTitle = "推荐写法：单例 + 工厂方法",
            sampleCode = """
                object TokenStore {
                    var token: String? = null
                }

                class ApiClient private constructor() {
                    companion object {
                        fun create(): ApiClient = ApiClient()
                    }
                }
            """.trimIndent()
        )
        "nullsafety" -> CodeExamplePage(
            summary = "空安全的目标是把 null 风险尽量前置处理，而不是让它扩散到后面。",
            improvements = listOf(
                "优先用 `?.` 和 `?:` 兜底",
                "链路太长时拆开变量",
                "只有非常确定时才用 `!!`"
            ),
            sampleTitle = "推荐写法：安全链 + 默认值",
            sampleCode = """
                val city = user
                    ?.profile
                    ?.address
                    ?.city
                    ?.takeIf { it.isNotBlank() }
                    ?: "未知"
                println(city)
            """.trimIndent()
        )
        "typealias" -> CodeExamplePage(
            summary = "typealias 适合给复杂类型起别名，让回调和数据结构更好读。",
            improvements = listOf(
                "减少长函数签名的噪音",
                "更适合 UI 回调和泛型类型",
                "语义比裸类型更清楚"
            ),
            sampleTitle = "推荐写法：给回调和 ID 起别名",
            sampleCode = """
                typealias OnItemClick = (Int) -> Unit
                typealias UserId = Long

                fun bind(onClick: OnItemClick) {
                    onClick(1)
                }
            """.trimIndent()
        )
        "coroutine" -> CodeExamplePage(
            summary = "协程更适合把异步操作写成顺序代码，重点是结构化并发和线程切换。",
            improvements = listOf(
                "IO 和主线程分工明确",
                "业务流程按顺序写，可读性更高",
                "viewModelScope 能自动跟随生命周期"
            ),
            sampleTitle = "推荐写法：IO 获取 + 主线程更新",
            sampleCode = """
                fun load() = viewModelScope.launch {
                    val posts = withContext(Dispatchers.IO) {
                        repo.loadPosts()
                    }
                    _uiState.value = UiState(posts = posts)
                }
            """.trimIndent()
        )
        "class-modifiers" -> CodeExamplePage(
            summary = "类修饰符主要解决继承、扩展和状态建模问题，常见的是 open、data、sealed、abstract。",
            improvements = listOf(
                "用 `sealed` 表达有限状态",
                "用 `data class` 表达纯数据",
                "用 `open`/`abstract` 提供扩展点"
            ),
            sampleTitle = "推荐写法：状态 + 数据模型",
            sampleCode = """
                open class BaseScreen

                data class User(val id: Long, val name: String)

                sealed class LoadState {
                    data object Loading : LoadState()
                    data class Success(val data: List<User>) : LoadState()
                    data class Error(val msg: String) : LoadState()
                }
            """.trimIndent()
        )
        "member-modifiers" -> CodeExamplePage(
            summary = "成员修饰符主要控制可见性、重写和延迟初始化。",
            improvements = listOf(
                "`private` 收窄暴露面",
                "`override` 明确重写关系",
                "`lateinit` 适合晚初始化属性"
            ),
            sampleTitle = "推荐写法：基类 + late init",
            sampleCode = """
                open class BaseFragment {
                    protected open fun title() = "Base"
                }

                class HomeFragment : BaseFragment() {
                    private lateinit var adapter: Any

                    override fun title() = "Home"
                }
            """.trimIndent()
        )
        "generic-modifiers" -> CodeExamplePage(
            summary = "泛型修饰符核心是协变 out、逆变 in，以及 reified 带来的类型保留。",
            improvements = listOf(
                "`out` 表示只产出，不消费",
                "`in` 表示只消费，不产出",
                "`reified` 让运行时还能拿到类型"
            ),
            sampleTitle = "推荐写法：生产者 / 消费者 / reified",
            sampleCode = """
                class Producer<out T>(private val value: T) {
                    fun get(): T = value
                }

                class Consumer<in T> {
                    fun put(item: T) {}
                }

                inline fun <reified T> fromJson(text: String): T? = null
            """.trimIndent()
        )
        "loops" -> CodeExamplePage(
            summary = "循环写法重点是让遍历意图清楚，尽量用 Kotlin 的范围和集合工具。",
            improvements = listOf(
                "`repeat` 用于固定次数",
                "`downTo` / `step` 表达步进",
                "`withIndex()` 更适合同时拿索引和值"
            ),
            sampleTitle = "推荐写法：遍历 + 步进",
            sampleCode = """
                val items = listOf("A", "B", "C")

                for ((index, item) in items.withIndex()) {
                    println("${'$'}index -> ${'$'}item")
                }

                repeat(3) { println("round ${'$'}it") }
                for (i in 10 downTo 0 step 2) println(i)
            """.trimIndent()
        )
        "sealed-network" -> CodeExamplePage(
            summary = "密封类适合封装网络状态，配合 when 可以把 loading、success、error 写成完整分支。",
            improvements = listOf(
                "状态类型固定，分支更安全",
                "when 能做穷尽检查",
                "UI 层只关心结果"
            ),
            sampleTitle = "推荐写法：网络状态统一建模",
            sampleCode = """
                sealed class NetworkResult<out T> {
                    data object Loading : NetworkResult<Nothing>()
                    data class Success<T>(val data: T) : NetworkResult<T>()
                    data class Error(val e: Throwable) : NetworkResult<Nothing>()
                }

                when (val result = repo.load()) {
                    is NetworkResult.Loading -> showLoading()
                    is NetworkResult.Success -> showData(result.data)
                    is NetworkResult.Error -> showError(result.e.message)
                }
            """.trimIndent()
        )
        "mvvm-demo" -> CodeExamplePage(
            summary = "MVVM 的重点是把 UI、状态和数据请求拆开，Activity 只负责观察和跳转。",
            improvements = listOf(
                "Repository 负责数据来源",
                "ViewModel 负责状态和业务流程",
                "Activity 只做 observe 和渲染"
            ),
            sampleTitle = "推荐写法：列表页的标准 MVVM",
            sampleCode = """
                class PostViewModel(private val repo: PostRepository) : ViewModel() {
                    private val _posts = MutableLiveData<List<Post>>()
                    val posts: LiveData<List<Post>> = _posts

                    fun load() = viewModelScope.launch {
                        _posts.value = repo.getPosts()
                    }
                }
            """.trimIndent()
        )
        "inline" -> CodeExamplePage(
            summary = "inline 更适合高频小函数，reified 则能把泛型类型在运行时保留下来。",
            improvements = listOf(
                "减少 Lambda 调用开销",
                "`reified` 解决泛型类型擦除",
                "适合封装工具函数和 DSL"
            ),
            sampleTitle = "推荐写法：计时 + 泛型解析",
            sampleCode = """
                inline fun <T> measure(block: () -> T): T {
                    val start = System.currentTimeMillis()
                    val result = block()
                    println("cost = ${'$'}{System.currentTimeMillis() - start}ms")
                    return result
                }

                inline fun <reified T> parse(json: String): T? = null
            """.trimIndent()
        )
        "operator" -> CodeExamplePage(
            summary = "操作符重载适合把对象行为写得更自然，但要控制边界。",
            improvements = listOf(
                "用 `plus`、`get`、`invoke` 提升可读性",
                "语义要符合直觉，不要滥用",
                "数据对象更适合做小范围 DSL"
            ),
            sampleTitle = "推荐写法：向量相加",
            sampleCode = """
                data class Point(val x: Int, val y: Int) {
                    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
                }

                val p = Point(1, 2) + Point(3, 4)
                println(p)
            """.trimIndent()
        )
        "extension" -> CodeExamplePage(
            summary = "扩展函数适合补充能力而不是改造类型，写法短，落点也清楚。",
            improvements = listOf(
                "让工具方法更贴近调用对象",
                "扩展属性适合表达轻量派生值",
                "优先做纯函数扩展，避免副作用"
            ),
            sampleTitle = "推荐写法：字符串清洗扩展",
            sampleCode = """
                fun String?.orDash(): String = this?.takeIf { it.isNotBlank() } ?: "-"

                fun Int.toPercent(total: Int): Int = if (total == 0) 0 else this * 100 / total
            """.trimIndent()
        )
        "higher-order" -> CodeExamplePage(
            summary = "高阶函数的价值在于把变化点抽出来，让执行流程和业务规则分离。",
            improvements = listOf(
                "把可变策略当参数传入",
                "更适合封装 retry、filter、map 这类逻辑",
                "和 Lambda、函数引用一起用更自然"
            ),
            sampleTitle = "推荐写法：可配置重试器",
            sampleCode = """
                fun retry(times: Int, action: () -> Unit) {
                    repeat(times) { action() }
                }

                retry(3) { println("loading") }
            """.trimIndent()
        )
        "collection-ops" -> CodeExamplePage(
            summary = "集合操作的核心是用 map/filter/groupBy/fold 表达数据转换管道。",
            improvements = listOf(
                "先筛选再转换，意图更明确",
                "用 `groupBy` / `mapValues` 表达聚合",
                "`fold` 适合做累计统计"
            ),
            sampleTitle = "推荐写法：筛选 + 聚合",
            sampleCode = """
                data class User(val name: String, val active: Boolean)

                val groups = users
                    .filter { it.active }
                    .groupBy { it.name.first() }
                    .mapValues { (_, list) -> list.size }
            """.trimIndent()
        )
        "when-expr" -> CodeExamplePage(
            summary = "when 是 Kotlin 更强的分支表达式，能直接返回值，也能做类型判断和范围匹配。",
            improvements = listOf(
                "表达式化，能直接赋值",
                "支持类型判断和智能转换",
                "不用 break，分支更干净"
            ),
            sampleTitle = "推荐写法：直接返回文案",
            sampleCode = """
                fun label(score: Int): String = when (score) {
                    in 90..100 -> "优秀"
                    in 60..89 -> "及格"
                    else -> "待提升"
                }
            """.trimIndent()
        )
        "delegation" -> CodeExamplePage(
            summary = "委托最适合减少模板代码，lazy、observable 和类委托都很实用。",
            improvements = listOf(
                "`by lazy` 做延迟初始化",
                "`observable`/`vetoable` 监听属性变化",
                "类委托能快速实现转发"
            ),
            sampleTitle = "推荐写法：懒加载 + 监听属性",
            sampleCode = """
                val config by lazy { loadConfig() }

                var title: String by Delegates.observable("init") { _, old, new ->
                    println("${'$'}old -> ${'$'}new")
                }
            """.trimIndent()
        )
        "flow" -> CodeExamplePage(
            summary = "Flow 更适合表达连续变化的数据流，尤其是 UI 状态、事件和多源组合。",
            improvements = listOf(
                "StateFlow 适合状态",
                "SharedFlow 适合一次性事件",
                "combine 可以合并多个数据源"
            ),
            sampleTitle = "推荐写法：StateFlow UI 状态",
            sampleCode = """
                data class UiState(val loading: Boolean = false, val data: List<String> = emptyList())

                private val _state = MutableStateFlow(UiState())
                val state = _state.asStateFlow()

                fun load() = viewModelScope.launch {
                    _state.value = UiState(loading = true)
                    val data = withContext(Dispatchers.IO) { repo.load() }
                    _state.value = UiState(data = data)
                }
            """.trimIndent()
        )
        else -> null
    }
}
