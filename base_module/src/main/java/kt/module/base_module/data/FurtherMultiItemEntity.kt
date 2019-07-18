package kt.module.base_module.data

import com.chad.library.adapter.base.entity.MultiItemEntity

open class FurtherMultiItemEntity<T> : MultiItemEntity {

    var data: T? = null
    var title: String? = null
    var type: Int = 0

    constructor(type: Int, title: String?, data: T?) {
        this.type = type
        this.title = title
        this.data = data
    }

    constructor()

    override fun getItemType(): Int {
        return type
    }
}