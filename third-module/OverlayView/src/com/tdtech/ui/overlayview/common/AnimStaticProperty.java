/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.common;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.View;

/**
 * Create Date: 2015-4-17<br>
 * Create Author: cWX239887<br>
 * Description : 动画静态属性
 */
public abstract class AnimStaticProperty
{
    /** 画笔 */
    protected Paint mPaint = new Paint();
    /** 路径 */
    protected Path mPath = new Path();
    /** 画笔颜色 */
    protected int mPaintColor = Color.RED;
    /** 画笔厚度（单位：dp） */
    protected float mThickness = 1.0f;

    public AnimStaticProperty(int paintColor, float thickness)
    {
        super();
        mPaintColor = paintColor;
        mThickness = thickness;
        mPaint.setColor(mPaintColor);
        mPaint.setStyle(Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeJoin(Join.ROUND);
        mPaint.setStrokeCap(Cap.ROUND);
    }

    public abstract void onDrawIt(View parent, Canvas canvas, CanvasCoordinate coordinate);
}
