package kt.module.base_module.base.entity

import kt.module.base_module.data.db.table.RvData

class SnapRvData(type: Int, title: String?, data: MutableList<RvData>?) : FurtherMultiItemEntity<MutableList<RvData>>(type, title, data) {
}