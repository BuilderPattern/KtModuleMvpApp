package kt.module.first_module.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MineViewPagerAdapter(fm: FragmentManager, private var fragmentList: List<Fragment>) : FragmentPagerAdapter(fm) {
    private val mTitles = arrayOf("FIRST", "SECOND", "THIRD")
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }

}
