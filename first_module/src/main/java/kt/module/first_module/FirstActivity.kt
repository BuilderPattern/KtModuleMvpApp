package kt.module.first_module

import android.view.Menu
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
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

    var currOffset: Int = 0
    var lastOffset: Int = 0

    override fun initViews() {
        fragmentList.run {
            add(homeFragment)
            add(messageFragment)
            add(furtherFragment)
        }

        mAdapter = MineViewPagerAdapter(supportFragmentManager, fragmentList)
        activity_first_viewpager.run {
            offscreenPageLimit = fragmentList.size
            adapter = mAdapter
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                }
            })
            activity_first_tabLayout.setupWithViewPager(this)
        }

        activity_first_app_bar_layout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                currOffset = abs(verticalOffset)

                takeIf { currOffset != lastOffset }//true的时候才执行下面的代码块，false的时候，该行代码会返回null
                    ?.let {
                        val range = appBarLayout?.totalScrollRange

                        if (currOffset > range!! * 5 / 8) {
                            if (canChangeToolBarColor) {
                                activity_first_toolbar.run {
                                    setNavigationIcon(R.mipmap.icon_back_black_img)
                                    activity_first_toolbar_titleTv.text = "任🛁"
                                    setBackgroundColor(resources.getColor(R.color.color_ffffff))
                                    canChangeToolBarColor = false
                                    nearByToolBar = true
                                    invalidateOptionsMenu()
                                }
                            }
                        } else if (currOffset >= range!! / 8 && currOffset < range!! * 5 / 8) {
                            canChangeToolBarColor = true
                        } else {
                            if (canChangeToolBarColor) {
                                activity_first_toolbar.run {
                                    setNavigationIcon(R.mipmap.icon_back_white_img)
                                    activity_first_toolbar_titleTv.text = ""
                                    setBackgroundColor(resources.getColor(R.color.color_00000000))
                                    canChangeToolBarColor = false
                                    nearByToolBar = false
                                    invalidateOptionsMenu()
                                }
                            }
                        }
                    }

                lastOffset = abs(verticalOffset)
            }
        })
    }

    override fun initEvents() {
        activity_first_toolbar?.run {
            title = ""
            setSupportActionBar(this)
            setNavigationIcon(R.mipmap.icon_back_white_img)
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_first_toolbar, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.run {
            findItem(R.id.action_share).apply {
                setIcon(R.mipmap.icon_share_black_img)
                    .takeIf { !nearByToolBar }
                    ?.let {
                        setIcon(R.mipmap.icon_share_white_img)
                    }
            }
            findItem(R.id.action_collect).apply {
                setIcon(R.mipmap.icon_collect_black)
                    .takeIf { !nearByToolBar }
                    ?.let {
                        setIcon(R.mipmap.icon_collect_default)
                    }
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }
}