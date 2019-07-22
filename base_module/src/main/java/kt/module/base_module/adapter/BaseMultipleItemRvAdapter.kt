package kt.module.base_module.adapter

import com.chad.library.adapter.base.MultipleItemRvAdapter
import kt.module.base_module.base.entity.FurtherMultiItemEntity

open abstract class BaseMultipleItemRvAdapter<T : FurtherMultiItemEntity<MutableList<*>>>(data: MutableList<T>) : MultipleItemRvAdapter<T, BaseRvViewHolder>(data) {
    abstract override fun registerItemProvider()

    abstract override fun getViewType(t: T): Int
}