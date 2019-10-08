package kt.module.module_main.mvp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
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

public class SplashActivity extends BaseActivity {

    RecyclerView mRecyclerView;

    BaseRvQuickAdapter mAdapter;

    List<RvData> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mRecyclerView = findViewById(R.id.recyclerView);

        data = DataUtils.INSTANCE.createData(5);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(SplashActivity.this));

        mAdapter = new BaseRvQuickAdapter<RvData>(R.layout.item_home_layout, data){

            @Override
            protected void convert(@Nullable BaseRvViewHolder helper, RvData item) {
                helper.setSimpleDraweeViewUrl(R.id.item_home_simpleDraweeView, item.getUrl());
                helper.setText(R.id.item_home_nameTv, item.getName());
                helper.setText(R.id.item_home_ageTv, item.getAge()+"");
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        addFooter();

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

    }

    private void addFooter() {
        View view = LayoutInflater.from(SplashActivity.this).inflate(R.layout.footer_home_layout, null, false);
        mAdapter.addFooterView(view);
    }

}
