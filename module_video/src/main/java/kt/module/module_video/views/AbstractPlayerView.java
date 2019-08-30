package kt.module.module_video.views;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.Map;

public abstract class AbstractPlayerView extends RelativeLayout implements ThinkoPlayerListener {

    protected Context mContext;
    // 监听器
    private ThinkoPlayerListener mListener;                    //PlayView 传入的listener
    private OnPreparedListener mPreparedListener;              //以下是ThinkoPlayerView 传入的listener
    private OnCompletionListener mCompletionListener;
    private OnErrorListener mOnErrorListener;

    public abstract void initPlayer();
    public abstract void initPlayer(String libraryPath);
    public abstract void play(String url);
    public abstract void play(Uri uri);
    public abstract void play(Uri uri, Map<String, String> headers);
    public abstract void setVolume(float a,float b);
    public abstract void setPlayerSize(float pivotX, float pivotY, float scaleX, float scaleY);
    public abstract void start();
    public abstract void seekTo(int pos);
    public abstract long getCurrentPosition();
    public abstract long getDuration();
    public abstract void pause();
    public abstract void stop();
    public abstract void reset();
    public abstract boolean isPlaying();
    public abstract int getBufferPercentage();
    public abstract void removeSurface();
    public abstract void release();
    public abstract void setUserAgent(String userAgent);
    public abstract void useMediaCodec(boolean use);
    //前一次播放位置
    public abstract void setLastPos(int lastPos);

    public AbstractPlayerView(Context context) {
        this(context, null);
    }
    public AbstractPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }
    public AbstractPlayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    public void onPrepared() {
        if (mPreparedListener != null){
            mPreparedListener.onPrepared();
        }
        if (mListener != null){
            mListener.onPrepared();
        }
    }

    @Override
    public void onBuffer(int percent) {
        if (mListener != null){
            mListener.onBuffer(percent);
        }
    }

    @Override
    public void onCompletion() {
    	if (mCompletionListener != null){
    		mCompletionListener.onCompletion();
    	}
        if (mListener != null){
            mListener.onCompletion();
        }
    }

    @Override
    public boolean onError(int what, int extra) {
    	if (mOnErrorListener != null){
    		mOnErrorListener.onError(what, extra);
    	}
        if (mListener != null){
            return mListener.onError(what, extra);
        }
        return false;
    }

    @Override
    public void onNetworkSpeedUpdate(int speed) {
        if (mListener != null){
            mListener.onNetworkSpeedUpdate(speed);
        }
    }
    
    /**
     *  设置监听器
     * @param listener
     */
    public void setPlayerListener(ThinkoPlayerListener listener){
        mListener = listener;
    }
    

    public interface OnPreparedListener {
        public void onPrepared();
    }

    public void setOnPreparedListener(OnPreparedListener listener){
        mPreparedListener = listener;
    }
    
    public interface OnCompletionListener {
    	public void onCompletion();
    }
    
    public void setOnCompletionListener(OnCompletionListener listener){
    	mCompletionListener = listener;
    }
    
    public interface OnErrorListener {
    	public void onError(int what, int extra);
    }
    
    public void setOnErrorListener(OnErrorListener listener){
    	mOnErrorListener = listener;
    }

    public interface OnInfoListener {
        public void onInfo(int what, int extra);
    }
}