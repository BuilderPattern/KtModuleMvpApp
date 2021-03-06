package kt.module.module_further.mvp

import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.fragment_further.*
import kt.module.module_base.adapter.FurtherAdapter
import kt.module.module_base.data.bean.BaseMultiItemEntity
import kt.module.module_base.base.view.BaseFragment
import kt.module.module_base.data.db.dao.ChildEntityDao
import kt.module.module_base.data.db.dao.ObjectEntityDao
import kt.module.module_base.data.db.table.ChildEntity
import kt.module.module_base.data.db.table.ObjectEntity
import kt.module.module_base.data.db.utils.DbUtils
import kt.module.module_base.utils.RouteUtils
import kt.module.module_base.utils.StatusBarUtil
import kt.module.module_further.R

@Route(path = RouteUtils.RouterMap.FurtherPage.Further)
class FurtherFragment : BaseFragment<FurtherPresenter>(), FurtherContract.IFurtherView {

    override fun getODSuccessed(data: MutableList<ObjectEntity>) {

        mDatas.clear()
        data?.forEach {
            var item =
                BaseMultiItemEntity<MutableList<ChildEntity>>()
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
        mAdapter?.setNewData(mDatas)

        data?.forEach { objEntity ->
            objEntity.child.forEach { childEntity ->
                childEntity.objectId = objEntity.show_template.toLong()
            }

            DbUtils.getInstance().insertOrReplaceList(objEntity.child, ChildEntity::class.java)
        }
        DbUtils.getInstance().deleteAll(ObjectEntity::class.java)
        DbUtils.getInstance().insertOrReplaceList(data, ObjectEntity::class.java)

        /**
         * 以下为查询示例
         */
        var objList = DbUtils.getInstance().getAnyDaoByTable(ObjectEntity::class.java).queryBuilder().let {
            it.orderDesc(ObjectEntityDao.Properties.Id)
            it.build().list()
        } as MutableList<ObjectEntity>
        Log.e("--------objList：", objList?.size.toString() + "\n" + objList.toString())

        var childList =
            (DbUtils.getInstance().getAnyDaoByTable(ChildEntity::class.java) as ChildEntityDao).queryBuilder().let {
                it.orderDesc(ChildEntityDao.Properties.Id)
                it.where(
                    ChildEntityDao.Properties.ObjectId.eq(objList[0].show_template),
                    it.or(
                        ChildEntityDao.Properties.Id.gt(50),
                        it.and(ChildEntityDao.Properties.Id.eq(30), ChildEntityDao.Properties.Id.ge(25))
                    )
                )
                it.build().list()

            } as MutableList<ChildEntity>
        Log.e("--------childList：", childList?.size.toString() + "\n" + childList.toString())
    }

    override fun getODFailed(msg: Any) {
        Toast.makeText(context, msg.toString(), Toast.LENGTH_SHORT).show()
    }

    override val contentLayoutId: Int
        get() = R.layout.fragment_further

    override val presenter: FurtherPresenter?
        get() = FurtherPresenter(this, FurtherModel())

    private var mAdapter: FurtherAdapter<MutableList<ChildEntity>>? = null

    var mDatas: MutableList<BaseMultiItemEntity<MutableList<ChildEntity>>> = mutableListOf()

    override fun initViews() {
        fragment_details_recyclerView.apply {
            fragment_details_recyclerView.layoutManager = LinearLayoutManager(context)
            mAdapter = FurtherAdapter()
            fragment_details_recyclerView.adapter = mAdapter
        }
        addHeader()
    }

    private fun addHeader() {

        var view = layoutInflater.inflate(R.layout.status_bar_height_layout, null, false) as LinearLayout
        var textView = view.findViewById<TextView>(R.id.status_bar_heightTv)
        textView.layoutParams.apply {
            height = StatusBarUtil.getStatusBarHeight(context!!).run {
                (this!! * 1.8).toInt()
            }
        }

        mAdapter?.addHeaderView(view)
    }

    override fun initEvents() {
        presenter?.apply {
            getPostTest(this@FurtherFragment)
        }
    }
}
