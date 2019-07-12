package kt.module.message_module

import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.fragment_message.*
import kt.module.base_module.adapter.BaseRvSectionAdapter
import kt.module.base_module.adapter.BaseRvViewHolder
import kt.module.base_module.base.view.BaseFragment
import kt.module.base_module.data.DataUtils
import kt.module.base_module.data.RvDataSection
import kt.module.base_module.utils.RouteUtils

@Route(path = RouteUtils.RouterMap.MessagePage.Message)
class MessageFragment : BaseFragment() {
    override val contentLayoutId: Int
        get() = R.layout.fragment_message

    var mDatas: MutableList<RvDataSection> = mutableListOf()

    var mAdapter: BaseRvSectionAdapter<RvDataSection>? = null

    override fun initViews() {
        fragment_message_recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = object :
            BaseRvSectionAdapter<RvDataSection>(R.layout.item_base_rv_layout, R.layout.head_base_rv_section_layout, mDatas) {
            override fun convertHead(holder: BaseRvViewHolder?, item: RvDataSection) {
                holder?.setText(R.id.head_base_ver_titleTv, item.header)
            }

            override fun convert(holder: BaseRvViewHolder?, item: RvDataSection) {
                holder?.setText(R.id.item_base_ver_nameTv, item.t.name)
                    ?.setText(R.id.item_base_ver_ageTv, item.t.age.toString())?.addOnClickListener(R.id.item_base_ver_nameTv)
            }
        }
        fragment_message_recyclerView.adapter = mAdapter

        val sectionList = DataUtils.createSectionData(3)
        mDatas.addAll(sectionList)
        mAdapter?.notifyDataSetChanged()
    }

    override fun initEvents() {
        mAdapter?.setOnItemChildClickListener { adapter, view, position ->
            Toast.makeText(context, (view as TextView).text.toString() + "\t点击", Toast.LENGTH_SHORT).show()
        }

        mAdapter?.setOnItemClickListener { adapter, view, position ->
            Toast.makeText(context, position.toString() + "\t点击", Toast.LENGTH_SHORT).show()
        }
    }
}