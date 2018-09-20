/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.common;

import android.animation.Animator;
import android.animation.ValueAnimator;

/**
 * Create Date: 2015-3-25<br>
 * Create Author: cWX239887<br>
 * Description : 二维坐标动画类，基于二维坐标的插值动画类
 */
public class PointAnimator extends ValueAnimator
{
    public static final String TAG = "PointAnimator";
    /** 起点坐标 */
    private ValuePoint mStartPoint;
    /** 终点坐标 */
    private ValuePoint mEndPoint;
    /** 下一次动画坐标 */
    private PointAnimator mNextAnimator;

    public PointAnimator()
    {
        super();
    }

    public void setPointValues(ValuePoint startPoint, ValuePoint endPoint)
    {
        mStartPoint = startPoint;
        mEndPoint = endPoint;
        //设置起点、终点数值
        setFloatValues(mStartPoint.mValueX, mEndPoint.mValueX);
        //添加动画监听器
        addListener(mAnimatorListener);
    }

    /** 获取当前时间点坐标（ 根据起点和终点，设计线段方程式，然后根据方程式求出Y轴的值）*/
    public ValuePoint getCurPoint()
    {
        double x1 = mStartPoint.mValueX;
        double x2 = mEndPoint.mValueX;
        double y1 = mStartPoint.mValueY;
        double y2 = mEndPoint.mValueY;
        double x = (Float) getAnimatedValue();
        double y = (x2 - x1 == 0) ? y2 : ((y2 - y1) / (x2 - x1) * (x - x1) + y1);
        // 生成坐标点对象
        ValuePoint valuePoint = new ValuePoint((float) x, (float) y);
        return valuePoint;
    }

    public PointAnimator getNextAnimator()
    {
        return mNextAnimator;
    }

    public void setNextAnimator(PointAnimator nextAnimator)
    {
        mNextAnimator = nextAnimator;
    }

    public ValuePoint getStartPoint()
    {
        return mStartPoint;
    }

    public void setStartPoint(ValuePoint startPoint)
    {
        mStartPoint = startPoint;
    }

    public ValuePoint getEndPoint()
    {
        return mEndPoint;
    }

    public void setEndPoint(ValuePoint endPoint)
    {
        mEndPoint = endPoint;
    }

    public AnimatorListener getAnimatorListener()
    {
        return mAnimatorListener;
    }

    public void setAnimatorListener(AnimatorListener animatorListener)
    {
        mAnimatorListener = animatorListener;
    }

    private AnimatorListener mAnimatorListener = new AnimatorListener()
    {

        @Override
        public void onAnimationCancel(Animator animation)
        {
        }

        @Override
        public void onAnimationEnd(Animator animation)
        {
            if (mNextAnimator == null)
                return;
            if(mNextAnimator.isStarted())
                return;
            
            mNextAnimator.start();
        }

        @Override
        public void onAnimationRepeat(Animator animation)
        {
        }

        @Override
        public void onAnimationStart(Animator animation)
        {
        }

    };

    @Override
    public String toString()
    {
        return "PointAnimator [mStartPoint=" + mStartPoint + ", mEndPoint=" + mEndPoint + "]";
    }
}
