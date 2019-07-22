package kt.module.base_module.data.utils

import kt.module.base_module.data.db.table.RvData
import kt.module.base_module.base.entity.RvDataSection
import kt.module.base_module.base.entity.SnapRvData

object DataUtils {

    fun createData(num: Int): MutableList<RvData> {

        var list :MutableList<RvData> = mutableListOf()

        for (index in 0..num) {
            list.add(RvData("张三", 22))
            list.add(RvData("李四", 90))
        }
        return list
    }

    fun createSectionData(num: Int): MutableList<RvDataSection> {

        var list:MutableList<RvDataSection> = mutableListOf()

        for (index in 0..num) {
            list.add(RvDataSection(true, index.toString()))
            list.add(
                RvDataSection(
                    RvData(
                        "张三",
                        22
                    )
                )
            )
            list.add(
                RvDataSection(
                    RvData(
                        "李四",
                        90
                    )
                )
            )
        }
        return list
    }
    fun createSnapData(num: Int): MutableList<SnapRvData> {
        var list :MutableList<SnapRvData> = mutableListOf()
        for (index in 0..num) {
            list.add(
                SnapRvData(
                    1,
                    "竖直方向",
                    createData(3)
                )
            )
            list.add(
                SnapRvData(
                    0,
                    "水平方向",
                    createData(3)
                )
            )
        }
        return list
    }
}