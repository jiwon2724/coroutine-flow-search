package kr.co.fastcampus.co.kr.coroutines.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kr.co.fastcampus.co.kr.coroutines.data.NaverImageSearchRepository
import kr.co.fastcampus.co.kr.coroutines.model.Item

class ImageSearchViewModel : ViewModel() {
    private val repository = NaverImageSearchRepository()
    private val queryFlow = MutableSharedFlow<String>()
    // 이미지 검색시 입력한 내용이 queryFlow를 통해 흐르고 있다.
    private val favorites = mutableSetOf<Item>()
    private val _favoritesFlow = MutableSharedFlow<List<Item>>(replay = 1)

    // SharedFlow, -> Hot flow 언제나 값을 흘려보내는 구조 (collect 사용안해도), 여러명이 구독 가능

    val pagingDataFlow = queryFlow // 입력한 내용을 통해서 이미지 검색을 한다.
        .flatMapLatest { // 입력한 검색어가 다른 검색어를 입력하고 바뀌기 위해 flatMapLatest를 사용
            searchImages(it)
        }
        .cachedIn(viewModelScope) // viewModelScope에 저장한다.

    val favoritesFlow = _favoritesFlow.asSharedFlow()
    // 값을 바꿀 수 없는 flow로 캐스팅

    private fun searchImages(query: String): Flow<PagingData<Item>> =
        repository.getImageSearch(query)

    // 사용자가 입력했던 검색어를 추가
    fun handleQuery(query: String) {
        viewModelScope.launch { // 뷰모델에서 코루틴을 사용할 땐 viewModelScope를 사용해서 만들어야한다.
            queryFlow.emit(query)
        }
    }

    fun toggle(item: Item) {
        if (favorites.contains(item)) {
            favorites.remove(item)
        } else {
            favorites.add(item)
        }
        viewModelScope.launch {
            _favoritesFlow.emit(favorites.toList())
        }
    }
}