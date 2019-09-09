package kt.module.module_mine

import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.fragment_mine.*
import kt.module.module_base.base.presenter.IBasePresenter
import kt.module.module_base.base.view.BaseFragment
import kt.module.module_base.utils.RouteUtils

@Route(path = RouteUtils.RouterMap.MinePage.Mine)
class MineFragment : BaseFragment<IBasePresenter>() {
    override val contentLayoutId: Int
        get() = R.layout.fragment_mine

    override fun initViews() {

    }

    override fun initEvents() {
        fragment_mine_firstTv.setOnClickListener {
            RouteUtils.go(RouteUtils.RouterMap.First.FirstAc).navigation()
        }
        fragment_mine_secondTv.setOnClickListener {
            RouteUtils.go(RouteUtils.RouterMap.Second.SecondAc).navigation()
        }
    }
}