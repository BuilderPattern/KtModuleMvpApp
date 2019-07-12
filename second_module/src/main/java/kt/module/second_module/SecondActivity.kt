package kt.module.second_module

import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_second.*
import kt.module.base_module.base.view.BaseActivity
import kt.module.base_module.utils.RouteUtils

@Route(path = RouteUtils.RouterMap.Second.SecondAc)
class SecondActivity : BaseActivity() {

    override val contentLayoutId: Int
        get() = R.layout.activity_second

    override fun initViews() {

    }

    override fun initEvents() {
        secondTv.setOnClickListener {
            RouteUtils.go(RouteUtils.RouterMap.First.FirstAc).navigation()
        }
    }
}