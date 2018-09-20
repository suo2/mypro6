/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.coordinateview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;

import com.tdtech.ui.overlayview.common.Utils;

/**
 * Create Date: 2015-4-9<br>
 * Create Author: cWX239887<br>
 * Description : 带横线的坐标控件
 */
public class SidewaysView extends BaseCoordinate
{
    public SidewaysView(Context context)
    {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        mCoordinate.setCanvasWidth(getMeasuredWidth());
        mCoordinate.setCanvasHeight(getMeasuredHeight());
        mCoordinate.setMaxX(mCoordinate.getMaxY());

        mLinePaint.setStrokeWidth(Utils.dp2Px(getContext(), mLineSize));
        mLinePaint.setColor(mLineColor);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Style.STROKE);
        mLinePaint.setStrokeJoin(Join.ROUND);
        mLinePaint.setStrokeCap(Cap.ROUND);

        float startX = 0;
        float canvasStartX = mCoordinate.getCanvasX(startX);
        float stopX = mCoordinate.getMaxX();
        float canvasStopX = mCoordinate.getCanvasX(stopX);
        float curY = 0;
        float canvasCurY;
        float stepSize = mCoordinate.getMaxY() / (MAX_Y_LINE - 1);
        for (int i = 0; i < MAX_Y_LINE; i++)
        {
            if (i == 0)
                mLinePaint.setPathEffect(null);
            else mLinePaint.setPathEffect(mPathEffect);
            canvasCurY = mCoordinate.getCanvasY(curY);
            // 线条厚度导致的误差
            canvasCurY += Utils.dp2Px(getContext(), mLineSize) / 2;
            canvas.drawLine(canvasStartX, canvasCurY, canvasStopX, canvasCurY, mLinePaint);
            curY += stepSize;
        }

        // 计算最大字符宽度
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(Utils.dp2Px(getContext(), mTextSize));
        mTextPaint.setTextAlign(Align.CENTER);
        float maxTextPxSize = mTextPaint.measureText(mCoordinateAdapter.getMaxText(mPointLength));

        // 自适应X轴数字间隔宽度
        int increaseSize = 1;
        increaseSize = Utils.calcTextIncreaseSize((int) mCoordinate.getCanvasWidth(),
                (int) maxTextPxSize + Utils.dp2Px(getContext(), mTextMargin), (int) mPointLength);

        for (int i = 0; i < mPointLength; i = i + increaseSize)
        {
            onDrawXValue(canvas, i, mCoordinateAdapter.getText(i, mPointLength));
        }
    }
}
