package kt.module.module_base.utils

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import androidx.core.app.NotificationCompat
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import kt.module.module_base.R
import kt.module.module_base.data.download.DownLoadInfoEntity
import java.io.File
import java.text.NumberFormat

class DownLoadUtil {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context

        fun init(context: Context) {
            this.mContext = context
        }
    }

    val numberFormat: NumberFormat

    val notificationManager: NotificationManager

    init {
        numberFormat = NumberFormat.getPercentInstance()
        notificationManager = mContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    var downLoadInfoListener: DownLoadInfoListener? = null

    open fun downLoad(downLoadInfoEntity: DownLoadInfoEntity, downLoadInfoListener: DownLoadInfoListener) {

        this.downLoadInfoListener = downLoadInfoListener

        val file = File(
            Environment.getExternalStorageDirectory(),
            downLoadInfoEntity.fileName.plus(".").plus(downLoadInfoEntity.fileType)
        )

        FileDownloader.getImpl().create(downLoadInfoEntity.url)
            .setPath(file.absolutePath, false)//存储path，是否作为目录
            .setCallbackProgressMinInterval(300)  //progress 刷新间隔
            .setMinIntervalUpdateSpeed(300)//speed 刷新间隔
            .setAutoRetryTimes(2)//下载失败的情况下，最多重试2次
            .setListener(object : FileDownloadListener() {

                @Suppress("DEPRECATION")
                val builder = Notification.Builder(mContext)

                var id = 1

                init {
                    builder.setLargeIcon(BitmapFactory.decodeResource(mContext.resources, R.mipmap.ic_launcher))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(false)
                        .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        var channel = NotificationChannel("kt.module.mvp", "kt_module", NotificationManager.IMPORTANCE_MIN)
                        notificationManager.createNotificationChannel(channel)
                        builder.setChannelId("kt.module.mvp")
                    }
                }

                override fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {
                    //等待下载
                }

                override fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {
                    builder.setContentTitle("下载中..." + numberFormat.format((soFarBytes.toFloat() / totalBytes).toDouble()))
                    builder.setProgress(100, (soFarBytes.toFloat() * 100 / totalBytes.toFloat()).toInt(), false)
                    notificationManager.notify(id, builder.build())

                    downLoadInfoEntity.progress = (soFarBytes * 100 / totalBytes).toString()
                    downLoadInfoListener.downLoadProgress(downLoadInfoEntity)
                }

                override fun completed(task: BaseDownloadTask) {
                    builder.setContentTitle("下载完成，正在检测文件")
                    builder.setProgress(100, 100, false)
                    notificationManager.notify(id, builder.build())

                    downLoadInfoEntity.isComplete = true
                    downLoadInfoListener.downLoadSuccessed(downLoadInfoEntity)
                }

                override fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {
                    //暂停下载
                }

                override fun error(task: BaseDownloadTask, e: Throwable) {
                    downLoadInfoListener.downLoadFailed(downLoadInfoEntity)
                    e.printStackTrace()

                    builder.setContentTitle("下载错误")
                    notificationManager.notify(id, builder.build())
                    //下载错误
//                    notificationManager.cancel(id)
                }

                override fun warn(task: BaseDownloadTask) {
                    //有相同的下载任务
                }
            }).start()
    }

    interface DownLoadInfoListener {
        fun downLoadSuccessed(downLoadInfoEntity: DownLoadInfoEntity)
        fun downLoadFailed(downLoadInfoEntity: DownLoadInfoEntity)
        fun downLoadProgress(downLoadInfoEntity: DownLoadInfoEntity)
    }
}