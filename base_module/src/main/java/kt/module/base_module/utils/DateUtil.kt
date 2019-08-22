package kt.module.base_module.utils

object DateUtil {
    init {

    }

    fun timeFormat(time: Long): String {
        var timeString = ""
        var h = 0
        var m = 0
        var s = 0

        var hStr: String? = "00"
        var mStr: String = "00"
        var sStr: String = "00"
        try {
            h = (time / 3600).toInt()
            m = ((time - h * 3600) / 60).toInt()
            s = ((time - h * 3600) % 60).toInt()
            if (h < 10 && h > 0) {
                hStr = "0$h"
            }
            if (m < 10) {
                mStr = "0$m"
            } else {
                mStr = m.toString() + ""
            }
            if (s < 10) {
                sStr = "0$s"
            } else {
                sStr = s.toString() + ""
            }

            val stringBuilder = StringBuilder()
            timeString = stringBuilder.append(hStr).append(":").append(mStr).append(":").append(sStr).toString()
            return timeString
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return timeString
    }

}