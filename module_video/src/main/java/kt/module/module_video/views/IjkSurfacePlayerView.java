package kt.module.module_video.views;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import org.greenrobot.eventbus.EventBus;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import java.io.IOException;
import java.util.Map;

public class IjkSurfacePlayerView extends AbstractPlayerView {

    private final static String TAG = "IjkPlayerView";

    private Context mCtx;
    private Context mAppContext;

    private IRenderView.ISurfaceHolder mSurfaceHolder = null;
    private IMediaPlayer mMediaPlayer = null;

    private IRenderView mRenderView;
    private int mVideoSarNum;
    private int mVideoSarDen;

    private int mVideoWidth;
    private int mVideoHeight;
    private int mSurfaceWidth;
    private int mSurfaceHeight;
    private int mVideoRotationDegree;

    //是否硬解
    private boolean mUseMediaCodec = false;

    // all possible internal states
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;


    private static final int SURFACE_BEST_FIT = 0;
    private static final int SURFACE_FILL = 1;
    private int mCurrentSize = SURFACE_FILL;

    // mCurrentState is a VideoView object's current state.
    // mTargetState is the state that a method caller intends to reach.
    // For instance, regardless the VideoView object's current state,
    // calling pause() intends to bring the object to a target state
    // of STATE_PAUSED.
    private int mCurrentState = STATE_IDLE;
    private int mTargetState = STATE_IDLE;

    //
    private int mSeekWhenPrepared;  // recording the seek position while preparing
    private int mCurrentBufferPercentage;

    //
    private static final int[] s_allAspectRatio = {
//            IRenderView.AR_ASPECT_FIT_PARENT,
            IRenderView.AR_ASPECT_FILL_PARENT,
//            IRenderView.AR_ASPECT_WRAP_CONTENT,
            // IRenderView.AR_MATCH_PARENT,
            IRenderView.AR_16_9_FIT_PARENT,
            IRenderView.AR_4_3_FIT_PARENT};
    private int mCurrentAspectRatioIndex = 0;
    private int mCurrentAspectRatio = s_allAspectRatio[0];

    private Uri mUri;
    private Map<String, String> mHeaders;


    //--------------------------------------
    public IjkSurfacePlayerView(Context context){
        super(context);
        init(context);
    }

    public IjkSurfacePlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IjkSurfacePlayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mCtx = context;
        mAppContext = context.getApplicationContext();
        setBackgroundColor(Color.BLACK);
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        SurfaceRenderView renderView = new SurfaceRenderView(getContext());
        setRenderView(renderView);

        EventBus.getDefault().register(this);

        //AudioManager am = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
        //am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    @Override
    public void initPlayer() {

    }

    @Override
    public void initPlayer(String libraryPath) {

    }

    @Override
    public void play(String uri) {
        Log.d(TAG, "[play] url:"+uri);
        if (null !=uri) {
            play(Uri.parse(uri));
        }
    }

    @Override
    public void play(Uri uri) {
        play(uri, null);
    }

    @Override
    public void play(Uri uri, Map<String, String> headers) {
        mUri = uri;
        mHeaders = headers;
        mSeekWhenPrepared = 0;

        openVideo();
        requestLayout();
        invalidate();
    }

    @Override
    public void setPlayerSize(float pivotX, float pivotY, float scaleX, float scaleY) {

    }
    @Override
    public void setVolume(float a, float b) {
        if (isInPlaybackState()){
            mMediaPlayer.setVolume(a,b);
        }
    }
    @Override
    public void start() {
        Log.d(TAG, "[start] state:"+isInPlaybackState());
        if (isInPlaybackState()) {
            mMediaPlayer.start();
            mCurrentState = STATE_PLAYING;
        }
        mTargetState = STATE_PLAYING;
    }

    @Override
    public void seekTo(int msec) {
        if (isInPlaybackState()) {
            mMediaPlayer.seekTo(msec);
            mSeekWhenPrepared = 0;
        }else {
            mSeekWhenPrepared = msec;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public long getCurrentPosition() {
        if (isInPlaybackState()) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public long getDuration() {
        if (isInPlaybackState()) {
            return mMediaPlayer.getDuration();
        }
        return -1;
    }

    @Override
    public void pause() {
        if (isInPlaybackState()) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                mCurrentState = STATE_PAUSED;
            }
        }
        mTargetState = STATE_PAUSED;
    }

    @Override
    public void stop() {
        Log.e(TAG, "stop-0");
        if (mMediaPlayer != null) {
            Log.e(TAG, "stop-1");
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;

            mCurrentState = STATE_IDLE;
            mTargetState = STATE_IDLE;

            AudioManager am = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
            am.abandonAudioFocus(null);
        }

        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    public boolean isPlaying() {
        return isInPlaybackState() && mMediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        if (mMediaPlayer != null) {
            return mCurrentBufferPercentage;
        }
        return 0;
    }

    @Override
    public void removeSurface() {

    }

    @Override
    public void reset() {
        if (mMediaPlayer != null){
            mMediaPlayer.reset();
        }
    }

    @Override
    public void release() {
        release(true);
    }

    @Override
    public void setUserAgent(String userAgent) {

    }

    @Override
    public void useMediaCodec(boolean use) {
        mUseMediaCodec = use;
    }

    @Override
    public void setLastPos(int lastPos) {

    }

    private void setRenderView(IRenderView renderView) {
        Log.d(TAG, "[setRenderView]");
        if (mRenderView != null) {
            removeRenderView();
        }

        if (renderView == null)
            return;

        mRenderView = renderView;
        renderView.setAspectRatio(mCurrentAspectRatio);
        if (mVideoWidth > 0 && mVideoHeight > 0)
            renderView.setVideoSize(mVideoWidth, mVideoHeight);
        if (mVideoSarNum > 0 && mVideoSarDen > 0)
            renderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);

        View renderUIView = mRenderView.getView();
        LayoutParams lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        renderUIView.setLayoutParams(lp);
        addView(renderUIView, lp);

        Log.d(TAG, "[setRenderView] addRenderCallback");
        mRenderView.addRenderCallback(mSHCallback);
        mRenderView.setVideoRotation(mVideoRotationDegree);
    }

    private void removeRenderView() {
        if (mMediaPlayer != null)
            mMediaPlayer.setDisplay(null);

        if (mRenderView != null) {
            View renderUIView = mRenderView.getView();
            mRenderView.removeRenderCallback(mSHCallback);
            mRenderView = null;
            removeView(renderUIView);
        }
    }

    private void bindSurfaceHolder(IMediaPlayer mp, IRenderView.ISurfaceHolder holder) {
        if (mp == null)
            return;

        if (holder == null) {
            mp.setDisplay(null);
            return;
        }

        holder.bindToMediaPlayer(mp);
    }

    IRenderView.IRenderCallback mSHCallback = new IRenderView.IRenderCallback() {
        @Override
        public void onSurfaceChanged(@NonNull IRenderView.ISurfaceHolder holder, int format, int w, int h) {
            Log.e(TAG, "onSurfaceChanged\n");
            if (holder.getRenderView() != mRenderView) {
                Log.e(TAG, "onSurfaceChanged: unmatched render callback\n");
                return;
            }

            mSurfaceWidth = w;
            mSurfaceHeight = h;
            boolean isValidState = (mTargetState == STATE_PLAYING);
            boolean hasValidSize = !mRenderView.shouldWaitForResize() || (mVideoWidth == w && mVideoHeight == h);
            if (mMediaPlayer != null && isValidState && hasValidSize) {
                if (mSeekWhenPrepared != 0) {
                    seekTo(mSeekWhenPrepared);
                }
                start();
            }
        }

        @Override
        public void onSurfaceCreated(@NonNull IRenderView.ISurfaceHolder holder, int width, int height) {
            Log.e(TAG, "onSurfaceCreated\n");
            if (holder.getRenderView() != mRenderView) {
                Log.e(TAG, "onSurfaceCreated: unmatched render callback\n");
                return;
            }

            mSurfaceHolder = holder;
            if (mMediaPlayer != null)
                bindSurfaceHolder(mMediaPlayer, holder);
            else
                openVideo();
        }

        @Override
        public void onSurfaceDestroyed(@NonNull IRenderView.ISurfaceHolder holder) {
            Log.e(TAG, "onSurfaceDestroyed\n");
            if (holder.getRenderView() != mRenderView) {
                Log.e(TAG, "onSurfaceDestroyed: unmatched render callback\n");
                return;
            }

            // after we return from this we can't use the surface any more
            mSurfaceHolder = null;
            // REMOVED: if (mMediaController != null) mMediaController.hide();
            // REMOVED: release(true);
            releaseWithoutStop();
        }
    };

    private void openVideo() {
        Log.d(TAG,"[openVideo]");
        if (mUri == null) {
            // not ready for playback just yet, will try again later
            return;
        }
        // we shouldn't clear the target state, because somebody might have
        // called start() previously
        release(false);

        AudioManager am = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        try {
            mMediaPlayer = createPlayer();

            // TODO: create SubtitleController in MediaPlayer, but we need
            // a context for the subtitle renderers
//            final Context context = getContext();
            // REMOVED: SubtitleController

            // REMOVED: mAudioSession
            mMediaPlayer.setOnPreparedListener(mPreparedListener);
            mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
//            mMediaPlayer.setOnSeekCompleteListener(mSeekCompletionListener);
            mMediaPlayer.setOnErrorListener(mErrorListener);
            mMediaPlayer.setOnInfoListener(mInfoListener);
            mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
            mCurrentBufferPercentage = 0;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                mMediaPlayer.setDataSource(mAppContext, mUri, mHeaders);
            } else {
                mMediaPlayer.setDataSource(mUri.toString());
            }
            bindSurfaceHolder(mMediaPlayer, mSurfaceHolder);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setScreenOnWhilePlaying(true);
            mMediaPlayer.prepareAsync();

            // REMOVED: mPendingSubtitleTracks

            // we don't set the target state here either, but preserve the
            // target state that was there before.
            mCurrentState = STATE_PREPARING;
            Log.d(TAG, "[openVideo] STATE_PREPARING");
        } catch (IOException ex) {
            Log.w(TAG, "Unable to open content: " + mUri, ex);
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            return;
        } catch (IllegalArgumentException ex) {
            Log.w(TAG, "Unable to open content: " + mUri, ex);
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            return;
        } finally {
            // REMOVED: mPendingSubtitleTracks.clear();
            Log.e(TAG, "[openVideo] finally");
        }
    }

    public IMediaPlayer createPlayer() {
        IjkMediaPlayer mediaPlayer = new IjkMediaPlayer();
        mediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_SILENT);

        if (mUseMediaCodec) {
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
        }else {
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0);
        }
        mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 0);
        mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_RV32);
        mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
        mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);

        mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);

        mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", -16);
        mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_idct", -16);

        //低延迟
        mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probesize", 4096);
        mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);

        //mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "user-agent", "sdk_demo");

        return mediaPlayer;
    }

    private void releaseWithoutStop() {
        if (mMediaPlayer != null)
            mMediaPlayer.setDisplay(null);
    }

    /*
     * release the media player in any state
     */
    private void release(boolean cleartargetstate) {
        Log.d(TAG,"[release]-0");
        if (mMediaPlayer != null) {
            Log.d(TAG,"[release]-1");
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            // REMOVED: mPendingSubtitleTracks.clear();
            mCurrentState = STATE_IDLE;
            if (cleartargetstate) {
                mTargetState = STATE_IDLE;
            }

            AudioManager am = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
            am.abandonAudioFocus(null);
        }
        EventBus.getDefault().unregister(this);
    }

    private boolean isInPlaybackState() {
        return (mMediaPlayer != null &&
                mCurrentState != STATE_ERROR &&
                mCurrentState != STATE_IDLE &&
                mCurrentState != STATE_PREPARING);
    }

    //event lisener
    private IMediaPlayer.OnPreparedListener mPreparedListener =
            new IMediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(IMediaPlayer mp) {
                    mCurrentState = STATE_PREPARED;
//                    IjkPlayerView.this.onPrepared();

                    mVideoWidth = mp.getVideoWidth();
                    mVideoHeight = mp.getVideoHeight();

                    int seekToPosition = mSeekWhenPrepared;  // mSeekWhenPrepared may be changed after seekTo() call
                    if (seekToPosition != 0) {
                        seekTo(seekToPosition);
                    }

                    if (mVideoWidth != 0 && mVideoHeight != 0) {
                        Log.d(TAG, "[OnPreparedListener] mVideoWidth:"+mVideoWidth+", mVideoHeight:"+mVideoHeight);
                        Log.d(TAG, "[OnPreparedListener] mVideoSarNum:"+mVideoSarNum+", mVideoSarDen:"+mVideoSarDen);
                        if (mRenderView != null) {
                            mRenderView.setVideoSize(mVideoWidth, mVideoHeight);
                            mRenderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);
                            if (!mRenderView.shouldWaitForResize() || mSurfaceWidth == mVideoWidth && mSurfaceHeight == mVideoHeight) {
                                // We didn't actually change the size (it was already at the size
                                // we need), so we won't get a "surface changed" callback, so
                                // start the video here instead of in the callback.
                                if (mTargetState == STATE_PLAYING) {
                                    start();
//                                    if (mMediaController != null) {
//                                        mMediaController.show();
//                                    }
                                }else if (!isPlaying() &&
                                        (seekToPosition != 0 && getCurrentPosition() > 0)) {

//                                    if (mMediaController != null) {
//                                        mMediaController.show();
//                                    }
                                }
                            } else {
                                start();
                            }
                        }
                    }else {
                        // We don't know the video size yet, but should start anyway.
                        // The video size might be reported to us later.
                        if (mTargetState == STATE_PLAYING) {
                            start();
                        }
                    }
                }
            };

    private IMediaPlayer.OnCompletionListener mCompletionListener =
            new IMediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(IMediaPlayer mp) {
                    mCurrentState = STATE_PLAYBACK_COMPLETED;
                    mTargetState = STATE_PLAYBACK_COMPLETED;

                    IjkSurfacePlayerView.this.onCompletion();
                }
            };
//    private IMediaPlayer.OnSeekCompleteListener mSeekCompletionListener =
//            new IMediaPlayer.OnSeekCompleteListener() {
//                @Override
//                public void onSeekComplete(IMediaPlayer iMediaPlayer) {
//
//                    IjkSurfacePlayerView.this.onSeekCompletion((int) (mMediaPlayer.getCurrentPosition()/1000));
//                }
//
////                @Override
////                public void (IMediaPlayer mp) {
////                    mCurrentState = STATE_PLAYBACK_COMPLETED;
////                    mTargetState = STATE_PLAYBACK_COMPLETED;
////
////                    IjkSurfacePlayerView.this.onCompletion();
////                }
//            };

    private IMediaPlayer.OnInfoListener mInfoListener =
            new IMediaPlayer.OnInfoListener() {

                @Override
                public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                    switch (what) {
                        case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                            Log.d(TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                            break;
                        case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                            //IjkPlayerView.this.onPrepared();
                            Log.d(TAG, "MEDIA_INFO_VIDEO_RENDERING_START:");
                            break;
                        case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                            Log.d(TAG, "MEDIA_INFO_BUFFERING_START:");
                            break;
                        case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                            Log.d(TAG, "MEDIA_INFO_BUFFERING_END:");
                            break;
                        case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                            Log.d(TAG, "MEDIA_INFO_NETWORK_BANDWIDTH: " + extra);
                            break;
                        case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                            Log.d(TAG, "MEDIA_INFO_BAD_INTERLEAVING:");
                            break;
                        case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                            Log.d(TAG, "MEDIA_INFO_NOT_SEEKABLE:");
                            break;
                        case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                            Log.d(TAG, "MEDIA_INFO_METADATA_UPDATE:");
                            break;
                        case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:
                            Log.d(TAG, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                            break;
                        case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:
                            Log.d(TAG, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                            break;
                        case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                            mVideoRotationDegree = extra;
                            Log.d(TAG, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + extra);
                            if (mRenderView != null)
                                mRenderView.setVideoRotation(extra);
                            break;
                        case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START://此处才是真正的开始播放，有声音了
                            Log.d(TAG, "MEDIA_INFO_AUDIO_RENDERING_START:");
                            IjkSurfacePlayerView.this.onPrepared();
                            break;
                    }
                    return true;
                }
            };

    private IMediaPlayer.OnErrorListener mErrorListener =
            new IMediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(IMediaPlayer mp, int framework_err, int impl_err) {
                    Log.e(TAG, "Error: " + framework_err + "," + impl_err);
                    mCurrentState = STATE_ERROR;
                    mTargetState = STATE_ERROR;
                    IjkSurfacePlayerView.this.onError(framework_err, impl_err);
                    return true;
                }
            };

    private IMediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener =
            new IMediaPlayer.OnBufferingUpdateListener() {

                @Override
                public void onBufferingUpdate(IMediaPlayer mp, int percent) {
                    Log.d(TAG, "onBufferingUpdate: percent:" + percent);
                    mCurrentBufferPercentage = percent;
                    IjkSurfacePlayerView.this.onBuffer(percent);
                }
            };

    IMediaPlayer.OnVideoSizeChangedListener mSizeChangedListener =
            new IMediaPlayer.OnVideoSizeChangedListener() {

                @Override
                public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
                    mVideoWidth = mp.getVideoWidth();
                    mVideoHeight = mp.getVideoHeight();
                    mVideoSarNum = mp.getVideoSarNum();
                    mVideoSarDen = mp.getVideoSarDen();
//                    if(mVideoHeight > 0 && ((float)mVideoWidth/(float)mVideoHeight < 1.6)
//                            && mVideoSarNum == mVideoSarDen) {
//                        mVideoSarNum = 4;
//                        mVideoSarDen = 3;
//                    }

                    Log.d(TAG, "[onVideoSizeChanged]1 mVideoWidth:"+mVideoWidth+", mVideoHeight:"+mVideoHeight);

//                    if (mVideoWidth < mVideoHeight) {
//                        mCurrentSize = SURFACE_BEST_FIT;
//                    }
//
//                    //全屏
//                    if (mCurrentSize == SURFACE_FILL) {
//                        int dw = ((Activity) mContext).getWindow().getDecorView().getWidth();
//                        int dh = ((Activity) mContext).getWindow().getDecorView().getHeight();
//                        if (dw < dh) {
//                            int w = dw;
//                            dw = dh;
//                            dh = w;
//                        }
//                        mVideoHeight = mVideoWidth * dh / dw;
//
//                        Log.d(TAG, "[onVideoSizeChanged] dw:"+dw+", dh:"+dh);
//                    }

                    Log.d(TAG, "[onVideoSizeChanged]2 mVideoWidth:"+mVideoWidth+", mVideoHeight:"+mVideoHeight);
                    Log.d(TAG, "[onVideoSizeChanged] mVideoSarNum:"+mVideoSarNum+", mVideoSarDen:"+mVideoSarDen);

                    if (mVideoWidth != 0 && mVideoHeight != 0) {
                        if (mRenderView != null) {
                            mRenderView.setVideoSize(mVideoWidth, mVideoHeight);
                            mRenderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);
                        }

                        requestLayout();
                    }
                }
            };

    public int toggleAspectRatio() {
        mCurrentAspectRatioIndex++;
        mCurrentAspectRatioIndex %= s_allAspectRatio.length;

        mCurrentAspectRatio = s_allAspectRatio[mCurrentAspectRatioIndex];
        if (mRenderView != null)
            mRenderView.setAspectRatio(mCurrentAspectRatio);
        return mCurrentAspectRatio;
    }

    @Override
    public boolean onInfo(int what, int extra) {
        return false;
    }

    public
}
