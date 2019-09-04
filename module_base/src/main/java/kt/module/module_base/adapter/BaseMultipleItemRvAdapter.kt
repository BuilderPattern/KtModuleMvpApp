package kt.module.module_base.adapter

import com.chad.library.adapter.base.MultipleItemRvAdapter
import kt.module.module_base.data.bean.BaseMultiItemEntity

open abstract class BaseMultipleItemRvAdapter<T : BaseMultiItemEntity<MutableList<*>>>(data: MutableList<T>) : MultipleItemRvAdapter<T, BaseRvViewHolder>(data) {
    abstract override fun registerItemProvider()

    abstract override fun getViewType(t: T): Int
}