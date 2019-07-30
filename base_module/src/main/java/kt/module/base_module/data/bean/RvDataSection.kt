package kt.module.base_module.data.bean

import com.chad.library.adapter.base.entity.SectionEntity
import kt.module.base_module.data.db.table.RvData

class RvDataSection : SectionEntity<RvData> {
    constructor(isHeader: Boolean, header: String) : super(isHeader, header)

    constructor(rvData: RvData) : super(rvData)
}