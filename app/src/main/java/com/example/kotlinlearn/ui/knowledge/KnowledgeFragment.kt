package com.example.kotlinlearn.ui.knowledge

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinlearn.DetailActivity
import com.example.kotlinlearn.KnowledgeData
import com.example.kotlinlearn.KnowledgePageAdapter
import com.example.kotlinlearn.databinding.FragmentKnowledgeBinding
import com.example.kotlinlearn.mvvm.ui.list.PostListActivity

/**
 * ## KnowledgeFragment — 知识点学习 (Tab 2)
 *
 * 复用已有的 KnowledgeData + KnowledgePageAdapter，
 * 展示 23 个知识点卡片列表。
 */
class KnowledgeFragment : Fragment() {

    private var _b: FragmentKnowledgeBinding? = null
    private val b get() = _b!!

    override fun onCreateView(inflater: LayoutInflater, c: ViewGroup?, state: Bundle?): View {
        _b = FragmentKnowledgeBinding.inflate(inflater, c, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = KnowledgePageAdapter { page ->
            if (page.id == "mvvm-demo") {
                startActivity(Intent(requireContext(), PostListActivity::class.java))
                return@KnowledgePageAdapter
            }
            startActivity(Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_PAGE_ID, page.id)
            })
        }
        adapter.submitList(KnowledgeData.allPages)
        b.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
