/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.common;

import android.animation.ValueAnimator;

/**
 * 
 * Create Date: 2015-4-28<br>
 * Create Author: cWX239887<br>
 * Description : 动画更新控制器，目的避免更新动画过快
 */
public class AnimatorUpdateCtrl
{

    /** 上一次动画数值 */
    Object mLastValue;
    /** 动画结束数值 */
    private float mEndValue;
    /** 最小的动画步进 */
    private int mAnimationMinStepSize;
    /** 坐标系转换对象 */
    private CanvasCoordinate mCoordinate;

    public AnimatorUpdateCtrl()
    {
        super();
    }

    public AnimatorUpdateCtrl(float endValue, int animationMinStepSize, CanvasCoordinate coordinate)
    {
        super();
        mEndValue = endValue;
        mAnimationMinStepSize = animationMinStepSize;
        mCoordinate = coordinate;
    }

    public boolean needUpdate(ValueAnimator animation)
    {
        if (mLastValue == null)
        {
            mLastValue = animation.getAnimatedValue();
            return true;
        }

        // 计算最大步进
        float canvasMaxSize = Math.abs((mEndValue - (Float) mLastValue)) * mCoordinate.getCanvasAndValueRatio();
        // 计算最小动画步进
        float minCanvasStepSize = canvasMaxSize < mAnimationMinStepSize ? canvasMaxSize : mAnimationMinStepSize;
        // 计算当前动画步进
        float canvasSize = Math.abs(((Float) animation.getAnimatedValue() - (Float) mLastValue))
                * mCoordinate.getCanvasAndValueRatio();
        // 如果当前动画步进未达到动画更新要求的步进则禁止更新画布
        if (canvasSize < minCanvasStepSize)
            return false;
        // 更新上一次动画的位置
        mLastValue = animation.getAnimatedValue();
        return true;
    }

    public float getEndValue()
    {
        return mEndValue;
    }

    public void setEndValue(float endValue)
    {
        mEndValue = endValue;
    }

    public int getAnimationMinStepSize()
    {
        return mAnimationMinStepSize;
    }

    public void setAnimationMinStepSize(int animationMinStepSize)
    {
        mAnimationMinStepSize = animationMinStepSize;
    }

    public CanvasCoordinate getCoordinate()
    {
        return mCoordinate;
    }

    public void setCoordinate(CanvasCoordinate coordinate)
    {
        mCoordinate = coordinate;
    }

}
