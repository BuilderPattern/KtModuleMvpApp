package kt.module.module_base.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import kt.module.module_base.R;

public class ConfirmDialog extends Dialog {

    Context context;

    public ConfirmDialog(Context context) {
        super(context, R.style.Theme_AppCompat_Dialog);//style可以自行选择或者重写
        this.context = context;
    }

    TextView titleTv, contentTv;

    private TextView cancelTv, confirmTv;

    private String titleStr, contentStr;
    String confirmStr = "确定";
    String cancelStr = "取消";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm_dialog);
        initViews();
        initEvents();
    }

    private void initViews() {
        titleTv = findViewById(R.id.confirm_dialog_titleTv);
        contentTv = findViewById(R.id.confirm_dialog_content);

        cancelTv = findViewById(R.id.confirm_dialog_cancel);
        confirmTv = findViewById(R.id.confirm_dialog_confirm);

        if (!TextUtils.isEmpty(titleStr)) {
            titleTv.setText(titleStr);
        }
        if (!TextUtils.isEmpty(contentStr)) {
            contentTv.setText(contentStr);
        }
        if (!TextUtils.isEmpty(cancelStr)) {
            cancelTv.setText(cancelStr);
        }
        if (!TextUtils.isEmpty(confirmStr)) {
            confirmTv.setText(confirmStr);
        }
    }

    public void setTitle(String title) {
        titleStr = title;
    }

    public void setContent(String content) {
        contentStr = content;
    }

    private void initEvents() {
        /**
         * 分别给布局中的cancel和confirm控件设置监听
         */

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancelClickListener != null) {
                    onCancelClickListener.cancelClickListener();
                    ConfirmDialog.this.dismiss();//确认的时候，让对话框消失
                }
            }
        });

        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onConfirmClickListener != null) {
                    onConfirmClickListener.confirmClickListener();
                    ConfirmDialog.this.dismiss();//取消的时候，让对话框消失
                }
            }
        });
    }

    private OnCancelClickListener onCancelClickListener;

    public void setOnCancelClickListener(OnCancelClickListener cancelClickListener) {
        this.onCancelClickListener = cancelClickListener;
    }

    OnConfirmClickListener onConfirmClickListener;
    public void setOnConfirmClickListener(OnConfirmClickListener confirmClickListener) {
        this.onConfirmClickListener = confirmClickListener;
    }

    /**
     * 提供设置cancel内容，和设置cancel监听
     *
     * @param cancel
     * @param cancelClickListener
     */
    public void setOnCancelClickListener(String cancel, OnCancelClickListener cancelClickListener) {
        if (!TextUtils.isEmpty(cancel)) {
            cancelStr = cancel;
        }
        this.onCancelClickListener = cancelClickListener;
    }

    /**
     * 提供设置confirm内容，和设置confirm监听
     *
     * @param confirm
     * @param confirmClickListener
     */

    public void setOnConfirmClickListener(String confirm, OnConfirmClickListener confirmClickListener) {
        if (!TextUtils.isEmpty(confirm)) {
            confirmStr = confirm;
        }
        this.onConfirmClickListener = confirmClickListener;
    }

    /**
     * 自定义的取消监听接口
     */
    public interface OnCancelClickListener {
        void cancelClickListener();
    }

    /**
     * 自定义的确认监听接口
     */
    public interface OnConfirmClickListener {
        void confirmClickListener();
    }
}
