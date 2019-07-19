package kt.module.first_module.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

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
