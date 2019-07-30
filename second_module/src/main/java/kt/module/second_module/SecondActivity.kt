package kt.module.second_module

import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_second.*
import kt.module.base_module.adapter.SecondDBAdapter
import kt.module.base_module.data.bean.BaseMultiItemEntity
import kt.module.base_module.base.presenter.IBasePresenter
import kt.module.base_module.base.view.BaseActivity
import kt.module.base_module.data.db.dao.ObjectEntityDao
import kt.module.base_module.data.db.table.ChildEntity
import kt.module.base_module.data.db.table.ObjectEntity
import kt.module.base_module.data.db.utils.DbUtils
import kt.module.base_module.utils.RouteUtils
import kt.module.base_module.utils.StatusBarUtil

@Route(path = RouteUtils.RouterMap.Second.SecondAc)
class SecondActivity : BaseActivity<IBasePresenter>() {

    override val contentLayoutId: Int
        get() = R.layout.activity_second

    private var mAdapter: SecondDBAdapter<MutableList<ChildEntity>>? = null
    private var mDatas: MutableList<BaseMultiItemEntity<MutableList<ChildEntity>>> = mutableListOf()

    override fun initViews() {
        fragment_second_recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            mAdapter = SecondDBAdapter()
            adapter = mAdapter
        }
        addHeader()
    }

    private fun addHeader() {

        var view = layoutInflater.inflate(R.layout.status_bar_height_layout, null, false) as LinearLayout
        var textView = view.findViewById<TextView>(R.id.status_bar_heightTv)
        textView.layoutParams.apply {
            height = StatusBarUtil.getStatusBarHeight(this@SecondActivity).run {
                (this!! * 1.8).toInt()
            }
        }

        mAdapter?.addHeaderView(view)
    }

    override fun initEvents() {
        var objList = DbUtils.getInstance().getAnyDaoByTable(ObjectEntity::class.java).queryBuilder().let {
            it.orderDesc(ObjectEntityDao.Properties.Id)//根据Id降序排列
            it.build().list()
        } as MutableList<ObjectEntity>

        objList?.run {
            mDatas.clear()
            forEach { objEntity ->
                objEntity?.takeIf {
                    objEntity.child?.size != 0
                }?.let {
                    var item = BaseMultiItemEntity<MutableList<ChildEntity>>()
                    if (it.show_template == 1) {
                        item.type = 1
                        item.title = "竖直方向"
                    } else {
                        item.type = 2
                        item.title = "水平方向"
                    }
                    item.data = it.child

                    mDatas.add(item)
                }
            }
        }

        mAdapter?.setNewData(mDatas)
    }
}