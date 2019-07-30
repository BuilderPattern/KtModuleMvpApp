package kt.module.base_module.adapter

import com.chad.library.adapter.base.MultipleItemRvAdapter
import kt.module.base_module.data.bean.BaseMultiItemEntity
import kt.module.base_module.data.db.table.ChildEntity
import kt.module.second_module.adapter.SecondHorItemProvider
import kt.module.second_module.adapter.SecondVerItemProvider

class SecondDBAdapter<T>: MultipleItemRvAdapter<BaseMultiItemEntity<T>, BaseRvViewHolder>{
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
        mProviderDelegate.registerProvider(SecondVerItemProvider<MutableList<ChildEntity>>())
        mProviderDelegate.registerProvider(SecondHorItemProvider<MutableList<ChildEntity>>())
    }
}