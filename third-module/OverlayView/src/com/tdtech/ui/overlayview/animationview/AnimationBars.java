/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.animationview;

import java.util.Arrays;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;

/**
 * Create Date: 2015-4-20<br>
 * Create Author: cWX239887<br>
 * Description : 多阶颜色组成的动画柱状图（每个柱状图有多种颜色）
 */
public class AnimationBars extends AnimationBar
{
    /** 多阶段 柱状图颜色列表 */
    private final int[] mPhaseColor;
    /** 多阶段 柱状图路径 */
    private final Path[] mPhasePath;
    /** 待绘制的数值,每个柱状图由多个数值组成 */
    private double[][] mBarsValues = new double[0][0];
    /** 动画效果是否已开始 */
    private boolean mIsStarted = false;

    public AnimationBars(Context context)
    {
        super(context);
        mPhaseColor = new int[3];
        mPhasePath = new Path[mPhaseColor.length];
        for (int i = 0; i < mPhasePath.length; i++)
            mPhasePath[i] = new Path();
    }

    public AnimationBars(Context context, int[] phaseColors)
    {
        super(context);
        if (phaseColors != null)
            mPhaseColor = Arrays.copyOf(phaseColors, phaseColors.length);
        else mPhaseColor = new int[]
        { Color.GREEN, Color.GREEN, Color.GREEN };

        mPhasePath = new Path[mPhaseColor.length];
        for (int i = 0; i < mPhasePath.length; i++)
            mPhasePath[i] = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(mCanvasColor);
        mCoordinate.setCanvasWidth(getMeasuredWidth());
        mCoordinate.setCanvasHeight(getMeasuredHeight());
        mCoordinate.setMaxX(mCoordinate.getMaxY());

        // 画线厚度引起的常量误差
        int length = mRectFs.size();
        for (int i = 0; i < length; i++)
        {
            if (!mAnimationBarListener.isPointEnable(i, sum(mBarsValues[i])))
                continue;

            mPath.reset();
            RectF rectF = mRectFs.get(i);
            mPaint.setStyle(Style.FILL);
            mPaint.setAntiAlias(true);
            float barWidth = rectF.right - rectF.left;
            float pxRadius = barWidth * CORNER_RADIUS_RATIO;
            float[] radii = new float[]
            { pxRadius, pxRadius, pxRadius, pxRadius, 0, 0, 0, 0 };
            onDrawRect(canvas, i, rectF, mBarsValues[i], pxRadius, radii);
        }
        
        if (!mIsStarted)
        {
            mIsStarted = true;
            startAnimation();
        }
        drawStaticProperties(canvas);
    }

    private void onDrawRect(Canvas canvas, int position, RectF rectF, double[] values, float pxRadius, float[] radii)
    {
        if (values == null || values.length <= 0)
            return;

        RectF tmpRectF = new RectF(rectF);
        // 计算不同阶段所占比例
        float valueY = mCoordinate.getValueY(rectF.top);
        float totalValue = (float) sum(values);
        float valueSum = valueY;

        int minLength = Math.min(values.length, mPhaseColor.length);

        for (int i = minLength - 1; i >= 0; i--)
        {

            mPaint.setColor(mPhaseColor[i]);
            float scale = (float) (values[i] / totalValue);
            float value = (scale * valueY);
            valueSum -= value;
            float canvasY = mCoordinate.getCanvasY(value + valueSum);
            tmpRectF.top = canvasY;
            float remainCancasHeight = tmpRectF.top - rectF.top;

            if (remainCancasHeight < pxRadius || i == minLength - 1)
            {
                Path path = mPhasePath[i];
                path.reset();
                float fixedRadius = pxRadius * (1 - remainCancasHeight/pxRadius);
                radii = new float[]
                        { fixedRadius, fixedRadius, fixedRadius, fixedRadius, 0, 0, 0, 0 };
                path.addRoundRect(tmpRectF, radii, Direction.CCW);
                canvas.drawPath(path, mPaint);
            }
            else
            {
                canvas.drawRect(tmpRectF, mPaint);
            }
        }

    }

    public int[] getPhaseColor()
    {
        return mPhaseColor;
    }

    public double[][] getBarsValues()
    {
        return mBarsValues;
    }

    public void setBarsValues(double[]... barsValues)
    {
        cancelAnimation();

        if (barsValues == null || barsValues.length <= 0)
            return;
        
        //拷贝外部数据
        mBarsValues = new double[barsValues.length][];
        for (int i = 0; i < mBarsValues.length; i++)
            mBarsValues[i] = Arrays.copyOf(barsValues[i], barsValues[i].length);
        
        double[] barsValue = new double[mBarsValues.length];
        for (int i = 0; i < mBarsValues.length; i++)
        {
            double[] values = mBarsValues[i];
            //堆积图不支持负值数据
            for (int j = 0; j < values.length; j++)
                values[j] = values[j] < 0 ? 0 : values[j];
            //合并单个柱子的各段数据
            barsValue[i] = sum(values);
        }

        setBarsValue(barsValue);
        mIsStarted = false;
    }

    private double sum(double[] ds)
    {
        double sum = 0;
        if (ds == null || ds.length == 0)
            sum = 0;
        else
        {
            for (double d : ds)
                sum += d;
        }
        return sum;
    }
}
