package kt.module.main_module.mvp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_main.*
import kt.module.base_module.base.view.BaseActivity
import kt.module.base_module.base.view.BaseFragment
import kt.module.base_module.data.db.table.ObjectEntity
import kt.module.base_module.utils.RouteUtils
import kt.module.main_module.BottomTabEntity
import kt.module.main_module.R
import java.util.*

@Route(path = RouteUtils.RouterMap.Main.MainAc)
class MainActivity : BaseActivity<MainPresenter>(), MainContract.IMainView {

    override fun getGetTestSuccessed(data: Any) {

    }

    override fun getGetTestCatFailed(msg: Any) {

    }

    override fun getPostTestSuccessed(data: MutableList<ObjectEntity>?) {

    }

    override fun getPostTestFailed(msg: Any) {

    }

    override val presenter: MainPresenter?
        get() = MainPresenter(this, MainModel())

    override val contentLayoutId: Int
        get() = R.layout.activity_main

    private var index: Int = 0

    private var fragmentList = ArrayList<Fragment>()

    private var mHomeFragment = RouteUtils.go(RouteUtils.RouterMap.HomePage.Home).navigation() as BaseFragment<*>
    private var mMessageFragment =
        RouteUtils.go(RouteUtils.RouterMap.MessagePage.Message).navigation() as BaseFragment<*>
    private var mFurtherFragment =
        RouteUtils.go(RouteUtils.RouterMap.FurtherPage.Further).navigation() as BaseFragment<*>
    private var mMineFragment = RouteUtils.go(RouteUtils.RouterMap.MinePage.Mine).navigation() as BaseFragment<*>

    //底部文字数组
    private val mTabTexts = arrayOf("Home", "Message", "Further", "Mine")

    //未选中图标数组
    private val mUncheckIcons = intArrayOf(
        R.mipmap.icon_tab_home_unselect,
        R.mipmap.icon_tab_message_unselect,
        R.mipmap.icon_tab_more_unselect,
        R.mipmap.icon_tab_mine_unselect
    )

    //选中图标数组
    private val mCheckIcons = intArrayOf(
        R.mipmap.icon_tab_home_select,
        R.mipmap.icon_tab_message_select,
        R.mipmap.icon_tab_more_select,
        R.mipmap.icon_tab_mine_select
    )

    //底部数据集
    private val mTabEntityList = ArrayList<CustomTabEntity>()

    override fun initViews() {
        fragmentList.add(mHomeFragment)
        fragmentList.add(mMessageFragment)
        fragmentList.add(mFurtherFragment)
        fragmentList.add(mMineFragment)

        for (index in mTabTexts.indices) {
            mTabEntityList.add(
                BottomTabEntity(
                    mTabTexts[index],
                    mCheckIcons[index],
                    mUncheckIcons[index]
                )
            )
        }

        activity_main_viewpager.setCurrentItem(index, false)
        activity_main_viewpager.adapter =
            MyPagerAdapter(fragmentList, mTabEntityList, supportFragmentManager)
    }

    override fun initEvents() {

        activity_main_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                activity_main_tabLayout.currentTab = position
            }
        })

        activity_main_tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                activity_main_viewpager.setCurrentItem(position, false)
            }

            override fun onTabReselect(position: Int) {

            }
        })
        activity_main_tabLayout.setTabData(mTabEntityList)

        switch(index)

        presenter?.getPostTest(this)
        presenter?.getGetTest(this)
    }

    private fun switch(index: Int) {
        activity_main_viewpager.setCurrentItem(index, false)
    }

    class MyPagerAdapter(
        var fragments: ArrayList<Fragment>,
        var tabList: ArrayList<CustomTabEntity>,
        supportManager: FragmentManager
    ) : FragmentPagerAdapter(supportManager) {

        override fun getPageTitle(position: Int): CharSequence? {
            var bottomTabEntity = tabList[position] as BottomTabEntity
            return bottomTabEntity.tabTitle
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }
}