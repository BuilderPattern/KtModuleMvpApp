package kt.module.home_module

import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.fragment_home.*
import kt.module.base_module.adapter.BaseRvQuickAdapter
import kt.module.base_module.adapter.BaseRvViewHolder
import kt.module.base_module.base.presenter.IBasePresenter
import kt.module.base_module.base.view.BaseFragment
import kt.module.base_module.data.db.table.RvData
import kt.module.base_module.data.utils.DataUtils
import kt.module.base_module.utils.RouteUtils

@Route(path = RouteUtils.RouterMap.HomePage.Home)
class HomeFragment : BaseFragment<IBasePresenter>() {
    override val contentLayoutId: Int
        get() = R.layout.fragment_home

    private val mDatas: MutableList<RvData> = mutableListOf()

    private var mAdapter: BaseRvQuickAdapter<RvData>? = null

    override fun initViews() {
        fragment_home_recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            mAdapter = object : BaseRvQuickAdapter<RvData>(R.layout.item_base_rv_layout, mDatas) {
                override fun convert(holder: BaseRvViewHolder?, item: RvData) {
                    holder!!.setText(R.id.item_base_ver_nameTv, item.name).setText(R.id.item_base_ver_ageTv, item.age.toString())
                }
            }
            adapter = mAdapter
        }



        val data = DataUtils.createData(3)
        mDatas.addAll(data)
        mAdapter?.notifyDataSetChanged()
    }

    override fun initEvents() {

    }
}
