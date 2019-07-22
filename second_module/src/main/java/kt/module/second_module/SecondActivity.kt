package kt.module.second_module

import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_second.*
import kt.module.base_module.base.presenter.IBasePresenter
import kt.module.base_module.base.view.BaseActivity
import kt.module.base_module.data.db.table.RvData
import kt.module.base_module.utils.RouteUtils

@Route(path = RouteUtils.RouterMap.Second.SecondAc)
class SecondActivity : BaseActivity<IBasePresenter>() {

    override val contentLayoutId: Int
        get() = R.layout.activity_second

    private var content: String? = null
    private var header: String? = null
    private var rvData: RvData? = null
    override fun initViews() {
        content = intent.getStringExtra("content")
        header = intent.getStringExtra("header")
        rvData = intent.getParcelableExtra<RvData>("item")

        var sb = StringBuilder()
        if (content != null) {
            sb.appendln(content)
        }
        if (header != null) {
            sb.appendln(header)
        }
        if (rvData != null) {
            sb.appendln(rvData?.name).appendln(rvData?.age)
        }
        secondTv.text = sb?.toString()
    }

    override fun initEvents() {
        secondTv.setOnClickListener {
            RouteUtils.go(RouteUtils.RouterMap.First.FirstAc).navigation()
        }
    }
}