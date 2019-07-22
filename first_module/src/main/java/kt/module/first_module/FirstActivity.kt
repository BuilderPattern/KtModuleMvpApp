package kt.module.first_module

import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_first.*
import kt.module.base_module.base.presenter.IBasePresenter
import kt.module.base_module.base.view.BaseActivity
import kt.module.base_module.base.view.BaseFragment
import kt.module.base_module.utils.RouteUtils
import kt.module.first_module.adapter.MineViewPagerAdapter
import kotlin.math.abs

@Route(path = RouteUtils.RouterMap.First.FirstAc)
open class FirstActivity : BaseActivity<IBasePresenter>() {

    override val contentLayoutId: Int
        get() = R.layout.activity_first

    var fragmentList: MutableList<BaseFragment<*>> = mutableListOf()
    var homeFragment = RouteUtils.go(RouteUtils.RouterMap.HomePage.Home).navigation() as BaseFragment<*>
    var messageFragment = RouteUtils.go(RouteUtils.RouterMap.MessagePage.Message).navigation() as BaseFragment<*>
    var furtherFragment = RouteUtils.go(RouteUtils.RouterMap.FurtherPage.Further).navigation() as BaseFragment<*>

    private var mAdapter: MineViewPagerAdapter? = null

    private var canChangeToolBarColor: Boolean = false
    private var nearByToolBar: Boolean = false

    override fun initViews() {
        fragmentList.add(homeFragment)
        fragmentList.add(messageFragment)
        fragmentList.add(furtherFragment)
        mAdapter = MineViewPagerAdapter(supportFragmentManager, fragmentList)
        activity_first_viewpager.adapter = mAdapter

        activity_first_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
            }

        })
        activity_first_tabLayout.setupWithViewPager(activity_first_viewpager)

        activity_first_app_bar_layout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                val range = appBarLayout?.totalScrollRange
                var offset = abs(verticalOffset)

                if (offset > range!! / 3) {
                    if (canChangeToolBarColor) {
                        activity_first_toolbar.setNavigationIcon(R.mipmap.icon_back_black_img)
                        activity_first_toolbar_titleTv.text = "任🛁"
                        activity_first_toolbar.setBackgroundColor(resources.getColor(R.color.color_ffffff))
                        canChangeToolBarColor = false
                        nearByToolBar = true
                        invalidateOptionsMenu()
                    } else {
                        canChangeToolBarColor = true
                    }
                } else if (offset > range!! / 3 && offset <= range!! * 2 / 3) {
                    canChangeToolBarColor = true
                } else {
                    if (canChangeToolBarColor) {
                        activity_first_toolbar.setNavigationIcon(R.mipmap.icon_back_white_img)
                        activity_first_toolbar_titleTv.text = ""
                        activity_first_toolbar.setBackgroundColor(resources.getColor(R.color.color_00000000))
                        canChangeToolBarColor = false
                        nearByToolBar = false
                        invalidateOptionsMenu()
                    } else {
                        canChangeToolBarColor = true
                    }
                }
            }
        })
    }

    override fun initEvents() {
        activity_first_toolbar.title = ""
        setSupportActionBar(activity_first_toolbar)
        activity_first_toolbar.setNavigationIcon(R.mipmap.icon_back_white_img)
        activity_first_toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_first_toolbar, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (nearByToolBar) {
            menu.findItem(R.id.action_share).setIcon(R.mipmap.icon_share_black_img)
            menu.findItem(R.id.action_collect).setIcon(R.mipmap.icon_collect_black)
        } else {
            menu.findItem(R.id.action_share).setIcon(R.mipmap.icon_share_white_img)
            menu.findItem(R.id.action_collect).setIcon(R.mipmap.icon_collect_default)
        }
        return super.onPrepareOptionsMenu(menu)
    }
}