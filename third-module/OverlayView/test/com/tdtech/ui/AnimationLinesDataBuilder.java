/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui;

import java.util.Arrays;
import java.util.LinkedList;

import android.graphics.RectF;

import com.tdtech.ui.overlayview.animationview.AnimationLineListener;
import com.tdtech.ui.overlayview.common.Utils;


/**
 * Create Date: 2015-4-23<br>
 * Create Author: cWX239887<br>
 * Description : 折线图数据构造器
 */
public class AnimationLinesDataBuilder
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
    AnimationLineListener listener;
    /** 柱状图数据  */
    double[] mLinesValues;
    
    static final LinkedList<AnimationLinesDataBuilder> BAR_DATAS = new LinkedList<AnimationLinesDataBuilder>();
    
    static
    {
        RectF rectF = null;
        int arraySize = 0;
        double maxYValue = 0;
        long duration = 0;
        long startDelay = 0;
        double[] linesValues = null;
        AnimationLineListener listener = null;
        int maxTestSize = Constant.maxTestCaseSize();
        AnimationLinesDataBuilder builder = null;
        for (int i = 0; i < maxTestSize + 1; i++)
        {
            arraySize = Constant.nextArraySize(i);
            linesValues = Constant.nextDoubleValues(arraySize, i);
            rectF = Constant.nextRectPadding(i);
            listener = Constant.nextListener(i);
            maxYValue = Utils.maxValue(linesValues);
            duration = Constant.nextAnimDuration(i);
            startDelay = Constant.nextStartDelay(i);
            builder = new AnimationLinesDataBuilder(rectF, maxYValue, duration, 
                    startDelay, listener, linesValues);
            BAR_DATAS.add(builder);
        }
    }

    public AnimationLinesDataBuilder(RectF mPaddingRect, double mMaxYValue, long mAnimDuration, long mAnimStartDelay,
            AnimationLineListener listener, double[] mBarsValues)
    {
        super();
        this.mPaddingRect = mPaddingRect;
        this.mMaxYValue = mMaxYValue;
        this.mAnimDuration = mAnimDuration;
        this.mAnimStartDelay = mAnimStartDelay;
        this.listener = listener;
        this.mLinesValues = mBarsValues;
    }
    
    @Override
    public String toString()
    {
        return "AnimationLinesDataBuilder [mPaddingRect=" + mPaddingRect + ", mMaxYValue=" + mMaxYValue
                + ", mAnimDuration=" + mAnimDuration + ", mAnimStartDelay=" + mAnimStartDelay + ", listener="
                + listener + ", mLinesValues=" + Arrays.toString(mLinesValues) + "]";
    }
    
}
