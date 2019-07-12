package kt.module.base_module.data

import com.chad.library.adapter.base.entity.SectionEntity

class RvDataSection : SectionEntity<RvData> {
    constructor(isHeader: Boolean, header: String) : super(isHeader, header)

    constructor(rvData: RvData) : super(rvData)
}