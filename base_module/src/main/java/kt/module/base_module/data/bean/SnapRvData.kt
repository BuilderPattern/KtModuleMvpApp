package kt.module.base_module.data.bean

import kt.module.base_module.data.db.table.RvData

class SnapRvData(type: Int, title: String?, data: MutableList<RvData>?) : BaseMultiItemEntity<MutableList<RvData>>(type, title, data) {
}