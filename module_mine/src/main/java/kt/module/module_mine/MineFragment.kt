package kt.module.module_mine

import android.util.Log
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.fragment_mine.*
import kt.module.module_base.base.presenter.IBasePresenter
import kt.module.module_base.base.view.BaseFragment
import kt.module.module_base.data.download.DownLoadInfoEntity
import kt.module.module_base.utils.DownLoadUtil
import kt.module.module_base.utils.Permission
import kt.module.module_base.utils.PermissionUtil
import kt.module.module_base.utils.RouteUtils
import kt.module.module_base.view.ConfirmDialog
import me.jessyan.autosize.AutoSizeCompat

@Route(path = RouteUtils.RouterMap.MinePage.Mine)
class MineFragment : BaseFragment<IBasePresenter>(), View.OnClickListener {

    override val contentLayoutId: Int
        get() = R.layout.fragment_mine

    override fun initViews() {

    }

    private var downLoadInfoEntity: DownLoadInfoEntity? = null
    private var confirmDialog: ConfirmDialog? = null
    override fun initEvents() {
        fragment_mine_firstTv.setOnClickListener(this)
        fragment_mine_secondTv.setOnClickListener(this)
        fragment_mine_updateTv.setOnClickListener(this)

        downLoadInfoEntity = DownLoadInfoEntity().apply {
            var url = "http://resource.shenqinaobo.com/android/tus/music/chanting.music"
            this.url = url
            fileName = "down"
            fileType = "mp4"
        }

        confirmDialog = ConfirmDialog(context).apply {
            setTitle("下载内容")
            setContent(downLoadInfoEntity?.fileName.plus(".").plus(downLoadInfoEntity?.fileType))
            setOnConfirmClickListener(object : ConfirmDialog.OnConfirmClickListener {
                override fun confirmClickListener() {
                    dismiss()

                    var permissions = arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    AutoSizeCompat.autoConvertDensity(resources, 667f, false)
                    PermissionUtil.getPermission(
                        this@MineFragment,
                        permissions,
                        false,
                        object : Permission.GrantedSuccess {
                            override fun onGranted() {
                                downLoad(downLoadInfoEntity!!)
                            }
                        })
                }
            })
            setOnCancelClickListener(object : ConfirmDialog.OnCancelClickListener {
                override fun cancelClickListener() {
                }
            })
        }
    }

    private fun ConfirmDialog.downLoad(downLoadInfoEntity: DownLoadInfoEntity) {
        if (downLoadInfoEntity.isComplete) {
            Toast.makeText(context, "已经下载！", Toast.LENGTH_SHORT).show()
            return
        } else {
            DownLoadUtil().downLoad(downLoadInfoEntity, object : DownLoadUtil.DownLoadInfoListener {
                override fun downLoadProgress(downLoadInfoEntity: DownLoadInfoEntity) {
                }

                override fun downLoadSuccessed(downLoadInfoEntity: DownLoadInfoEntity) {
                    Log.e("--------listener：", "success")
                }

                override fun downLoadFailed(downLoadInfoEntity: DownLoadInfoEntity) {
                    Log.e("--------listener：", "failed")
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fragment_mine_firstTv -> RouteUtils.go(RouteUtils.RouterMap.First.FirstAc).navigation()
            R.id.fragment_mine_secondTv -> RouteUtils.go(RouteUtils.RouterMap.Second.SecondAc).navigation()
            R.id.fragment_mine_updateTv -> {
                if (!confirmDialog!!.isShowing) {
                    AutoSizeCompat.autoConvertDensity(resources, 667f, false)
                    confirmDialog?.show()
                }
            }
        }
    }
}