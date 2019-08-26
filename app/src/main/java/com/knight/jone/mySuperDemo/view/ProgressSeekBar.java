package com.knight.jone.mySuperDemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

import androidx.appcompat.widget.AppCompatSeekBar;

import com.knight.jone.mySuperDemo.R;
import com.knight.jone.mySuperDemo.utils.Lg;

import java.text.DecimalFormat;

/**
 * 带有数字的水平拖动条
 */
public class ProgressSeekBar extends AppCompatSeekBar {
    private static DecimalFormat mDf = new DecimalFormat("#.0");
    //最大电量16 -9
    private static final double MAX_POWER_VALUE = 7;
    //最小电量9
    private static final double MIN_POWER_VALUE = 9;
    //报警最低 10-11.5
    private static final double MIN_VALUE = 10;
    // 9-11.5
    private static final double MAX_POWER = 1.5;

    private int oldPaddingTop;

    private int oldPaddingLeft;

    private int oldPaddingRight;

    private int oldPaddingBottom;

    private boolean isMysetPadding = true;

    private String mText;

    private float mTextWidth;

    private float mImgWidth;

    private float mImgHei;

    private Paint mPaint;

    private Resources res;

    private Bitmap bm;

    private int textsize = 30;
    private int textpaddingleft;
    private int textpaddingtop;
    private int imagepaddingleft;
    private int imagepaddingtop;
    private boolean ishide;

    public ProgressSeekBar(Context context) {
        super(context);
        init();
    }

    public ProgressSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    // 屏蔽滑动
    // @Override
    // public boolean onTouchEvent(MotionEvent event) {
    // return false;
    // }

    ///**
    // * (非 Javadoc)
    // *
    // * @param event
    // * @return
    // * @方法名: onTouchEvent
    // * @描述: 不屏蔽屏蔽滑动
    // * @日期: 2014-8-11 下午2:03:15
    // * @see android.widget.AbsSeekBar#onTouchEvent(MotionEvent)
    // */
    //@Override
    //public boolean onTouchEvent(MotionEvent event) {
    //    return super.onTouchEvent(event);
    //}

    /**
     *     修改setpadding 使其在外部调用的时候无效
     */
    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (isMysetPadding) {
            super.setPadding(left, top, right, bottom);
        }
    }

    // 初始化
    private void init() {
        res = getResources();
        initBitmap();
        initDraw();
        setPadding();
        setOnSeekBarChangeListener(new onSeekBarChangeListener());
    }

    private void initDraw() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setTextSize(textsize);
        mPaint.setColor(0xff000000);
    }

    private void initBitmap() {
        bm = BitmapFactory.decodeResource(res, R.drawable.lucency_icon);
        if (bm != null) {
            mImgWidth = bm.getWidth();
            mImgHei = bm.getHeight();
        } else {
            mImgWidth = 0;
            mImgHei = 0;
        }
    }

    private class onSeekBarChangeListener implements OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
            setIshide(true);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
            setIshide(false);
        }

    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {

        try {
            super.onDraw(canvas);
            if (ishide == true) {
                mText = getAlarmValue() + "V";
                mTextWidth = mPaint.measureText(mText);
                Rect bounds = this.getProgressDrawable().getBounds();
                float xImg = bounds.width() * getProgress() / getMax()
                        + imagepaddingleft + oldPaddingLeft;
                float yImg = imagepaddingtop + oldPaddingTop;

                float xText = bounds.width() * getProgress() / getMax()
                        + mImgWidth / 2 - mTextWidth / 2 + textpaddingleft
                        + oldPaddingLeft;
                float yText = yImg + textpaddingtop + mImgHei / 2
                        + getTextHei() / 4;
                canvas.drawBitmap(bm, xImg, yImg, mPaint);
                canvas.drawText(mText, xText, yText, mPaint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //10-11.5
    public String getAlarmValue() {
        return mDf.format((getProgress() * 1.0 / getMax()) * (MAX_POWER) + MIN_VALUE);
    }

    //百分比转电量
    public static String getValue(double progress, int maxProgress) {
        return mDf.format((progress * 1.0 / maxProgress) * MAX_POWER_VALUE + MIN_POWER_VALUE);
    }

    //public static void main(String[] args) {
    //    String lowAlarmPercent = getLowAlarmPercent(114 / 10.0);
    //    System.out.println("----" + lowAlarmPercent + "----");
    //}

    public static int getLowAlarmPercent(double value) {
        double percent = ((value - MIN_VALUE) * 1.0) / MAX_POWER;
        Lg.d(value + "  当前电量百分比：" + Math.round(percent * 100));
        return (int) Math.round(percent * 100);
    }

    // 初始化padding 使其左右上 留下位置用于展示进度图片
    private void setPadding() {
        int top = getBitmapHeigh() + oldPaddingTop;
        int left = getBitmapWidth() / 2 + oldPaddingLeft;
        int right = getBitmapWidth() / 2 + oldPaddingRight;
        int bottom = oldPaddingBottom;
        isMysetPadding = true;
        setPadding(left, top, right, bottom);
        isMysetPadding = false;
    }

    /**
     * 设置展示进度背景图片
     *
     * @param resid
     */
    public void setBitmap(int resid) {
        bm = BitmapFactory.decodeResource(res, resid);
        if (bm != null) {
            mImgWidth = bm.getWidth();
            mImgHei = bm.getHeight();
        } else {
            mImgWidth = 0;
            mImgHei = 0;
        }
        setPadding();
    }

    /**
     * 替代setpadding
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setMyPadding(int left, int top, int right, int bottom) {
        oldPaddingTop = top;
        oldPaddingLeft = left;
        oldPaddingRight = right;
        oldPaddingBottom = bottom;
        isMysetPadding = true;
        setPadding(left + getBitmapWidth() / 2, top + getBitmapHeigh(), right
                + getBitmapWidth() / 2, bottom);
        isMysetPadding = false;
    }

    /**
     * 设置进度字体大小
     *
     * @param textsize
     */
    public void setTextSize(int textsize) {
        this.textsize = textsize;
        mPaint.setTextSize(textsize);
    }

    /**
     * 设置进度字体颜色
     *
     * @param color
     */
    public void setTextColor(int color) {
        mPaint.setColor(color);
    }

    /**
     * 调整进度字体的位置 初始位置为图片的正中央
     *
     * @param top
     * @param left
     */
    public void setTextPadding(int top, int left) {
        this.textpaddingleft = left;
        this.textpaddingtop = top;
    }

    /**
     * 调整进图背景图的位置 初始位置为进度条正上方、偏左一半
     *
     * @param top
     * @param left
     */
    public void setImagePadding(int top, int left) {
        this.imagepaddingleft = left;
        this.imagepaddingtop = top;
    }

    private int getBitmapWidth() {
        return (int) Math.ceil(mImgWidth);
    }

    private int getBitmapHeigh() {
        return (int) Math.ceil(mImgHei);
    }

    private float getTextHei() {
        FontMetrics fm = mPaint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.top) + 2;
    }

    public int getTextpaddingleft() {
        return textpaddingleft;
    }

    public int getTextpaddingtop() {
        return textpaddingtop;
    }

    public int getImagepaddingleft() {
        return imagepaddingleft;
    }

    public int getImagepaddingtop() {
        return imagepaddingtop;
    }

    public int getTextsize() {
        return textsize;
    }

    public void setIshide(boolean ishide) {
        this.ishide = ishide;
    }

    public boolean isIshide() {
        return ishide;
    }

}
