package kr.co.fastcampus.co.kr.coroutines.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(private val fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

//    val imageSearchFragment = ImageSearchFragment()
//
//    override fun createFragment(position: Int): Fragment {
//        return imageSearchFragment
//    }

//    -- > 이런식으로 코딩하면 안된다. createFragment()는 상황에 따라서 필요한걸 만들고 캐싱을한다.
//         항상 만들어져 있어서 안됨.



    override fun createFragment(position: Int): Fragment {
        return ImageSearchFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}