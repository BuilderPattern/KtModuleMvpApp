package kt.module.module_base.adapter

import com.chad.library.adapter.base.BaseQuickAdapter

abstract class BaseRvQuickAdapter<T>(layoutResId: Int, data: MutableList<T>?) : BaseQuickAdapter<T, BaseRvViewHolder>(layoutResId, data) {

    abstract override fun convert(helper: BaseRvViewHolder?, item: T)
}