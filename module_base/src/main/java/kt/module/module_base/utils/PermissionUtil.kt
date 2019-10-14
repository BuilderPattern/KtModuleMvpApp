package kt.module.module_base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import kt.module.module_common.R
import kt.module.module_common.constant.Constant
import me.jessyan.autosize.AutoSizeCompat
import org.jetbrains.anko.alert
import org.jetbrains.anko.support.v4.alert

@SuppressLint("CheckResult")
object PermissionUtil {
    private const val type_denid = 0//重新获取权限
    private const val type_denid_never_ask = 1//权限被永久禁止，前往设置界面

    /**
     * activity中使用，可以关闭授权界面的权限申请
     * @param activity RxAppCompatActivity 当前的activity
     * @param permissions Array<String> 权限数据数组
     * @param cancel Boolean   是否可以人为关闭授权界面。默认可以关闭
     * @param permissionCallback Permission.GrantedSuccess 授权成功的回调
     */
    fun getPermission(
        activity: RxAppCompatActivity,
        permissions: Array<String>,
        cancel: Boolean = true,
        permissionCallback: Permission.GrantedSuccess
    ) {
        var p: RxPermissions? = RxPermissions(activity)
        p!!.requestEachCombined(*permissions)
            .subscribe { permission ->
                when {
                    //已获得所有申请权限
                    permission.granted -> {
                        permissionCallback.onGranted()
                        val c = p.javaClass
                        val f = c.getDeclaredField("mRxPermissionsFragment")
                        f.isAccessible = true
                        f.set(p, null)
                    }
                    //至少一个权限未授予
                    permission.shouldShowRequestPermissionRationale -> {
                        alert(
                            type_denid,
                            activity,
                            permissions,
                            cancel,
                            permissionCallback
                        ) { activity, permissions, cancel, permissionCallback ->
                            getPermission(activity, permissions, cancel, permissionCallback)
                        }
                    }
                    //至少一个权限选了不再询问
                    else -> {
                        alert(
                            type_denid_never_ask,
                            activity,
                            permissions,
                            cancel,
                            permissionCallback
                        ) { activity, _, _, _ ->
                            goAppPermissionSetting(activity)
                        }
                    }
                }
            }
    }

    /**
     * fragment的权限请求
     */
    fun getPermission(
        fragment: RxFragment,
        permissions: Array<String>,
        cancel: Boolean = true,
        permissionCallback: Permission.GrantedSuccess
    ) {
        RxPermissions(fragment).requestEachCombined(*permissions)
            .subscribe {
                when {
                    //已获得所有申请权限
                    it.granted -> permissionCallback.onGranted()
                    //至少一个权限未授予
                    it.shouldShowRequestPermissionRationale -> {
                        alert(
                            type_denid,
                            fragment,
                            permissions,
                            cancel,
                            permissionCallback
                        ) { fragment, permissions, cancel, permissionCallback ->
                            getPermission(fragment, permissions, cancel, permissionCallback)
                        }
                    }
                    //至少一个权限点击了再也不询问
                    else -> {
                        alert(
                            type_denid_never_ask,
                            fragment,
                            permissions,
                            cancel,
                            permissionCallback
                        ) { fragment, _, _, _ ->
                            goAppPermissionSetting(fragment.context!!)
                        }
                    }
                }
            }
    }

    /**
     * 前往权限设置界面
     */
    private fun goAppPermissionSetting(context: Context) {
        val settingIntent = Intent()
        settingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        settingIntent.action = Constant.ACTION_GO_SETTING
        settingIntent.data = Uri.fromParts(Constant.PACKAGE, Constant.APP_ID, null)
        context.startActivity(settingIntent)
    }

    /**
     * 创建activity的权限alert
     * @param type Int 0代表重新拉取权限的alert字符串资源， 1代表去app设置界面的alert字符串资源
     * @param activity RxAppCompatActivity
     * @param permissions Array<String>
     * @param cancel Boolean
     * @param permissionCallback Permission.GrantedSuccess
     * @param block (RxAppCompatActivity, Array<String>, Boolean, Boolean, Permission.GrantedSuccess) -> Unit
     */
    private fun alert(
        type: Int,
        activity: RxAppCompatActivity,
        permissions: Array<String>,
        cancel: Boolean = true,
        permissionCallback: Permission.GrantedSuccess,
        block: (RxAppCompatActivity, Array<String>, Boolean, Permission.GrantedSuccess) -> Unit
    ) {

        AutoSizeCompat.autoConvertDensity(activity.resources, 667f, false)
        activity.alert {
            title = ResourceUtil.getResString(R.string.string_permission_title)
            message =
                if (type == type_denid) ResourceUtil.getResString(R.string.string_permission_denid)
                else ResourceUtil.getResString(R.string.string_permission_denid_never)
            isCancelable = false
            positiveButton(
                if (type == type_denid) ResourceUtil.getResString(R.string.string_permission_button_ok)
                else ResourceUtil.getResString(R.string.string_permission_button_go_setting)
            ) {
                it.dismiss()
                block(activity, permissions, cancel, permissionCallback)
            }
            if (cancel) {
                this.negativeButton(ResourceUtil.getResString(R.string.string_permission_button_cancle)) {
                    it.dismiss()
                }
            }
        }.show()
    }

    /**
     * 创建fragment的权限alert
     * @param type Int
     * @param fragment RxFragment
     * @param permissions Array<String>
     * @param cancel Boolean
     * @param permissionCallback Permission.GrantedSuccess
     * @param block (RxFragment, Array<String>, Boolean, Boolean, Permission.GrantedSuccess) -> Unit
     */
    private fun alert(
        type: Int,
        fragment: RxFragment,
        permissions: Array<String>,
        cancel: Boolean = true,
        permissionCallback: Permission.GrantedSuccess,
        block: (RxFragment, Array<String>, Boolean, Permission.GrantedSuccess) -> Unit
    ) {
        fragment.alert {
            title = ResourceUtil.getResString(R.string.string_permission_title)
            message =
                if (type == type_denid) ResourceUtil.getResString(R.string.string_permission_denid)
                else ResourceUtil.getResString(R.string.string_permission_denid_never)
            isCancelable = false
            positiveButton(
                if (type == type_denid) ResourceUtil.getResString(R.string.string_permission_button_ok)
                else ResourceUtil.getResString(R.string.string_permission_button_go_setting)
            ) {
                it.dismiss()
                block(fragment, permissions, cancel, permissionCallback)
            }
            if (cancel) {
                this.negativeButton(ResourceUtil.getResString(R.string.string_permission_button_cancle)) {
                    it.dismiss()
                }
            }
        }.show()
    }
}

interface Permission {
    interface GrantedSuccess {
        fun onGranted()
    }
}