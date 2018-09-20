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
 * Description : 堆积式柱状图数据构造器
 */
public class AnimationBarsDataBuilder
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
    double[][] mBarsValues;
    
    static final LinkedList<AnimationBarsDataBuilder> BARS_DATAS = new LinkedList<AnimationBarsDataBuilder>();
    
    static
    {
        RectF rectF = null;
        int arraySize = 0;
        double maxYValue = 0;
        long duration = 0;
        long startDelay = 0;
        double[][] barsValues = null;
        AnimationBarListener listener = null;
        int maxTestSize = Constant.maxTestCaseSize();
        AnimationBarsDataBuilder builder = null;
        for (int i = 0; i < maxTestSize + 1; i++)
        {
            arraySize = Constant.nextArraySize(i);
            barsValues = Constant.nextArrayDoubles(arraySize, i);
            rectF = Constant.nextRectPadding(i);
            listener = Constant.nextBarListener(i);
            maxYValue = maxValue(barsValues);
            duration = Constant.nextAnimDuration(i);
            startDelay = Constant.nextStartDelay(i);
            builder = new AnimationBarsDataBuilder(rectF, maxYValue, duration, 
                    startDelay, listener, barsValues);
            BARS_DATAS.add(builder);
        }
    }

    private static double maxValue(double[][] values)
    {
        double[] fixedValues = new double[values.length];
        for (int i = 0; i < fixedValues.length; i++)
        {
            double sum = 0;
            for (double d : values[i])
                sum += d;
            fixedValues[i] = sum;
        }
        
        return Utils.maxValue(fixedValues);
        
    }
    
    public AnimationBarsDataBuilder(RectF mPaddingRect, double mMaxYValue, long mAnimDuration, long mAnimStartDelay,
            AnimationBarListener listener, double[][] mBarsValues)
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
