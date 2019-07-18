package kt.module.base_module.adapter

import com.chad.library.adapter.base.MultipleItemRvAdapter
import kt.module.base_module.data.ChildEntity
import kt.module.base_module.data.FurtherMultiItemEntity
import kt.module.further_module.adapter.BaseHorItemProvider
import kt.module.further_module.adapter.BaseVerItemProvider

class FurtherAdapter<T>: MultipleItemRvAdapter<FurtherMultiItemEntity<T>, BaseRvViewHolder>{
    override fun getViewType(t: FurtherMultiItemEntity<T>?): Int {
        return t!!.type
    }

    constructor():super(null){
        finishInitialize()
    }
    companion object{
        const val TYPE_VER_FIRST:Int  = 1
        const val TYPE_HOR_FIRST:Int  = 2
    }
    override fun registerItemProvider() {
        mProviderDelegate.registerProvider(BaseHorItemProvider<MutableList<ChildEntity>>())
        mProviderDelegate.registerProvider(BaseVerItemProvider<MutableList<ChildEntity>>())

    }
}