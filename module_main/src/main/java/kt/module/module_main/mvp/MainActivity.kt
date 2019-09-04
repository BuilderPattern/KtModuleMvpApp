package kt.module.module_main.mvp

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.leon.channel.helper.ChannelReaderUtil
import kotlinx.android.synthetic.main.activity_main.*
import kt.module.BaseApp
import kt.module.module_base.base.view.BaseActivity
import kt.module.module_base.base.view.BaseFragment
import kt.module.module_base.data.db.table.ObjectEntity
import kt.module.module_base.utils.RouteUtils
import kt.module.module_main.BottomTabEntity
import kt.module.module_main.R
import java.util.*

@Route(path = RouteUtils.RouterMap.Main.MainAc)
class MainActivity : BaseActivity<MainPresenter>(), MainContract.IMainView {
    override fun getPostTestSuccessed(data: MutableList<ObjectEntity>) {

    }

    override fun getPostTestFailed(msg: Any) {
    }

    override fun getGetTestSuccessed(data: Any) {
    }

    override fun getGetTestCatFailed(msg: Any) {
    }

    override val contentLayoutId: Int
        get() = R.layout.activity_main

    override val presenter: MainPresenter?
        get() = MainPresenter(this, MainModel())

    private var index: Int = 0

    private val fragmentList = ArrayList<Fragment>()

    private var mHomeFragment = RouteUtils.go(RouteUtils.RouterMap.HomePage.Home).navigation() as BaseFragment<*>
    private val mMessageFragment =
        RouteUtils.go(RouteUtils.RouterMap.MessagePage.Message).navigation() as BaseFragment<*>
    private val mFurtherFragment =
        RouteUtils.go(RouteUtils.RouterMap.FurtherPage.Further).navigation() as BaseFragment<*>
    private val mMineFragment = RouteUtils.go(RouteUtils.RouterMap.MinePage.Mine).navigation() as BaseFragment<*>

    //底部文字数组
    private val mTabTexts = arrayOf("Home", "Message", "Further", "Mine")

    //未选中图标数组
    private val mUncheckIcons = intArrayOf(
        R.mipmap.icon_tab_home_unselect,
        R.mipmap.icon_tab_message_unselect,
        R.mipmap.icon_tab_further_unselect,
        R.mipmap.icon_tab_mine_unselect
    )

    //选中图标数组
    private val mCheckIcons = intArrayOf(
        R.mipmap.icon_tab_home_select,
        R.mipmap.icon_tab_message_select,
        R.mipmap.icon_tab_further_select,
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

        activity_main_viewpager.apply {
            offscreenPageLimit = fragmentList.size
            currentItem = index
            adapter = MyPagerAdapter(fragmentList, mTabEntityList, supportFragmentManager)
        }
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
                activity_main_viewpager.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })
        activity_main_tabLayout.setTabData(mTabEntityList)

        switch(index)

        presenter?.apply {
            getPostTest(this@MainActivity)
            getGetTest(this@MainActivity)
        }
    }

    private fun switch(index: Int) {
        activity_main_viewpager.currentItem = index
        val channel = ChannelReaderUtil.getChannel(BaseApp.application.applicationContext)
        Toast.makeText(this, channel, Toast.LENGTH_SHORT).show()
    }

    class MyPagerAdapter(
        private var fragments: ArrayList<Fragment>,
        private var tabList: ArrayList<CustomTabEntity>,
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