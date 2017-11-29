package com.knight.jone.mySuperDemo.dialogTutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.knight.jone.mySuperDemo.R;

public class CommonDialog extends BaseDialog implements View.OnClickListener {

    private ViewHolder           mViewHolder;
    private CommonDialogListener mCommonDialogListener;

    private class ViewHolder {
        TextView tv_ok, tv_cancel;
        TextView tv_content, tv_title;
    }

    public abstract class CommonDialogListener {
        public void onOkClick(View view) {
        }

        public void onCancelClick(View view) {
        }
    }

    public CommonDialog(Context context, CommonDialogListener listener) {
        super(context);
        this.mCommonDialogListener = listener;
        initDialog(context);
    }

    public CommonDialog(Context context, String contentStr, String sureStr) {
        super(context);

        mViewHolder.tv_ok.setText(sureStr);
        mViewHolder.tv_content.setText(contentStr);
        initDialog(context);
    }

    private void initDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);
        addView(view);
        mViewHolder = new ViewHolder();
        mViewHolder.tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        mViewHolder.tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        mViewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
        mViewHolder.tv_content = (TextView) view.findViewById(R.id.tv_content);


        mViewHolder.tv_ok.setOnClickListener(this);
        mViewHolder.tv_cancel.setOnClickListener(this);
    }

    public void setTitle(String title) {
        mViewHolder.tv_title.setText(title);
    }

    public void onClick(View view) {
        if (mCommonDialogListener != null) {
            if (view == mViewHolder.tv_ok) {
                mCommonDialogListener.onOkClick(view);
            } else {
                mCommonDialogListener.onCancelClick(view);
            }
        } else {
            if (view == mViewHolder.tv_ok) {
                if (listener != null) {
                    listener.onClick(view);//执行宿主onClickListener
                }
            }
            dismiss();
        }
    }
}

