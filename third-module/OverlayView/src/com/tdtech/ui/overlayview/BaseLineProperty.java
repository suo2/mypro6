/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.view.View;

import com.tdtech.ui.overlayview.common.AnimStaticProperty;
import com.tdtech.ui.overlayview.common.CanvasCoordinate;
import com.tdtech.ui.overlayview.common.Utils;

/**
 * Create Date: 2015-4-17<br>
 * Create Author: cWX239887<br>
 * Description : 参考基线属性
 */
public class BaseLineProperty extends AnimStaticProperty
{

    private double mBaseLineValue;
    /** 虚线效果 */
    private PathEffect mPathEffect;
    /** 虚线线点间隔 (单位：dp) */
    private final float mLineInterval = 3;
    /** 文字表示 */
    private String mShowText = "";
    /** 文字用的画笔 */
    private Paint mTextPaint = new Paint();
    /** 文字的大小(单位：dp) */
    private final float DEFAULT_TEXT_SIZE = 7;

    public BaseLineProperty(int paintColor, float thickness, double baseLineValue, String showText)
    {
        super(paintColor, thickness);
        mBaseLineValue = baseLineValue;
        mShowText = showText;
        mTextPaint.setStyle(Style.FILL);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(paintColor);
    }

    @Override
    public void onDrawIt(View parent, Canvas canvas, CanvasCoordinate coordinate)
    {
        mTextPaint.setTextSize(Utils.dp2Px(parent.getContext(), DEFAULT_TEXT_SIZE));
        float thickNessPx = Utils.dp2Px(parent.getContext(), mThickness);
        mPaint.setStrokeWidth(thickNessPx);

        float linePxInterval = Utils.dp2Px(parent.getContext(), mLineInterval);
        mPathEffect = new DashPathEffect(new float[]
        { linePxInterval, linePxInterval }, 0);

        float startX = 0;
        float canvasStartX = coordinate.getCanvasX(startX);
        float stopX = coordinate.getMaxX();
        float canvasStopX = coordinate.getCanvasX(stopX);
        float canvasY = coordinate.getCanvasY((float) mBaseLineValue);
        // 线条厚度导致的误差
        canvasY += Utils.dp2Px(parent.getContext(), mThickness) / 2;
        mPaint.setPathEffect(mPathEffect);
        canvas.drawLine(canvasStartX, canvasY, canvasStopX, canvasY, mPaint);

        float textwidth = mTextPaint.measureText(mShowText);
        canvas.drawText(mShowText, canvasStopX - textwidth / 3, canvasY + mTextPaint.getTextSize(), mTextPaint);
    }
}