package kt.module.base_module.data

object DataUtils {

    fun createData(num: Int): ArrayList<RvData> {

        var list = ArrayList<RvData>()

        for (index in 0..num) {
            list.add(RvData("张三", 22))
            list.add(RvData("李四", 90))
        }
        return list
    }

    fun createSectionData(num: Int): ArrayList<RvDataSection> {

        var list = ArrayList<RvDataSection>()

        for (index in 0..num) {
            list.add(RvDataSection(true, index.toString()))
            list.add(RvDataSection(RvData("张三", 22)))
            list.add(RvDataSection(RvData("李四", 90)))
        }
        return list
    }
}