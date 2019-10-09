package kt.module.module_main.mvp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import kt.module.module_base.adapter.BaseRvQuickAdapter;
import kt.module.module_base.adapter.BaseRvViewHolder;
import kt.module.module_base.base.view.BaseActivity;
import kt.module.module_base.data.db.table.RvData;
import kt.module.module_base.utils.DataUtils;
import kt.module.module_main.R;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

public class SplashActivity extends BaseActivity {

    RecyclerView mRecyclerView;
    BaseRvQuickAdapter mAdapter;
    List<RvData> data;
    boolean isTouch = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mRecyclerView = findViewById(R.id.recyclerView);

        data = DataUtils.INSTANCE.createData(6);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new BaseRvQuickAdapter<RvData>(R.layout.item_home_layout, data) {
            @Override
            protected void convert(@Nullable BaseRvViewHolder helper, RvData item) {
                helper.setSimpleDraweeViewUrl(R.id.item_home_simpleDraweeView, item.getUrl());
                helper.setText(R.id.item_home_nameTv, item.getName());
                helper.setText(R.id.item_home_ageTv, item.getAge() + "");
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        addHeader();

        addFooter();

        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels;//可用显示大小的绝对高度（以像素为单位）。

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isTouch = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        int[] location = new int[2];
                        footerView.getLocationOnScreen(location);

                        int visibleHeight = (heightPixels - location[1]) * 2;
                        int footerHeight = footerView.getMeasuredHeight();
                        if (visibleHeight > footerHeight && location[1] > 0) {
                            mRecyclerView.smoothScrollBy(0, footerView.getMeasuredHeight() - (heightPixels - location[1]), new FastOutLinearInInterpolator());
                            isTouch = true;
                        } else {
                            if (location[1] > 0) {
                                mRecyclerView.smoothScrollBy(0, -(heightPixels - location[1]), new FastOutLinearInInterpolator());
                                isTouch = true;
                            } else {
                                isTouch = false;
                            }
                        }
                        break;
                    default:
                        break;
                }
                return isTouch;
            }
        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                SCROLL_STATE_IDLE   SCROLL_STATE_DRAGGING  SCROLL_STATE_SETTLING
                int[] location = new int[2];
                footerView.getLocationOnScreen(location);
                if (newState == SCROLL_STATE_IDLE && location[1] > 0 && !isTouch) {
//                    mRecyclerView.smoothScrollBy(0, -(heightPixels - location[1]), new LinearInterpolator());
                    mRecyclerView.smoothScrollBy(0, -(heightPixels - location[1]), new FastOutLinearInInterpolator());
                }
            }
        });
    }

    View headerFirstView;

    View headerSecondView;
    RecyclerView mSecondRecyclerView;
    BaseRvQuickAdapter mSecondAdapter;
    private void addHeader() {
        headerFirstView = LayoutInflater.from(SplashActivity.this).inflate(R.layout.header_first_layout, null, false);

        headerSecondView = LayoutInflater.from(SplashActivity.this).inflate(R.layout.header_second_layout, null, false);
        mAdapter.addHeaderView(headerFirstView);
        mAdapter.addHeaderView(headerSecondView);

        mSecondRecyclerView = headerSecondView.findViewById(R.id.header_second_recyclerView);

        mSecondAdapter = new BaseRvQuickAdapter<RvData>(R.layout.item_header_second_layout, data) {
            @Override
            protected void convert(@Nullable BaseRvViewHolder helper, RvData item) {
                if (data.size() >= 12){
                    if (helper.getAdapterPosition() < 11){
                        helper.setGone(R.id.item_header_tv, true);
                        helper.setDrawableBottom(R.id.item_header_tv, getResources().getDrawable(R.mipmap.icon_tab_home_select));
                        helper.setText(R.id.item_header_tv, item.getName());
                    }else if (helper.getAdapterPosition() == 11){
                        helper.setGone(R.id.item_header_tv, true);
                        helper.setText(R.id.item_header_tv, "更多");
                    }else {
                        helper.setGone(R.id.item_header_tv, false);
                    }
                }else {
                    helper.setGone(R.id.item_header_tv, false);
                    helper.setDrawableBottom(R.id.item_header_tv, getResources().getDrawable(R.mipmap.icon_tab_home_select));
                    helper.setText(R.id.item_header_tv, item.getName());
                }
            }
        };
        mSecondRecyclerView.setLayoutManager(new GridLayoutManager(SplashActivity.this, 6));
        mSecondRecyclerView.setAdapter(mSecondAdapter);

    }

    View footerView;
    private void addFooter() {
        footerView = LayoutInflater.from(SplashActivity.this).inflate(R.layout.footer_home_layout, null, false);
        mAdapter.addFooterView(footerView);
    }
}