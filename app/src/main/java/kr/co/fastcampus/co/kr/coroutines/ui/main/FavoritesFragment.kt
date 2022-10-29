package kr.co.fastcampus.co.kr.coroutines.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.co.fastcampus.co.kr.coroutines.R
import kr.co.fastcampus.co.kr.coroutines.databinding.FragmentFavoritesBinding
import kr.co.fastcampus.co.kr.coroutines.databinding.FragmentMainBinding


class FavoritesFragment : Fragment() {
    private lateinit var imageSearchViewModel: ImageSearchViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageSearchViewModel = ViewModelProvider(requireActivity())[ImageSearchViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root = binding.root

        val adapter = FavoritesAdapter()
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            imageSearchViewModel.favoritesFlow.collectLatest {
                adapter.setItems(it)
            }
        }
        return root
    }

}