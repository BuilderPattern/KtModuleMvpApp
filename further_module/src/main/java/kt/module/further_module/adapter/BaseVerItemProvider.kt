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
import kt.module.base_module.base.entity.FurtherMultiItemEntity
import kt.module.base_module.data.db.table.ChildEntity

class BaseVerItemProvider<T> : BaseItemProvider<FurtherMultiItemEntity<T>, BaseRvViewHolder>() {

    override fun layout(): Int {
        return R.layout.further_vertical_layout
    }

    override fun viewType(): Int {
        return FurtherAdapter.TYPE_VER_FIRST
    }

    override fun convert(holder: BaseRvViewHolder?, data: FurtherMultiItemEntity<T>?, position: Int) {
        var titleTv = holder?.itemView?.findViewById<TextView>(R.id.further_ver_titleTv)
        titleTv?.text = data?.title

        var recyclerView = holder?.itemView?.findViewById<RecyclerView>(R.id.further_ver_recyclerView)
        var mutableList = data?.data as MutableList<ChildEntity>

        recyclerView?.layoutManager = LinearLayoutManager(mContext, LinearLayout.VERTICAL, false)//垂直方向
        var adapter: BaseRvQuickAdapter<ChildEntity>? = null

        if (adapter == null) {
            adapter = object : BaseRvQuickAdapter<ChildEntity>(R.layout.item_further_vertical, mutableList) {
                override fun convert(holder: BaseRvViewHolder?, item: ChildEntity) {
                    if (item.title.length > 3){
                        holder?.itemView?.findViewById<TextView>(R.id.item_snap_ver_nameTv)?.text = item.title.substring(item.title.length-3, item.title.length-1)
                    }else{
                        holder?.itemView?.findViewById<TextView>(R.id.item_snap_ver_nameTv)?.text = item.title
                    }
                    holder?.itemView?.findViewById<TextView>(R.id.item_snap_ver_ratingTv)?.text = item.id.toString()
                }
            }
            recyclerView?.adapter = adapter

        } else {
            adapter.setNewData(mutableList)
        }
    }
}