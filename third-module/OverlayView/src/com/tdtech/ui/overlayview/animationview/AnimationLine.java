/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.animationview;

import java.util.Arrays;
import java.util.LinkedList;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.Log;

import com.tdtech.ui.overlayview.common.AnimatorUpdateCtrl;
import com.tdtech.ui.overlayview.common.CanvasCoordinate;
import com.tdtech.ui.overlayview.common.ListPointAnimator;
import com.tdtech.ui.overlayview.common.ListPointListener;
import com.tdtech.ui.overlayview.common.PointAnimator;
import com.tdtech.ui.overlayview.common.Utils;
import com.tdtech.ui.overlayview.common.ValuePoint;

/**
 * Create Date: 2015-3-25<br>
 * Create Author: cWX239887<br>
 * Description : 带动画效果的折线图
 */
public class AnimationLine extends AnimationView
{

    public static final String TAG = "AnimationLine";
    /** 画线路径 */
    private Path mPaths[] = new Path[0];
    /** 画点用画笔 */
    private Paint mPointPaint = new Paint();
    /** 线条厚度（单位：dp） */
    private float mStrokeWidth;
    /** 圆环厚度（单位：dp） */
    private float mRingWidth;
    /** 绘制点半径（单位：dp） */
    private float mPointRadius;
    /** 需要绘制点的中心坐标列表 */
    private LinkedList<ValuePoint> mValuePoints = new LinkedList<ValuePoint>();
    /** 列表坐标动画生成器 */
    private ListPointAnimator mListPointAnimator = new ListPointAnimator();
    /** 多个坐标点 */
    private double[] mLineValues = new double[0];
    /** 绘制路径效果 */
    private PathEffect mPathEffect = new CornerPathEffect(2);
    /** 动画效果是否已开始 */
    private boolean mIsStarted = true;
    /** 绘制点的最大半径（单位：dp） */
    private float mMaxDpRadius = 4.4f;
    /** 线条监听器 */
    private AnimationLineListener mAnimationLineListener = new AnimationLineListener()
    {

        @Override
        public void onDrawPoint(AnimationLine animationLine, Canvas canvas, CanvasCoordinate coordinate,
                ValuePoint point, int positon, Paint paint, double lineValue)
        {
            Utils.drawRingCircular(canvas, paint, point, mPointRadius, mRingWidth, Color.RED, Color.WHITE);
        }

        @Override
        public boolean isPointEnable(int positon, double lineValue)
        {
            return true;
        }
    };

    public AnimationLine(Context context)
    {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        configDrawParam();

        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mPaintColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.STROKE);
        mPaint.setPathEffect(mPathEffect);
        for (Path path : mPaths)
            canvas.drawPath(path, mPaint);
        
        int pointLength = mValuePoints.size();
        for (int i = 0; i < pointLength; i++)
        {
            ValuePoint point = mValuePoints.get(i);
            mPointPaint.setAntiAlias(true);
            int position = point.getPosition();
            mAnimationLineListener.onDrawPoint(this, canvas, mCoordinate, point, position, mPointPaint,
                    mLineValues[point.getPosition()]);
        }

        if (!mIsStarted)
        {
            mIsStarted = true;
            startAnimation();
        }

        drawStaticProperties(canvas);
    }

    @Override
    public void cancelAnimation()
    {
        mListPointAnimator.cancelAnimation();
        mValuePoints.clear();
        for (Path path : mPaths)
            path.reset();
    }

    public void configDrawParam()
    {
        float stepSize = mCoordinate.getValidCanvasWidth() / mLineValues.length;
        float maxPxRadius = Utils.dp2Px(getContext(), mMaxDpRadius);
        mPointRadius = (stepSize / 6 > maxPxRadius) ? maxPxRadius : stepSize / 6;
        mRingWidth = mPointRadius * 0.392f;
        mStrokeWidth = mPointRadius / 3;
    }

    public void setLinesValues(double[] linesValues)
    {
        cancelAnimation();

        if (linesValues == null || linesValues.length <= 0)
            return;

        mLineValues = Arrays.copyOf(linesValues, linesValues.length);
        //不支持负值展示
        for (int i = 0; i < linesValues.length; i++)
            mLineValues[i] = mLineValues[i] < 0 ? 0 : mLineValues[i];
        
        mIsStarted = false;
        invalidate();
    }

    @Override
    public void startAnimation()
    {
        // 类型转换
        float[] valuesY = new float[mLineValues.length];
        for (int i = 0; i < valuesY.length; i++)
        {
            valuesY[i] = (float) mLineValues[i];
        }

        // 计算所有绘制点的坐标（X,Y）
        float[] valuesX = new float[mLineValues.length];
        float stepSize = calcXStepSize(mLineValues.length);
        valuesX[0] = stepSize / 2;
        for (int i = 1; i < valuesX.length; i++)
        {
            valuesX[i] = stepSize + valuesX[i - 1];
        }

        ValuePoint[] valuePoints = new ValuePoint[mLineValues.length];
        for (int i = 0; i < valuePoints.length; i++)
        {
            float valueX = valuesX[i];
            float valueY = valuesY[i];
            valuePoints[i] = new ValuePoint(valueX, valueY);
        }
        mPaths = new Path[valuePoints.length];
        for (int i = 0; i < valuePoints.length; i++)
            mPaths[i] = new Path();

        mListPointAnimator.setListPointListener(mListPointListener);
        mListPointAnimator.setDuration(mAnimationDuration);
        mListPointAnimator.setValuePoints(valuePoints);
        mListPointAnimator.start(mAnimationLineListener, mLineValues, mAnimationStartDelay);
    }

    private ListPointListener mListPointListener = new ListPointListener()
    {

        AnimatorUpdateCtrl mUpdateCtrl = new AnimatorUpdateCtrl();

        @Override
        public void onAnimationUpdate(int position, Animator animation)
        {
            PointAnimator pointAnimator = (PointAnimator) animation;
            ValuePoint valuePoint = pointAnimator.getCurPoint();
            mUpdateCtrl.setAnimationMinStepSize(mAnimationMinStepSize);
            mUpdateCtrl.setCoordinate(mCoordinate);
            mUpdateCtrl.setEndValue(pointAnimator.getEndPoint().mValueX);

            if (!mUpdateCtrl.needUpdate(pointAnimator))
                return;
            
            Path path = mPaths[position];

            // 计算当前绘制点坐标
            float valueY = valuePoint.mValueY;
            float valueX = valuePoint.mValueX;
            // 坐标系转换（数值坐标系—> 画布坐标系)
            float canvasX = mCoordinate.getCanvasX(valueX);
            float canvasY = mCoordinate.getCanvasY(valueY);

            // 设置画线的起点和路径
            if (path.isEmpty()){
                ValuePoint firstPoint = pointAnimator.getStartPoint();
                path.moveTo(mCoordinate.getCanvasX(firstPoint.mValueX), 
                        mCoordinate.getCanvasY(firstPoint.mValueY));
                path.lineTo(canvasX, canvasY);
            }
            else path.lineTo(canvasX, canvasY);

            // 更新画布
            postInvalidate();
        }

        @Override
        public void onAnimationStart(int position, Animator animation)
        {
            Log.i(TAG, "onAnimationStart position:" + position + ",time:" + System.currentTimeMillis());
        }

        @Override
        public void onAnimationRepeat(int position, Animator animation)
        {
        }

        @Override
        public void onAnimationEnd(int position, Animator animation)
        {
            Log.i(TAG, "onAnimationEnd position:" + position + ",time:" + System.currentTimeMillis());
            PointAnimator pointAnimator = (PointAnimator) animation;
            ValuePoint valuePoint = pointAnimator.getCurPoint();
            // 计算当前绘制点坐标
            float valueY = valuePoint.mValueY;
            float valueX = valuePoint.mValueX;
            // 坐标系转换（数值坐标系—> 画布坐标系)
            float canvasX = mCoordinate.getCanvasX(valueX);
            float canvasY = mCoordinate.getCanvasY(valueY);
            // 设置绘制点
            valuePoint.setCanvasX(canvasX);
            valuePoint.setCanvasY(canvasY);
            valuePoint.setPosition(position);
            mValuePoints.add(valuePoint);
        }

        @Override
        public void onAnimationCancel(int position, Animator animation)
        {
        }
    };

    public double[] getLineValues()
    {
        return Arrays.copyOf(mLineValues, mLineValues.length);
    }

    public float getStrokeWidth()
    {
        return mStrokeWidth;
    }

    public void setStrokeWidth(float strokeWidth)
    {
        mStrokeWidth = strokeWidth;
    }

    public AnimationLineListener getAnimationLineListener()
    {
        return mAnimationLineListener;
    }

    public void setAnimationLineListener(AnimationLineListener animationLineListener)
    {
        if (mAnimationLineListener == null)
            return;
        mAnimationLineListener = animationLineListener;
    }

    public Paint getPointPaint()
    {
        return mPointPaint;
    }

    public float getRingWidth()
    {
        return mRingWidth;
    }

    public float getPointRadius()
    {
        return mPointRadius;
    }

    public void setPointPaint(Paint pointPaint)
    {
        mPointPaint = pointPaint;
    }

    public void setRingWidth(float ringWidth)
    {
        mRingWidth = ringWidth;
    }

    public void setPointRadius(int pointRadius)
    {
        mPointRadius = pointRadius;
    }
}
