package kt.module.module_base.utils

import kt.module.module_base.data.db.table.RvData
import kt.module.module_base.data.bean.RvDataSection
import kt.module.module_base.data.bean.SnapRvData

object DataUtils {

    fun createData(num: Int): MutableList<RvData> {

        var list :MutableList<RvData> = mutableListOf()

        for (index in 0..num) {
            list.add(RvData("张三", 22, "http://rscdn.17mingxiang.com/Now/picture/courseImg/20190322205909-5c94dc1df2f1f.jpg"))
            list.add(RvData("李四", 90, "http://rscdn.17mingxiang.com/Now/picture/courseImg/20190329141821-5c9db8ad6020b.jpg"))
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
                        22,
                        "http://rscdn.17mingxiang.com/Now/picture/courseImg/20190322205909-5c94dc1df2f1f.jpg"
                    )
                )
            )
            list.add(
                RvDataSection(
                    RvData(
                        "李四",
                        90,
                        "http://rscdn.17mingxiang.com/Now/picture/courseImg/20190329141821-5c9db8ad6020b.jpg"
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