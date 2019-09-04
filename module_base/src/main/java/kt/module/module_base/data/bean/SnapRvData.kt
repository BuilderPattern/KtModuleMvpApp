package kt.module.module_base.data.bean

import kt.module.module_base.data.db.table.RvData

class SnapRvData(type: Int, title: String?, data: MutableList<RvData>?) : BaseMultiItemEntity<MutableList<RvData>>(type, title, data) {
}