package kt.module.module_home

import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kt.module.module_base.adapter.BaseRvQuickAdapter
import kt.module.module_base.adapter.BaseRvViewHolder
import kt.module.module_base.base.presenter.IBasePresenter
import kt.module.module_base.base.view.BaseFragment
import kt.module.module_base.data.db.table.RvData
import kt.module.module_base.utils.DataUtils
import kt.module.module_base.utils.RouteUtils
import kt.module.module_base.utils.StatusBarUtil

@Route(path = RouteUtils.RouterMap.HomePage.Home)
class HomeFragment : BaseFragment<IBasePresenter>() {
    override val contentLayoutId: Int
        get() = R.layout.fragment_home

    private val mDatas: MutableList<RvData> = mutableListOf()

    private var mAdapter: BaseRvQuickAdapter<RvData>? = null

    override fun initViews() {

        (fragment_home_recyclerView as RecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            mAdapter = object : BaseRvQuickAdapter<RvData>(R.layout.item_home_layout, mDatas) {
                override fun convert(holder: BaseRvViewHolder?, item: RvData) {
                    item.run {
                        holder?.apply {
                            setSimpleDraweeViewUrl(R.id.item_home_simpleDraweeView, url)
                            setText(R.id.item_home_nameTv, name)
                            setText(R.id.item_home_ageTv, age.toString())
                        }
                    }
                }
            }
            adapter = mAdapter
        }

        val data = DataUtils.createData(3)
        mDatas.addAll(data)
        mAdapter?.notifyDataSetChanged()

        addHeader()
    }

    private fun addHeader() {

        var view = layoutInflater.inflate(R.layout.status_bar_height_layout, null, false) as LinearLayout
        var textView = view.findViewById<TextView>(R.id.status_bar_heightTv)
        textView.layoutParams.apply {
            height = StatusBarUtil.getStatusBarHeight(context!!).run {
                (this!! * 1.5).toInt()
            }
        }

        mAdapter?.addHeaderView(view)
    }


    override fun initEvents() {
        mAdapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            RouteUtils.go(RouteUtils.RouterMap.Play.PlayVideoAc).navigation()
        }
    }
}
