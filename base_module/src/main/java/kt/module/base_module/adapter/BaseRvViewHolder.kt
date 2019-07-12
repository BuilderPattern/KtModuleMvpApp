package kt.module.base_module.adapter

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import java.io.File

class BaseRvViewHolder(view: View?) : BaseViewHolder(view) {

    fun setDrawableLeft(viewId: Int, drawable: Drawable): BaseViewHolder {
        val view = this.getView<TextView>(viewId)
        view.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        return this
    }

    fun setDrawblePadding(viewId: Int, size: Int): BaseViewHolder {
        val view = this.getView<TextView>(viewId)
        view.compoundDrawablePadding = 4
        return this
    }

    fun setImageFile(id: Int, file: File): BaseViewHolder {
        val imageView = getView<ImageView>(id)
        Glide.with(imageView.context).load(file)
            .into(imageView)
        return this
    }

    fun setImageUri(id: Int, uri: Uri): BaseViewHolder {
        val imageView = getView<ImageView>(id)
        Glide.with(imageView.context).load(uri)
            .into(imageView)
        return this
    }

    fun setImageRes(id: Int, resId: Int): BaseViewHolder {
        val imageView = getView<ImageView>(id)
        Glide.with(imageView.context).load(resId)
            .into(imageView)
        return this
    }
}