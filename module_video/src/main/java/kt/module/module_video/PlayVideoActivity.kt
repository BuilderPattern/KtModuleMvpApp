package kt.module.module_video

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_play_video.*
import kotlinx.android.synthetic.main.layout_play_video_controller_widgets.*
import kotlinx.android.synthetic.main.layout_play_video_window.*
import kt.module.module_base.adapter.BaseRvQuickAdapter
import kt.module.module_base.adapter.BaseRvViewHolder
import kt.module.module_base.constant.ConstantEvent.EVENT_FULL_SCREEN
import kt.module.module_base.constant.ConstantEvent.PLAY_COMPLETE
import kt.module.module_base.constant.ConstantEvent.PLAY_PAUSE
import kt.module.module_base.constant.ConstantEvent.PLAY_PREPARED
import kt.module.module_base.constant.ConstantEvent.PLAY_PROGRESS
import kt.module.module_base.constant.ConstantEvent.PLAY_START
import kt.module.module_base.constant.ConstantEvent.PLAY_STOP
import kt.module.module_base.constant.ConstantEvent.PLAY_WINDOW_STATUS
import kt.module.module_base.data.bean.BaseEvent
import kt.module.module_base.data.db.table.RvData
import kt.module.module_base.utils.DataUtils
import kt.module.module_base.utils.DateUtil
import kt.module.module_base.utils.RouteUtils
import kt.module.module_video.entities.VideoInfoEntity
import kt.module.module_video.utils.OrientationUtil
import kt.module.module_video.views.ThinkoPlayerListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit

@Route(path = RouteUtils.RouterMap.Play.PlayVideoAc)
class PlayVideoActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val TAG = "PlayVideoActivity"
    }

    private var disposable: Disposable? = null
    private var disposableWindow: Disposable? = null
    private var progress: Int? = 0

    private var mOrientationUtil: OrientationUtil? = null

    private var mForceLandscapePlay: Boolean = false//是否强制横屏
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)
        initViews()
        initEvents()
    }

    private fun initViews() {
        mOrientationUtil = OrientationUtil(this)
//        mOrientationUtil!!.setForceLandscapePlay(mForceLandscapePlay)
    }

    @SuppressLint("CheckResult")
    private fun initEvents() {
        EventBus.getDefault().register(this)
        play_video_widgets_backTv.setOnClickListener(this)
        play_video_widgets_play_pauseCbx.setOnClickListener(this)
        play_video_widgets_full_screenTv.setOnClickListener(this)
        play_video_ijkPlayerView.setOnClickListener(this)

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

                postDelayWindowStatus(1000, PLAY_PREPARED)
            }

            override fun onBuffer(percent: Int) {
                Log.e(TAG, "onBuffer".plus(percent.toString()))
            }

            override fun onCompletion() {
                Log.e(TAG, "onCompletion")
                pause()

                EventBus.getDefault().post(BaseEvent(PLAY_COMPLETE, VideoInfoEntity("战狼")))
            }

            override fun onError(what: Int, extra: Int): Boolean {
                play_video_widgets_play_pauseCbx.isChecked = false
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
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar!!.progress == 0) {
                    play_video_ijkPlayerView.seekTo(0)
                } else {
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

//        play_video_ijkPlayerView.play("http://qcmedia.starschinalive.com/video/2019/6/6/2019661559805234563_21_4868.mp4?sign=9380751f05679ac86b842f0da4467508&t=1567391713")
//        play_video_ijkPlayerView.play("http://qcmedia.starschinalive.com/video/2019/6/6/2019661559805283300_21_4479.mp4?sign=181903c61c42da136f0135b5656d5435&t=1567483045")
        play_video_ijkPlayerView.play("http://qcmedia.starschinalive.com/video/2019/6/6/2019661559805108072_21_4112.mp4?sign=442329a212581d44abd0027ea08c6228&t=1567587290")
        EventBus.getDefault().post(BaseEvent(PLAY_START, VideoInfoEntity()))

        setOrientation(mForceLandscapePlay)

        //列表数据
        initData()
    }

    private val mDatas: MutableList<RvData> = mutableListOf()
    private var mAdapter: BaseRvQuickAdapter<RvData>? = null
    private fun initData() {
        (layout_play_video_recyclerView as RecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            mAdapter = object : BaseRvQuickAdapter<RvData>(R.layout.item_play_video_layout, mDatas) {
                override fun convert(holder: BaseRvViewHolder?, item: RvData) {
                    item.run {
                        holder?.apply {
                            setSimpleDraweeViewUrl(R.id.item_home_simpleDraweeView, url)
                            setText(R.id.item_home_nameTv, name)
                            setText(R.id.item_home_ageTv, age.toString())
                        }
                    }
                }
            }
            adapter = mAdapter
        }

        val data = DataUtils.createData(3)
        mDatas.addAll(data)
        mAdapter?.notifyDataSetChanged()
    }

    private var currentPosition: Long = 0L

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.play_video_widgets_backTv -> onBackPressed()
            R.id.play_video_widgets_play_pauseCbx -> playOrPause()
            R.id.play_video_widgets_full_screenTv -> {
                if (mForceLandscapePlay) {
                    mOrientationUtil!!.clickToPortrait()
                } else {
                    mOrientationUtil!!.clickToLandscape()
                }
            }
            R.id.play_video_ijkPlayerView -> {
                if (play_video_widgets_rly.visibility == GONE) {
                    play_video_widgets_rly.visibility = VISIBLE
                } else {
                    play_video_widgets_rly.visibility = GONE
                }
                disposableWindow?.dispose()
                postDelayWindowStatus(3000, PLAY_WINDOW_STATUS)
            }
        }
    }

    private fun playOrPause() {
        if (play_video_ijkPlayerView.isPlaying) {
            pause()
        } else {
            play()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: BaseEvent<*>) {
        when (event.code) {
//            PLAY_START ->
//            PLAY_PAUSE ->
            PLAY_PROGRESS -> {
                if (event.data is Long) {

                    play_video_widgets_current_positionTv.text =
                        DateUtil.timeFormat((event.data as Long) / 1000) //当前播放时间

                    play_video_widgets_seekBar.progress =
                        ((event.data as Long) * 100 / play_video_ijkPlayerView.duration).toInt() //设置当前进度条位置
                }
            }
            EVENT_FULL_SCREEN -> {
                if (event.data is Boolean) {
                    mForceLandscapePlay = event.data as Boolean
                    setOrientation(mForceLandscapePlay)
                }
            }
            PLAY_WINDOW_STATUS -> {
                play_video_widgets_rly.visibility = GONE
            }
            PLAY_PREPARED -> play_video_widgets_play_pauseCbx.isChecked = play_video_ijkPlayerView.isPlaying
        }
    }

    @SuppressLint("CheckResult")
    private fun setOrientation(fullScreen: Boolean?) {
        if (fullScreen == null) {
            return
        }

        disposableWindow?.dispose()

        if (!fullScreen) {//竖屏
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 560)
            play_video_rly.layoutParams = lp
        } else {//横屏

            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            play_video_rly.layoutParams = lp
        }
        postDelayWindowStatus(3000, PLAY_WINDOW_STATUS)
    }

    private fun postDelayWindowStatus(time: Long, status: Int) {
        disposableWindow = Observable.timer(time, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).subscribe {
            EventBus.getDefault().post(BaseEvent<Any>(status))
        }
    }

    private fun play() {
        play_video_ijkPlayerView.start()
        play_video_widgets_play_pauseCbx.isChecked = true
        EventBus.getDefault().post(BaseEvent(PLAY_START, VideoInfoEntity()))
    }

    private fun pause() {
        play_video_ijkPlayerView.pause()
        play_video_widgets_play_pauseCbx.isChecked = false
        EventBus.getDefault().post(BaseEvent(PLAY_PAUSE, VideoInfoEntity()))
    }

    override fun onStart() {
        super.onStart()
        mOrientationUtil!!.start()
    }

    override fun onResume() {
        super.onResume()
        if (play_video_ijkPlayerView != null) {
            play()
        }
    }

    override fun onPause() {
        super.onPause()
        if (play_video_ijkPlayerView != null && play_video_ijkPlayerView.isPlaying) {
            pause()
        }
    }

    override fun onStop() {
        super.onStop()
        mOrientationUtil!!.stop()
        EventBus.getDefault().post(BaseEvent(PLAY_STOP, VideoInfoEntity()))
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        disposable?.dispose()
        disposableWindow?.dispose()
    }

    override fun onBackPressed() {
        if (!mOrientationUtil!!.onBackPressed()) {
            play_video_ijkPlayerView.release()
            finish()
        }
    }
//    fun switchLock() {
//        mLocked.set(!mLocked.get())
//        EventBus.getDefault().post(EventMessage(PlayerEvent.EVENT_SWITCH_LOCK, mLocked.get()))
//    }

}