package com.knight.jone.mySuperDemo.dialogTutorial;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.knight.jone.mySuperDemo.R;

/**
 * 1.通过new创建dialog，并且设置style
 * 2.Dialog占据面积为屏幕宽度的0.9
 */
public class BaseDialog {

    protected Dialog               dialog;
    protected View.OnClickListener listener;
    protected Context              context;

    public BaseDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.MyDialog);
    }

    public void addView(View dialogView) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (dm.widthPixels * 0.9f), ViewGroup
                .LayoutParams.WRAP_CONTENT);
        dialog.addContentView(dialogView, params);
    }

    public void showDialog() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
