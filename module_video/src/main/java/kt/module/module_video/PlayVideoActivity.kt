package kt.module.module_video

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.SeekBar
import com.alibaba.android.arouter.facade.annotation.Route
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_play_video_controller_widgets.*
import kotlinx.android.synthetic.main.layout_play_video_window.*
import kt.module.base_module.base.presenter.IBasePresenter
import kt.module.base_module.base.view.BaseActivity
import kt.module.base_module.constant.ConstantEvent.PLAY_PAUSE
import kt.module.base_module.constant.ConstantEvent.PLAY_PROGRESS
import kt.module.base_module.constant.ConstantEvent.PLAY_START
import kt.module.base_module.data.bean.BaseEvent
import kt.module.base_module.utils.DateUtil
import kt.module.base_module.utils.RouteUtils
import kt.module.module_video.views.ThinkoPlayerListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import tv.danmaku.ijk.media.player.IMediaPlayer
import java.util.concurrent.TimeUnit

@Route(path = RouteUtils.RouterMap.Play.PlayVideoAc)
class PlayVideoActivity : BaseActivity<IBasePresenter>(), View.OnClickListener {

    private var disposable: Disposable? = null
    private var progress: Int? = 0

    override val contentLayoutId: Int
        get() = R.layout.activity_play_video

    override fun initViews() {
        play_video_ijkPlayerView.play("http://qcmedia.starschinalive.com/video/2019/3/30/20193301553928326321_21_4683.mp4?sign=700375653f8b0df29adb2387f2082a82&t=1566465305")

        play_video_widgets_play_pauseCbx.isChecked = play_video_ijkPlayerView.isPlaying
    }

    @SuppressLint("CheckResult")
    override fun initEvents() {
        EventBus.getDefault().register(this)
        play_video_widgets_play_pauseCbx.setOnClickListener(this)

        /**
         * 播放器设置监听
         */
        play_video_ijkPlayerView.setPlayerListener(object : ThinkoPlayerListener {
            override fun onPrepared() {
                play_video_widgets_total_positionTv.text =
                    DateUtil.timeFormat((play_video_ijkPlayerView.duration) / 1000)
            }

            override fun onBuffer(percent: Int) {
                Log.e("---------percent", percent.toString())
            }

            override fun onCompletion() {

            }

            override fun onError(what: Int, extra: Int): Boolean {
                return onError(what, extra)
            }

            override fun onNetworkSpeedUpdate(speed: Int) {

            }
        })

        play_video_widgets_seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.e("-----------", "onProgressChanged" + progress)
                this@PlayVideoActivity.progress = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.e("-----------", "onStartTrackingTouch")
                play_video_ijkPlayerView.pause()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.e("-----------", "onStopTrackingTouch")
                play_video_ijkPlayerView.seekTo(this@PlayVideoActivity.progress!!)
                play_video_ijkPlayerView.start()
            }
        })

        /**
         * 实时获取进度
         */
        disposable = Observable.interval(300, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (!play_video_ijkPlayerView.isPlaying) {
                    return@subscribe
                }
                var currentPosition = play_video_ijkPlayerView.currentPosition
                EventBus.getDefault().post(BaseEvent(PLAY_PROGRESS, currentPosition))
            }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.play_video_widgets_play_pauseCbx -> playOrPause()
        }
    }

    private fun playOrPause() {
        if (play_video_ijkPlayerView.isPlaying) {
            play_video_ijkPlayerView.pause()
            EventBus.getDefault().post(BaseEvent<Boolean>(PLAY_PAUSE))
        } else {
            play_video_ijkPlayerView.start()
            EventBus.getDefault().post(BaseEvent<Boolean>(PLAY_START))
        }
        play_video_widgets_play_pauseCbx.isChecked = play_video_ijkPlayerView.isPlaying
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: BaseEvent<*>) {
        when (event.code) {
//            PLAY_START -> play_video_widgets_play_pauseCbx.background = resources.getDrawable(R.mipmap.icon_player_play_img)
//            PLAY_PAUSE -> play_video_widgets_play_pauseCbx.background = resources.getDrawable(R.mipmap.icon_player_pause_img)
            PLAY_PROGRESS -> {
                if (event.data is Long) {

                    play_video_widgets_current_positionTv.text =
                        DateUtil.timeFormat((event.data as Long) / 1000) //当前播放时间

                    play_video_widgets_seekBar.progress =
                        ((event.data as Long) * 100 / play_video_ijkPlayerView.duration).toInt() //设置当前进度条位置
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}