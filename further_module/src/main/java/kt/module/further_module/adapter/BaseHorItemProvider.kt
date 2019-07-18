package kt.module.further_module.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.provider.BaseItemProvider
import kt.module.base_module.R
import kt.module.base_module.adapter.BaseRvQuickAdapter
import kt.module.base_module.adapter.BaseRvViewHolder
import kt.module.base_module.adapter.FurtherAdapter
import kt.module.base_module.data.FurtherMultiItemEntity
import kt.module.base_module.data.ChildEntity

class BaseHorItemProvider<T> : BaseItemProvider<FurtherMultiItemEntity<T>, BaseRvViewHolder>() {

    override fun layout(): Int {
        return R.layout.further_horizontal_layout
    }

    override fun viewType(): Int {
        return FurtherAdapter.TYPE_HOR_FIRST
    }

    override fun convert(holder: BaseRvViewHolder?, data: FurtherMultiItemEntity<T>?, position: Int) {
        var titleTv = holder?.itemView?.findViewById<TextView>(R.id.further_hor_titleTv)
        titleTv?.text = data?.title

        var recyclerView = holder?.itemView?.findViewById<RecyclerView>(R.id.further_hor_recyclerView)
        var mutableList = data?.data as MutableList<ChildEntity>

        recyclerView?.layoutManager = LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false)//水平方向
        var adapter: BaseRvQuickAdapter<ChildEntity>? = null
        if (adapter == null) {
            adapter = object : BaseRvQuickAdapter<ChildEntity>(R.layout.item_further_horizontal, mutableList) {
                override fun convert(holder: BaseRvViewHolder?, item: ChildEntity) {
                    holder?.itemView?.findViewById<TextView>(R.id.item_snap_hor_nameTv)?.text = item.title_img_new
                    holder?.itemView?.findViewById<TextView>(R.id.item_snap_hor_ratingTv)?.text = item.id.toString()
                }
            }
            recyclerView?.adapter = adapter
        } else {
            adapter.setNewData(mutableList)
        }
    }
}