package kt.module.module_video

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_play_video_controller_widgets.*
import kotlinx.android.synthetic.main.layout_play_video_window.*
import kt.module.module_base.adapter.BaseRvQuickAdapter
import kt.module.module_base.adapter.BaseRvViewHolder
import kt.module.module_base.constant.ConstantEvent.EVENT_FULL_SCREEN
import kt.module.module_base.constant.ConstantEvent.EVENT_SWITCH_DEFINITION
import kt.module.module_base.constant.ConstantEvent.EVENT_SWITCH_LOCK
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
import kt.module.module_video.definition.DefinitionMenuAdapter
import kt.module.module_video.entities.VideoInfoEntity
import kt.module.module_video.utils.OrientationUtil
import kt.module.module_video.views.IjkPlayerListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import tv.danmaku.ijk.media.player.IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START
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
        play_video_widgets_video_lockImg.setOnClickListener(this)
        play_video_widgets_play_pauseCbx.setOnClickListener(this)
        play_video_widgets_switch_definitionTv.setOnClickListener(this)
        play_video_widgets_full_screenTv.setOnClickListener(this)
        play_video_ijkPlayerView.setOnClickListener(this)

        /**
         * 播放器设置监听
         */
        play_video_ijkPlayerView.setPlayerListener(object : IjkPlayerListener {

            override fun onInfo(what: Int, extra: Int): Boolean {
                if (what == MEDIA_INFO_AUDIO_RENDERING_START) {//开始播放有声音了
                    play_video_widgets_total_positionTv.text =
                        DateUtil.timeFormat((play_video_ijkPlayerView.duration) / 1000)
                    EventBus.getDefault().post(BaseEvent<Any>(PLAY_START))
                }
                return false
            }

            override fun onPrepared() {
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

        play_video_ijkPlayerView.play("http://xxx.xxx.com/video/2019/6/6/2019661559804461397_21_415.mp4?sign=e5889e5edb9f8f7996dba512d273a115&t=1567734070")

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
            R.id.play_video_widgets_video_lockImg -> switchLock()
            R.id.play_video_widgets_play_pauseCbx -> playOrPause()
            R.id.play_video_widgets_switch_definitionTv -> {
                initChooseDefinitionMenu()
            }
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

                layout_play_video_right_Lly.visibility = GONE
                disposableWindow?.dispose()
                postDelayWindowStatus(3000, PLAY_WINDOW_STATUS)
            }
        }
    }

    var currLevel: Int? = 0

    var chooseMenuList: MutableList<VideoInfoEntity> = mutableListOf()
    var definitionMenuAdapter: DefinitionMenuAdapter? = null
    private fun initChooseDefinitionMenu() {
        layout_play_video_right_Lly.visibility = VISIBLE
        if (chooseMenuList.size == 0 || chooseMenuList == null) {
            chooseMenuList.add(
                VideoInfoEntity(
                    "战狼",
                    "http://xxx.xxx.com/video/2019/6/6/2019661559804461397_21_415.mp4?sign=e5889e5edb9f8f7996dba512d273a115&t=1567734070",
                    level = 0
                )
            )
            chooseMenuList.add(
                VideoInfoEntity(
                    "战狼",
                    "http://xxx.xxx.com/video/2019/6/6/2019661559804461397_21_11.mp4?sign=0f1025812295ff88a788cb0ba8fc1891&t=1567734070",
//                    "http://qcmedia.starschinalive.com/video/2019/6/7/2019671559878526610_21_4835.mp4?sign=7d9ef3dd336667d51b440b555e072157&t=1567743376",
                    level = 1
                )
            )
        }
        if (definitionMenuAdapter == null) {
            definitionMenuAdapter = DefinitionMenuAdapter(R.layout.item_choose_definition, chooseMenuList)
            layout_play_video_recyclerView.layoutManager = LinearLayoutManager(this)
            layout_play_video_recyclerView.adapter = definitionMenuAdapter
        } else {
            definitionMenuAdapter?.notifyDataSetChanged()
        }

        definitionMenuAdapter?.setOnItemClickListener { adapter, view, position ->
            var videoInfoEntity: VideoInfoEntity = chooseMenuList[position]
            if (currLevel != videoInfoEntity.level) {
                videoInfoEntity.currPosition = play_video_ijkPlayerView.currentPosition
                EventBus.getDefault().post(BaseEvent(EVENT_SWITCH_DEFINITION, videoInfoEntity))
                layout_play_video_right_Lly.visibility = GONE
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
            PLAY_START -> play_video_widgets_play_pauseCbx.isChecked = play_video_ijkPlayerView.isPlaying
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
            EVENT_SWITCH_DEFINITION -> {
                play_video_widgets_bottom_lly.visibility = VISIBLE
                if (event.data is VideoInfoEntity) {
                    switchDefinition(event.data as VideoInfoEntity)
                }
            }
//            PLAY_PREPARED -> play_video_widgets_play_pauseCbx.isChecked = play_video_ijkPlayerView.isPlaying
            EVENT_SWITCH_LOCK -> {
                if (event.data is Boolean) {

//                    play_video_widgets_bottom_lly.apply {
//                        visibility = VISIBLE
//                        takeIf {
//                            event.data as Boolean
//                        }?.let {
//                            visibility = GONE
//                        }
//                    }
                    play_video_widgets_bottom_lly.run {
                        visibility = VISIBLE
                        takeIf {
                            event.data as Boolean
                        }?.let {
                            visibility = GONE
                        }
                    }
                }
            }
        }
    }

    private fun switchDefinition(videoInfoEntity: VideoInfoEntity) {
        currLevel = videoInfoEntity.level
        play_video_ijkPlayerView.play(videoInfoEntity.url)
        play_video_ijkPlayerView.seekTo(videoInfoEntity.currPosition.toInt())
        when (videoInfoEntity.level) {
            0 -> play_video_widgets_switch_definitionTv.text = "流畅"
            1 -> play_video_widgets_switch_definitionTv.text = "高清"
            2 -> play_video_widgets_switch_definitionTv.text = "1080"
        }

    }

    /**
     * 横竖屏切换设置
     */
    @SuppressLint("CheckResult")
    private fun setOrientation(fullScreen: Boolean?) {
        if (fullScreen == null) {
            return
        }

        disposableWindow?.dispose()

        if (!fullScreen) {//竖屏

            play_video_widgets_video_lockImg.visibility = GONE
            play_video_widgets_switch_definitionTv.visibility = GONE

            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 560)
            play_video_rly.layoutParams = lp
        } else {//横屏

            play_video_widgets_video_lockImg.visibility = VISIBLE
            play_video_widgets_switch_definitionTv.visibility = VISIBLE

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

    private var mLocked: Boolean = false//是否屏幕锁定
    private fun switchLock() {
        mLocked = !mLocked
        if (mLocked) {
            play_video_widgets_video_lockImg.setImageResource(R.mipmap.icon_lock_img)
        } else {
            play_video_widgets_video_lockImg.setImageResource(R.mipmap.icon_unlock_img)
        }
        mOrientationUtil?.lock(mLocked)
        EventBus.getDefault().post(BaseEvent(EVENT_SWITCH_LOCK, mLocked))
    }
}