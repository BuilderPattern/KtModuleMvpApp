package kt.module.module_base.data.bean

import com.chad.library.adapter.base.entity.SectionEntity
import kt.module.module_base.data.db.table.RvData

class RvDataSection : SectionEntity<RvData> {
    constructor(isHeader: Boolean, header: String) : super(isHeader, header)

    constructor(rvData: RvData) : super(rvData)
}