package kt.module.module_base.utils

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import kt.module.BaseApp

object ResourceUtil {
    fun getResString(id: Int): String {
        return BaseApp.application.getString(id)
    }

    fun Int.getResColor(): Int {
        return ContextCompat.getColor(BaseApp.application, this)
    }

    fun getResDrawable(id: Int): Drawable {
        return ContextCompat.getDrawable(BaseApp.application, id)!!
    }

}