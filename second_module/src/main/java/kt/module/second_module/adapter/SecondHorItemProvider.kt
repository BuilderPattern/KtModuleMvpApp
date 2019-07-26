package kt.module.second_module.adapter

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.provider.BaseItemProvider
import kt.module.base_module.adapter.BaseRvQuickAdapter
import kt.module.base_module.adapter.BaseRvViewHolder
import kt.module.base_module.adapter.SecondDBAdapter
import kt.module.base_module.base.entity.BaseMultiItemEntity
import kt.module.base_module.data.db.table.ChildEntity
import kt.module.second_module.R

class SecondHorItemProvider<T> : BaseItemProvider<BaseMultiItemEntity<T>, BaseRvViewHolder>() {

    override fun layout(): Int {
        return R.layout.second_horizontal_layout
    }

    override fun viewType(): Int {
        return SecondDBAdapter.TYPE_HOR_FIRST
    }

    override fun convert(holder: BaseRvViewHolder, data: BaseMultiItemEntity<T>, position: Int) {
        var titleTv = holder.itemView?.findViewById<TextView>(R.id.second_hor_titleTv)
        titleTv?.text = data.title

        var recyclerView = holder.itemView?.findViewById<RecyclerView>(R.id.second_hor_recyclerView)
        var mutableList = data.data as MutableList<ChildEntity>

        var adapter: BaseRvQuickAdapter<ChildEntity>? = null
        if (adapter == null) {
            recyclerView?.apply {
                layoutManager = LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false)//水平方向
                adapter = object : BaseRvQuickAdapter<ChildEntity>(R.layout.item_second_horizontal, mutableList) {
                    override fun convert(holder: BaseRvViewHolder?, item: ChildEntity) {
                        holder?.itemView?.let {
                            it.findViewById<TextView>(R.id.item_second_hor_nameTv).text = item.title_img_new
                            it.findViewById<TextView>(R.id.item_second_hor_ratingTv).text = item.id.toString()
                        }
//                        holder?.itemView.run {
//                            findViewById<TextView>(R.id.item_second_hor_nameTv)?.text = item.title_img_new
//                            findViewById<TextView>(R.id.item_second_hor_ratingTv)?.text = item.id.toString()
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