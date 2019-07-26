package kt.module.base_module.adapter

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import java.io.File
import android.R
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.common.RotationOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder


class BaseRvViewHolder(view: View?) : BaseViewHolder(view) {

    fun setDrawableLeft(viewId: Int, drawable: Drawable): BaseViewHolder {
        var view = this.getView<TextView>(viewId)
        view.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        return this
    }

    fun setDrawblePadding(viewId: Int, size: Int): BaseViewHolder {
        var view = this.getView<TextView>(viewId)
        view.compoundDrawablePadding = 4
        return this
    }

    fun setImageFile(id: Int, file: File): BaseViewHolder {
        var imageView = getView<ImageView>(id)
        Glide.with(imageView.context).load(file)
            .into(imageView)
        return this
    }

    fun setImageRes(id: Int, resId: Int): BaseViewHolder {
        var imageView = getView<ImageView>(id)
        Glide.with(imageView.context).load(resId)
            .into(imageView)
        return this
    }

    fun setImageUrl(id: Int, url: String): BaseViewHolder {
        var imageView = getView<ImageView>(id)
        Glide.with(imageView.context).load(url)
            .into(imageView)
        return this
    }

    fun setSimpleDraweeViewUrl(viewId: Int, url: String?): BaseViewHolder {
        val draweeView = this.getView<View>(viewId) as SimpleDraweeView
        url?.let {
            val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(it))
                .apply {
                    rotationOptions = RotationOptions.autoRotate()
                    isProgressiveRenderingEnabled = true
                }.build()

            draweeView.controller = Fresco.newDraweeControllerBuilder()
                .apply {
                    oldController = draweeView.controller
                    imageRequest = request
                    autoPlayAnimations = true
                }.build()
        }
        return this
    }
}