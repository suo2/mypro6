/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.coordinateview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.view.View;

import com.tdtech.ui.overlayview.CoordinateBaseAdapter;
import com.tdtech.ui.overlayview.common.CanvasCoordinate;
import com.tdtech.ui.overlayview.common.Utils;

/**
 * Create Date: 2015-4-9<br>
 * Create Author: cWX239887<br>
 * Description :坐标控件的基类
 */
public class BaseCoordinate extends View
{

    /** 虚线条数 */
    public static final int MAX_Y_LINE = 4;
    /** 虚线效果 */
    PathEffect mPathEffect;
    /** 虚线线点间隔 (单位：dp) */
    float mLineInterval = 1.5f;
    /** 画点的数量 */
    int mPointLength = 0;
    /** 画线用的画笔 */
    Paint mLinePaint = new Paint();
    /** 画线的厚度（单位：dp） */
    float mLineSize = 1.0f;
    /** 画线的颜色 */
    int mLineColor = Color.BLUE;
    /** 画文字用的画笔 */
    Paint mTextPaint = new Paint();
    /** 画文字的大小（单位：dp） */
    float mTextSize = 13;
    /** 画文字的大小（单位：dp） */
    float mTextMargin = 3;
    /** 画文字的颜色 */
    int mTextColor = Color.BLUE;
    /** 画布坐标系对象 */
    public final CanvasCoordinate mCoordinate = new CanvasCoordinate();
    /** 绘制点的最大半径（单位：dp） */
    private float mMaxDpRadius = 4.4f;
    CoordinateBaseAdapter mCoordinateAdapter = new CoordinateBaseAdapter()
    {

        @Override
        public String getText(int positon, int totalPositon)
        {
            return String.valueOf(positon);
        }

        @Override
        public String getMaxText(int totalPositon)
        {
            return String.valueOf(totalPositon);
        }

        @Override
        public int getColor(int position, int totalPosition)
        {
            return Color.GREEN;
        }
    };

    public BaseCoordinate(Context context)
    {
        super(context);
        float linePxInterval = Utils.dp2Px(context, mLineInterval);
        mPathEffect = new DashPathEffect(new float[]
        { linePxInterval, linePxInterval }, 0);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public void onDrawXValue(Canvas canvas, int position, String text)
    {
        // 计算出分隔区块的步进
        float stepSize = calcXStepSize(mPointLength);
        float offset = getIndexOffset(mPointLength);
        // 计算当前绘制矩形的中心点X坐标
        float centerX = mCoordinate.getCanvasX((position * stepSize) + stepSize / 2);
        float centerY = mCoordinate.getCanvasY(0) + (float) (mTextPaint.getTextSize()) + offset;
        canvas.drawText(text, centerX, centerY, mTextPaint);
    }

    public void onDrawYValue(Canvas canvas, int position, int linesColor)
    {
        // 计算出分隔区块的步进
        float stepSize = calcXStepSize(mPointLength);
        // 计算当前绘制矩形的中心点X坐标
        float centerX = mCoordinate.getCanvasX((position * stepSize) + stepSize / 2);
        float linesHalfWidth = Utils.dp2Px(getContext(), mLineSize) / 2;
        float startY = mCoordinate.getCanvasY(0) - linesHalfWidth;
        float endY = mCoordinate.getCanvasY(mCoordinate.getMaxY()) + linesHalfWidth;
        int oldColor = mLinePaint.getColor();
        mLinePaint.setColor(linesColor);
        // 线条厚度导致的误差
        canvas.drawLine(centerX, startY, centerX, endY, mLinePaint);
        mLinePaint.setColor(oldColor);
    }

    public void setAnimationPadding(float left, float top, float right, float bottom)
    {
        mCoordinate.setCanvasPaddingLeft(left);
        mCoordinate.setCanvasPaddingTop(top);
        mCoordinate.setCanvasPaddingRight(right);
        mCoordinate.setCanvasPaddingBottom(bottom);
    }

    public void setMaxValueY(float maxY)
    {
        mCoordinate.setMaxY(maxY);
    }

    public float getLineInterval()
    {
        return mLineInterval;
    }

    public int getPointLength()
    {
        return mPointLength;
    }

    public Paint getLinePaint()
    {
        return mLinePaint;
    }

    public float getLineSize()
    {
        return mLineSize;
    }

    public int getLineColor()
    {
        return mLineColor;
    }

    public float getTextSize()
    {
        return mTextSize;
    }

    public int getTextColor()
    {
        return mTextColor;
    }

    public void setPointLength(int pointLength)
    {
        mPointLength = pointLength;
    }

    public void setLinePaint(Paint linePaint)
    {
        mLinePaint = linePaint;
    }

    public void setLineSize(float lineSize)
    {
        mLineSize = lineSize;
    }

    public void setLineColor(int lineColor)
    {
        mLineColor = lineColor;
    }

    public void setTextSize(float textSize)
    {
        mTextSize = textSize;
    }

    public void setTextColor(int textColor)
    {
        mTextColor = textColor;
    }

    public float getTextMargin()
    {
        return mTextMargin;
    }

    public void setTextMargin(float textMargin)
    {
        mTextMargin = textMargin;
    }

    public CoordinateBaseAdapter getCoordinateAdapter()
    {
        return mCoordinateAdapter;
    }

    public void setCoordinateAdapter(CoordinateBaseAdapter coordinateAdapter)
    {
        mCoordinateAdapter = coordinateAdapter;
    }

    public void setLineInterval(float lineInterval)
    {
        mLineInterval = lineInterval;
    }

    /** 计算步进长度 */
    public float calcXStepSize(int pointLength)
    {
        float stepSize = 0;
        float maxValueX = mCoordinate.getMaxX();
        float rectLength = pointLength;
        stepSize = maxValueX / rectLength;
        return stepSize;
    }

    public float getIndexOffset(int length)
    {
        float stepSize = 0;
        if (length != 0)
        {
            stepSize = mCoordinate.getValidCanvasWidth() / length;
        }
        float maxPxRadius = Utils.dp2Px(getContext(), mMaxDpRadius);
        float pointRadius = (stepSize / 6 > maxPxRadius) ? maxPxRadius : stepSize / 6;
        float ringWidth = pointRadius * 0.392f;
        return pointRadius / 2 + ringWidth + 1;
    }
}
