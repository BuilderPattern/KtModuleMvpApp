package kt.module.further_module.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.provider.BaseItemProvider
import kt.module.base_module.R
import kt.module.base_module.adapter.BaseRvQuickAdapter
import kt.module.base_module.adapter.BaseRvViewHolder
import kt.module.base_module.adapter.SecondDBAdapter
import kt.module.base_module.base.entity.BaseMultiItemEntity
import kt.module.base_module.data.db.table.ChildEntity

class SecondHorItemProvider<T> : BaseItemProvider<BaseMultiItemEntity<T>, BaseRvViewHolder>() {

    override fun layout(): Int {
        return R.layout.further_horizontal_layout
    }

    override fun viewType(): Int {
        return SecondDBAdapter.TYPE_HOR_FIRST
    }

    override fun convert(holder: BaseRvViewHolder, data: BaseMultiItemEntity<T>, position: Int) {
        var titleTv = holder.itemView?.findViewById<TextView>(R.id.further_hor_titleTv)
        titleTv?.text = data.title

        var recyclerView = holder.itemView?.findViewById<RecyclerView>(R.id.further_hor_recyclerView)
        var mutableList = data.data as MutableList<ChildEntity>

        var adapter: BaseRvQuickAdapter<ChildEntity>? = null
        if (adapter == null) {
            recyclerView?.apply {
                layoutManager = LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false)//水平方向
                adapter = object : BaseRvQuickAdapter<ChildEntity>(R.layout.item_further_horizontal, mutableList) {
                    override fun convert(holder: BaseRvViewHolder?, item: ChildEntity) {
                        holder?.itemView?.let {
                            it.findViewById<TextView>(R.id.item_snap_hor_nameTv).text = item.title_img_new
                            it.findViewById<TextView>(R.id.item_snap_hor_ratingTv).text = item.id.toString()
                        }
//                        holder?.itemView.run {
//                            findViewById<TextView>(R.id.item_snap_hor_nameTv)?.text = item.title_img_new
//                            findViewById<TextView>(R.id.item_snap_hor_ratingTv)?.text = item.id.toString()
//                        }
                    }
                }
                recyclerView?.adapter = adapter
            }

            recyclerView?.addOnItemTouchListener(object : OnItemClickListener() {
                override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                    Toast.makeText(mContext, "位置：".plus(position.toString()), Toast.LENGTH_SHORT).show()
                }
            })

        } else {
            adapter!!.setNewData(mutableList)
        }
    }
}