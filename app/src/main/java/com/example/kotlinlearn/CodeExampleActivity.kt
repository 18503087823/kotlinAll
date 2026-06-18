package com.example.kotlinlearn

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.kotlinlearn.databinding.ActivityCodeExampleBinding

class CodeExampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCodeExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodeExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pageId = intent.getStringExtra(DetailActivity.EXTRA_PAGE_ID).orEmpty()
        val sourcePage = KnowledgeData.allPages.find { it.id == pageId }
        val examplePage = CodeExampleData.pageFor(pageId)

        binding.toolbar.title = sourcePage?.title?.let { "$it · 更优代码示例" } ?: "更优代码示例"
        binding.toolbar.setNavigationOnClickListener { finish() }

        buildUI(sourcePage, examplePage)
    }

    private fun buildUI(sourcePage: KnowledgePage?, examplePage: CodeExamplePage?) {
        with(binding.contentContainer) {
            addView(buildHeaderCard(sourcePage, examplePage))
            if (examplePage != null) {
                addView(buildTextCard("总结", examplePage.summary))
                addView(buildListCard("升级点", examplePage.improvements))
                addView(buildCodeCard(examplePage.sampleTitle, examplePage.sampleCode))
                if (examplePage.note.isNotBlank()) {
                    addView(buildNoteCard(examplePage.note))
                }
            } else {
                addView(buildTextCard("说明", "这个知识点的示例还没补全。"))
            }
        }
    }

    private fun buildHeaderCard(page: KnowledgePage?, examplePage: CodeExamplePage?): View {
        return CardView(this).apply {
            radius = dp(12).toFloat()
            setCardElevation(dp(2).toFloat())
            setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            setContentPadding(dp(16), dp(16), dp(16), dp(16))
            layoutParams = cardLayoutParams()

            addView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(TextView(context).apply {
                    text = page?.briefDesc ?: "代码示例页"
                    textSize = 14f
                    setTextColor(Color.parseColor("#555555"))
                })
                addView(space(dp(8)))
                addView(TextView(context).apply {
                    text = examplePage?.sampleTitle ?: "更优写法"
                    textSize = 18f
                    setTextColor(Color.parseColor("#222222"))
                    setTypeface(typeface, Typeface.BOLD)
                })
            })
        }
    }

    private fun buildTextCard(title: String, content: String): View {
        return CardView(this).apply {
            radius = dp(12).toFloat()
            setCardElevation(dp(2).toFloat())
            setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            setContentPadding(dp(16), dp(16), dp(16), dp(16))
            layoutParams = cardLayoutParams()

            addView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(sectionTitle(title))
                addView(space(dp(8)))
                addView(TextView(context).apply {
                    text = content
                    textSize = 14f
                    setTextColor(Color.parseColor("#444444"))
                })
            })
        }
    }

    private fun buildListCard(title: String, items: List<String>): View {
        return CardView(this).apply {
            radius = dp(12).toFloat()
            setCardElevation(dp(2).toFloat())
            setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            setContentPadding(dp(16), dp(16), dp(16), dp(16))
            layoutParams = cardLayoutParams()

            addView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(sectionTitle(title))
                addView(space(dp(8)))
                items.forEach { item ->
                    addView(TextView(context).apply {
                        text = "• $item"
                        textSize = 14f
                        setTextColor(Color.parseColor("#444444"))
                    })
                }
            })
        }
    }

    private fun buildCodeCard(title: String, code: String): View {
        return CardView(this).apply {
            radius = dp(12).toFloat()
            setCardElevation(dp(2).toFloat())
            setCardBackgroundColor(Color.parseColor("#1E1E1E"))
            setContentPadding(dp(12), dp(12), dp(12), dp(12))
            layoutParams = cardLayoutParams()

            addView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(TextView(context).apply {
                    text = title
                    textSize = 15f
                    setTextColor(Color.parseColor("#E0E0E0"))
                })
                addView(space(dp(8)))
                addView(ScrollView(context).apply {
                    addView(TextView(context).apply {
                        text = code
                        textSize = 13f
                        setTextColor(Color.parseColor("#A9B7C6"))
                        typeface = Typeface.MONOSPACE
                        setTextIsSelectable(true)
                        setPadding(dp(8), dp(8), dp(8), dp(8))
                    })
                })
            })
        }
    }

    private fun buildNoteCard(note: String): View {
        return CardView(this).apply {
            radius = dp(12).toFloat()
            setCardElevation(dp(2).toFloat())
            setCardBackgroundColor(Color.parseColor("#FFF3E0"))
            setContentPadding(dp(16), dp(14), dp(16), dp(14))
            layoutParams = cardLayoutParams()

            addView(TextView(context).apply {
                text = note
                textSize = 13.5f
                setTextColor(Color.parseColor("#BF360C"))
            })
        }
    }

    private fun sectionTitle(text: String): TextView {
        return TextView(this).apply {
            this.text = text
            textSize = 16f
            setTextColor(Color.parseColor("#222222"))
            setTypeface(typeface, Typeface.BOLD)
        }
    }

    private fun space(height: Int): View = View(this).apply {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
    }

    private fun cardLayoutParams(): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { bottomMargin = dp(12) }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
