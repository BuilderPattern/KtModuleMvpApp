package kt.module.further_module.mvp

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.fragment_further.*
import kt.module.BaseApp
import kt.module.base_module.adapter.FurtherAdapter
import kt.module.base_module.base.entity.FurtherMultiItemEntity
import kt.module.base_module.base.view.BaseFragment
import kt.module.base_module.data.db.dao.ChildEntityDao
import kt.module.base_module.data.db.dao.ObjectEntityDao
import kt.module.base_module.data.db.table.ChildEntity
import kt.module.base_module.data.db.table.ObjectEntity
import kt.module.base_module.data.db.utils.DbUtils
import kt.module.base_module.utils.RouteUtils
import kt.module.base_module.utils.StatusBarUtil
import kt.module.further_module.R

@Route(path = RouteUtils.RouterMap.FurtherPage.Further)
class FurtherFragment : BaseFragment<FurtherPresenter>(), FurtherContract.IFurtherView {

    override fun getODSuccessed(data: MutableList<ObjectEntity>?) {

        mDatas.clear()
        data?.forEach {
            var item =
                FurtherMultiItemEntity<MutableList<ChildEntity>>()
            if (it.show_template == 1) {
                item.type = 1
                item.title = "竖直方向"
            } else {
                item.type = 2
                item.title = "水平方向"
            }
            childList.clear()
            childList.addAll(it.child)
            item.data = childList

            mDatas.add(item)
        }
        mAdapter?.setNewData(mDatas)

        data?.forEach { objEntity ->
            objEntity.child.forEach { childEntity ->
                if (objEntity.show_template == 1) {
                    childEntity.objectId = 1
                } else {
                    childEntity.objectId = 2
                }
            }
            DbUtils.getInstance().deleteAllNote(ObjectEntity::class.java)
            DbUtils.getInstance().insertOrReplaceList(objEntity.child, ChildEntity::class.java)
        }
        DbUtils.getInstance().insertOrReplaceList(data, ObjectEntity::class.java)

        var objList = DbUtils.getInstance().getAnyDaoByTable(ObjectEntity::class.java).queryBuilder().let {
            it?.orderDesc(ObjectEntityDao.Properties.Id)
//            it.build().list()
            it.where(ObjectEntityDao.Properties.Show_template.eq(1))
            it?.build()?.list()
        }
        Log.e("--------objList：", objList?.size.toString() + "\n" + objList.toString())

        var childList = DbUtils.getInstance().getAnyDaoByTable(ChildEntity::class.java).queryBuilder().let {
//            it?.orderDesc(ChildEntityDao.Properties.Id)
//            it.build().list()
//            it.where(ChildEntityDao.Properties.ObjectId.eq(1))
            it?.build()?.list()
        }
        Log.e("--------childList：", childList?.size.toString() + "\n" + childList.toString())
    }

    override fun getODFailed(msg: Any) {
        Toast.makeText(context, msg.toString(), Toast.LENGTH_SHORT).show()
    }

    var childList: MutableList<ChildEntity> = mutableListOf()

    override val contentLayoutId: Int
        get() = R.layout.fragment_further

    override val presenter: FurtherPresenter?
        get() = FurtherPresenter(this, FurtherModel())

    private var mAdapter: FurtherAdapter<MutableList<ChildEntity>>? = null

    var mDatas: MutableList<FurtherMultiItemEntity<MutableList<ChildEntity>>> = mutableListOf()

    override fun initViews() {
        fragment_details_recyclerView.layoutManager = LinearLayoutManager(context)

        mAdapter = FurtherAdapter()
        fragment_details_recyclerView.adapter = mAdapter
        addHeader()
    }

    private fun addHeader() {

        var view =
            LayoutInflater.from(context).inflate(R.layout.status_bar_height_layout, null, false) as LinearLayout
        var textView = view.findViewById<TextView>(R.id.status_bar_heightTv)
        val layoutParams = textView.layoutParams
        layoutParams.height = (StatusBarUtil.getStatusBarHeight(context!!)!! * 1.5).toInt()
        mAdapter?.addHeaderView(view)
    }

    override fun initEvents() {
        presenter?.getPostTest(this)
    }
}
