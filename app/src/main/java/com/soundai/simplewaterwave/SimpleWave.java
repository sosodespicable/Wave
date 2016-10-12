package com.soundai.simplewaterwave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fez on 2016/10/11.
 */

public class SimpleWave extends View {

    private static final int WAVE_PAINT_COLOR = 0x880000aa;
    private static final float A = 60;
    private static final float offset_y = 100;

    //两条水波纹的移动速度
    private static final int TRANSLATE_X_SPEED_ONE = 7;
    private static final int TRANSLATE_X_SPEED_TWO =5;
    //三角函数公式中的角速度
    private float w;

    private int mTotalWidth,mTotalHeight;
    private float[] mYPositions;
    private float[] mResetOneYPosition;
    private float[] mResetTwoYPosition;
    private int mXOffsetSpeedOne;
    private int mXOffsetSpeedTwo;
    private int mXOneOffset;
    private int mXTwoOffset;

    private Paint mWavePaint;
    private DrawFilter mDrawFilter;

    public SimpleWave(Context context) {
        super(context);
    }

    public SimpleWave(Context context, AttributeSet attrs) {
        super(context, attrs);
        mXOffsetSpeedOne = Utils.dip2px(context,TRANSLATE_X_SPEED_ONE);
        mXOffsetSpeedTwo = Utils.dip2px(context,TRANSLATE_X_SPEED_TWO);
        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setColor(WAVE_PAINT_COLOR);
        mDrawFilter = new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    public SimpleWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);
        resetPositionY();
        for (int i = 0;i < mTotalWidth;i++){
            //第一条水波纹
            canvas.drawLine(i,mTotalHeight - mResetOneYPosition[i] - 400,i,mTotalHeight,mWavePaint);
//            canvas.drawPoint(i,mTotalHeight - mResetOneYPosition[i] - 400,mWavePaint);
            //第二条水波纹
            canvas.drawLine(i,mTotalHeight - mResetTwoYPosition[i] - 400,i,mTotalHeight,mWavePaint);
//            canvas.drawPoint(i,mTotalHeight - mResetTwoYPosition[i] - 400,mWavePaint);
        }
        mXOneOffset = mXOffsetSpeedOne + mXOneOffset;
        mXTwoOffset = mXTwoOffset + mXOffsetSpeedTwo;
        if (mXOneOffset >= mTotalWidth){
            mXOneOffset = 0;
        }
        if (mXTwoOffset >= mTotalWidth){
            mXTwoOffset = 0;
        }
        //引发View的重新绘制
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mYPositions = new float[mTotalWidth];
        mResetOneYPosition = new float[mTotalWidth];
        mResetTwoYPosition = new float[mTotalWidth];
        this.w = (float) (2 * Math.PI/mTotalWidth);
        for (int i = 0;i < mTotalWidth;i++){
            mYPositions[i] = (float) (A * Math.sin((this.w * i) + offset_y));
        }
    }

    private void resetPositionY(){
        int yOneInterval = mYPositions.length - mXOneOffset;
        System.arraycopy(mYPositions,mXOneOffset,mResetOneYPosition,0,yOneInterval);
        System.arraycopy(mYPositions,0,mResetOneYPosition,yOneInterval,mXOneOffset);

        int yTwoInterval = mYPositions.length - mXTwoOffset;
        System.arraycopy(mYPositions,mXTwoOffset,mResetTwoYPosition,0,yTwoInterval);
        System.arraycopy(mYPositions,0,mResetTwoYPosition,yTwoInterval,mXTwoOffset);
    }
}
