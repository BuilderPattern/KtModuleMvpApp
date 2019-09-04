package kt.module.module_base.adapter

import com.chad.library.adapter.base.MultipleItemRvAdapter
import kt.module.module_base.data.bean.BaseMultiItemEntity
import kt.module.module_base.data.db.table.ChildEntity
import kt.module.module_further.adapter.FurtherHorItemProvider
import kt.module.module_further.adapter.FurtherVerItemProvider

class FurtherAdapter<T>: MultipleItemRvAdapter<BaseMultiItemEntity<T>, BaseRvViewHolder>{
    override fun getViewType(t: BaseMultiItemEntity<T>?): Int {
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
        mProviderDelegate.registerProvider(FurtherHorItemProvider<MutableList<ChildEntity>>())
        mProviderDelegate.registerProvider(FurtherVerItemProvider<MutableList<ChildEntity>>())

    }
}