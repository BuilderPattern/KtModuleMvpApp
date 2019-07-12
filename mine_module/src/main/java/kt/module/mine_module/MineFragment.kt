package kt.module.mine_module

import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.fragment_mine.*
import kt.module.base_module.base.view.BaseFragment
import kt.module.base_module.utils.RouteUtils

@Route(path = RouteUtils.RouterMap.MinePage.Mine)
class MineFragment : BaseFragment() {
    override val contentLayoutId: Int
        get() = R.layout.fragment_mine

    override fun initViews() {

    }

    override fun initEvents() {
        fragment_mine_clickTv.setOnClickListener {
            RouteUtils.go(RouteUtils.RouterMap.Second.SecondAc).navigation()
        }
    }
}