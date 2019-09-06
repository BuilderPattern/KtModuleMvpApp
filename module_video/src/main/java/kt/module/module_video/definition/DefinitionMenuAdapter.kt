package kt.module.module_video.definition

import kt.module.module_base.adapter.BaseRvQuickAdapter
import kt.module.module_base.adapter.BaseRvViewHolder
import kt.module.module_video.R
import kt.module.module_video.entities.VideoInfoEntity

class DefinitionMenuAdapter(layoutId: Int, data:MutableList<VideoInfoEntity>) : BaseRvQuickAdapter<VideoInfoEntity>(layoutId, data) {
    override fun convert(holder: BaseRvViewHolder?, item: VideoInfoEntity) {

        when (item.level) {
            0 -> holder?.setText(R.id.item_choose_definitionTv, "流畅")
            1 -> holder?.setText(R.id.item_choose_definitionTv, "高清")
            2 -> holder?.setText(R.id.item_choose_definitionTv, "1080")
        }
    }
}