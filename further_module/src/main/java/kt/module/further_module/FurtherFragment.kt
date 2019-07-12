package kt.module.further_module

import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.fragment_details.*
import kt.module.base_module.base.view.BaseFragment
import kt.module.base_module.utils.RouteUtils

@Route(path = RouteUtils.RouterMap.FurtherPage.Further)
class FurtherFragment : BaseFragment() {
    override val contentLayoutId: Int
        get() = R.layout.fragment_details

    override fun initViews() {

    }

    override fun initEvents() {
        fragment_details_clickTv.setOnClickListener {
            RouteUtils.go(RouteUtils.RouterMap.First.FirstAc).navigation()
        }
    }

}
