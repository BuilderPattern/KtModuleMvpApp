package kt.module.base_module.adapter

import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.entity.SectionEntity

abstract class BaseRvSectionAdapter<T : SectionEntity<*>?>(layoutResId: Int, layoutHeadId: Int, data: MutableList<T>?) : BaseSectionQuickAdapter<T, BaseRvViewHolder>(layoutResId, layoutHeadId, data) {
    abstract override fun convertHead(helper: BaseRvViewHolder?, item: T)

    abstract override fun convert(helper: BaseRvViewHolder?, item: T)
}