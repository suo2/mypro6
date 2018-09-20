/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui;

import java.util.Arrays;
import java.util.LinkedList;

import android.graphics.RectF;

import com.tdtech.ui.overlayview.animationview.AnimationBarListener;
import com.tdtech.ui.overlayview.common.Utils;


/**
 * Create Date: 2015-4-23<br>
 * Create Author: cWX239887<br>
 * Description : 柱状图数据构造器
 */
public class AnimationBarDataBuilder
{
    /** 画布 padding */
    RectF mPaddingRect;
    /** Y坐标最大值 */
    double mMaxYValue;
    /** 动画持续时间 */
    long mAnimDuration;
    /** 动画持续时间 */
    long mAnimStartDelay;
    /** 柱状图监听器  */
    AnimationBarListener listener;
    /** 柱状图数据  */
    double[] mBarsValues;
    
    static final LinkedList<AnimationBarDataBuilder> BAR_DATAS = new LinkedList<AnimationBarDataBuilder>();
    
    static
    {
        RectF rectF = null;
        int arraySize = 0;
        double maxYValue = 0;
        long duration = 0;
        long startDelay = 0;
        double[] barsValues = null;
        AnimationBarListener listener = null;
        int maxTestSize = Constant.maxTestCaseSize();
        AnimationBarDataBuilder builder = null;
        for (int i = 0; i < maxTestSize + 1; i++)
        {
            arraySize = Constant.nextArraySize(i);
            barsValues = Constant.nextDoubleValues(arraySize, i);
            rectF = Constant.nextRectPadding(i);
            listener = Constant.nextBarListener(i);
            maxYValue = Utils.maxValue(barsValues);
            duration = Constant.nextAnimDuration(i);
            startDelay = Constant.nextStartDelay(i);
            builder = new AnimationBarDataBuilder(rectF, maxYValue, duration, 
                    startDelay, listener, barsValues);
            BAR_DATAS.add(builder);
        }
    }

    public AnimationBarDataBuilder(RectF mPaddingRect, double mMaxYValue, long mAnimDuration, long mAnimStartDelay,
            AnimationBarListener listener, double[] mBarsValues)
    {
        super();
        this.mPaddingRect = mPaddingRect;
        this.mMaxYValue = mMaxYValue;
        this.mAnimDuration = mAnimDuration;
        this.mAnimStartDelay = mAnimStartDelay;
        this.listener = listener;
        this.mBarsValues = mBarsValues;
    }

    @Override
    public String toString()
    {
        return "AnimationBarDataBuilder [mPaddingRect=" + mPaddingRect + ", mMaxYValue=" + mMaxYValue
                + ", mAnimDuration=" + mAnimDuration + ", mAnimStartDelay=" + mAnimStartDelay + ", listener="
                + listener + ", mBarsValues=" + Arrays.toString(mBarsValues) + "]";
    }
    
}
