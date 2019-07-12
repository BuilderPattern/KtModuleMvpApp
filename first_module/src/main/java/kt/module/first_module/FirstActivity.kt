package kt.module.first_module

import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_first.*
import kt.module.base_module.base.view.BaseActivity
import kt.module.base_module.utils.RouteUtils

@Route(path = RouteUtils.RouterMap.First.FirstAc)
open class FirstActivity : BaseActivity() {

    override val contentLayoutId: Int
        get() = R.layout.activity_first

    override fun initViews() {

    }

    override fun initEvents() {
        firstTv.setOnClickListener {
            RouteUtils.go(RouteUtils.RouterMap.Second.SecondAc).navigation()
        }
    }
}