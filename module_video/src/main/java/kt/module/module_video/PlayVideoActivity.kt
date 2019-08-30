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
import kotlinx.android.synthetic.main.activity_play_video.*
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

    companion object {
        const val TAG = "PlayVideoActivity"
    }

    private var disposable: Disposable? = null
    private var progress: Int? = 0

    override val contentLayoutId: Int
        get() = R.layout.activity_play_video

    override fun initViews() {
//        play_video_ijkPlayerView.play("http://qcmedia.starschinalive.com/video/2019/5/14/20195141557805343303_61_4804.mp4?sign=afc451551425a71aade47e1e69895598&t=1567149465")
        play_video_ijkPlayerView.play("http://qcmedia.starschinalive.com/video/2019/6/6/2019661559805283300_21_4479.mp4?sign=427ad4a95629d09fff38d387225028ab&t=1567151178")

    }

    @SuppressLint("CheckResult")
    override fun initEvents() {
        EventBus.getDefault().register(this)
        play_video_widgets_play_pauseCbx.setOnClickListener(this)

        /**
         * 播放器设置监听
         */
        play_video_ijkPlayerView.setPlayerListener(object : ThinkoPlayerListener {
//            override fun onSeekCompletion(progress: Int) {
//
//            }

            override fun onInfo(what: Int, extra: Int): Boolean {
                return false
            }

            override fun onPrepared() {
                play_video_widgets_total_positionTv.text =
                    DateUtil.timeFormat((play_video_ijkPlayerView.duration) / 1000)

                play_video_widgets_play_pauseCbx.isChecked = play_video_ijkPlayerView.isPlaying
            }

            override fun onBuffer(percent: Int) {
                Log.e(TAG, "onBuffer".plus(percent.toString()))
            }

            override fun onCompletion() {
                Log.e(TAG, "onCompletion")
            }

            override fun onError(what: Int, extra: Int): Boolean {
                return false
            }

            override fun onNetworkSpeedUpdate(speed: Int) {

            }
        })

        play_video_widgets_seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.e(TAG, "onProgressChanged" + seekBar!!.progress)
                this@PlayVideoActivity.progress = progress
//                play_video_ijkPlayerView.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.e(TAG, "onStartTrackingTouch" + seekBar?.progress)
//                play_video_ijkPlayerView.pause()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.e(TAG, "onStopTrackingTouch" + seekBar?.progress)

                if (seekBar!!.progress == 0){
                    play_video_ijkPlayerView.seekTo(0)
                }else{
                    val seekToTime = (play_video_ijkPlayerView.duration / 100 * (seekBar!!.progress + 1)).toInt()
                    play_video_ijkPlayerView.seekTo(seekToTime)
                }
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

                currentPosition = play_video_ijkPlayerView.currentPosition
                EventBus.getDefault().post(BaseEvent(PLAY_PROGRESS, currentPosition))
            }
    }

    private var currentPosition: Long = 0L

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

    override fun onResume() {
        super.onResume()
        if (play_video_ijkPlayerView != null){
            play_video_ijkPlayerView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        if (play_video_ijkPlayerView != null && play_video_ijkPlayerView.isPlaying){
            play_video_ijkPlayerView.pause()
            EventBus.getDefault().post(BaseEvent<Any>(PLAY_PAUSE))
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