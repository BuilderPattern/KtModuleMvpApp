package kt.module.module_message

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.fragment_message.*
import kt.module.module_base.adapter.BaseRvSectionAdapter
import kt.module.module_base.adapter.BaseRvViewHolder
import kt.module.module_base.base.presenter.IBasePresenter
import kt.module.module_base.base.view.BaseFragment
import kt.module.module_base.utils.DataUtils
import kt.module.module_base.data.bean.RvDataSection
import kt.module.module_base.utils.RouteUtils

@Route(path = RouteUtils.RouterMap.MessagePage.Message)
class MessageFragment : BaseFragment<IBasePresenter>() {
    override val contentLayoutId: Int
        get() = R.layout.fragment_message

    private val mDatas: MutableList<RvDataSection> = mutableListOf()

    private var mAdapter: BaseRvSectionAdapter<RvDataSection>? = null

    override fun initViews() {
        fragment_message_recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = object :
            BaseRvSectionAdapter<RvDataSection>(
                R.layout.item_message_section_layout,
                R.layout.head_message_section_layout,
                mDatas
            ) {
            override fun convertHead(holder: BaseRvViewHolder?, item: RvDataSection) {
                if (item.isHeader) {
                    holder?.setText(R.id.head_message_titleTv, item.header)
                }
            }

            override fun convert(holder: BaseRvViewHolder?, item: RvDataSection) {
                if (item.t != null && !item.isHeader) {
                    holder?.apply {
                        item.run {
                            setText(R.id.item_message_section_nameTv, t.name)
                            setText(R.id.item_message_section_ageTv, t.age.toString())
                        }.addOnClickListener(R.id.item_message_section_nameTv)
                            .addOnClickListener(R.id.item_message_section_ageTv)
                    }
                }
            }
        }
        fragment_message_recyclerView.adapter = mAdapter

        val sectionList = DataUtils.createSectionData(3)
        mDatas.addAll(sectionList)
        mAdapter?.notifyDataSetChanged()
    }

    override fun initEvents() {
        mAdapter?.setOnItemChildClickListener { adapter, view, position ->
            val dataSection = mDatas[position]
            if (dataSection.isHeader) {
                RouteUtils.go(RouteUtils.RouterMap.Second.SecondAc).withString("content", dataSection.header)
                    .navigation()
            } else {
                RouteUtils.go(RouteUtils.RouterMap.Second.SecondAc)
                    .withString("content", (view as TextView).text.toString()).navigation()
            }
        }

        mAdapter?.setOnItemClickListener { _, view, position ->
            val dataSection = mDatas[position]
            if (dataSection.isHeader) {
                RouteUtils.go(RouteUtils.RouterMap.Second.SecondAc).withString("header", mDatas[position].header)
                    .navigation()
            } else {
                RouteUtils.go(RouteUtils.RouterMap.Second.SecondAc).withParcelable("item", mDatas[position].t)
                    .navigation()
            }
        }
    }
}