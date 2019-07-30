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
import kt.module.base_module.data.bean.BaseMultiItemEntity
import kt.module.base_module.data.db.table.ChildEntity
import kt.module.second_module.R

class SecondVerItemProvider<T> : BaseItemProvider<BaseMultiItemEntity<T>, BaseRvViewHolder>() {

    override fun layout(): Int {
        return R.layout.second_vertical_layout
    }

    override fun viewType(): Int {
        return SecondDBAdapter.TYPE_VER_FIRST
    }

    override fun convert(holder: BaseRvViewHolder?, data: BaseMultiItemEntity<T>?, position: Int) {
        var titleTv = holder?.itemView?.findViewById<TextView>(R.id.second_ver_titleTv)
        titleTv?.text = data?.title

        var recyclerView = holder?.itemView?.findViewById<RecyclerView>(R.id.second_ver_recyclerView)
        var mutableList = data?.data as MutableList<ChildEntity>

        var adapter: BaseRvQuickAdapter<ChildEntity>? = null

        if (adapter == null) {
            recyclerView?.apply {
                layoutManager = LinearLayoutManager(mContext, LinearLayout.VERTICAL, false)//垂直方向
                adapter = object : BaseRvQuickAdapter<ChildEntity>(R.layout.item_second_vertical, mutableList) {
                    override fun convert(holder: BaseRvViewHolder?, item: ChildEntity) {

//                        holder?.itemView?.let {
//                            if (item.title.length > 3) {
//                                it.findViewById<TextView>(R.id.item_second_ver_nameTv)?.text =
//                                    item.title.substring(item.title.length - 3, item.title.length - 1)
//                            } else {
//                                it.findViewById<TextView>(R.id.item_second_ver_nameTv)?.text = item.title
//                            }
//                            it.findViewById<TextView>(R.id.item_second_ver_ratingTv)?.text = item.id.toString()
//                        }

                        holder?.itemView?.run {
                            if (item.title.length > 3) {
                                findViewById<TextView>(R.id.item_second_ver_nameTv).text =
                                    item.title.substring(item.title.length - 3, item.title.length - 1)
                            } else {
                                findViewById<TextView>(R.id.item_second_ver_nameTv).text = item.title
                            }
                            findViewById<TextView>(R.id.item_second_ver_ratingTv).text = item.id.toString()
                        }
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