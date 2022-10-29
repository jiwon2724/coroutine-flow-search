package kr.co.fastcampus.co.kr.coroutines.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.co.fastcampus.co.kr.coroutines.databinding.FragmentMainBinding

class ImageSearchFragment : Fragment() {

    private lateinit var imageSearchViewModel: ImageSearchViewModel
    private val adapter: ImageSearchAdapter = ImageSearchAdapter {
        imageSearchViewModel.toggle(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageSearchViewModel = ViewModelProvider(requireActivity())[ImageSearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // lifecycleScope // 프래그먼트 라이프사이클
        // viewLifecycleOwner.lifecycleScope // 뷰의 라이프사이클 스코프

        viewLifecycleOwner.lifecycleScope.launch {
            imageSearchViewModel.pagingDataFlow.collectLatest {
                adapter.submitData(it)
            }
        }

        val binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.search.setOnClickListener {
            // TODO("검색 기능을 구현해야합니다.")
            val query = binding.editText.text.trim().toString()
            imageSearchViewModel.handleQuery(query)
            // trim()은 좌우 공백을 제거해줌.
        }

        return root
    }
}