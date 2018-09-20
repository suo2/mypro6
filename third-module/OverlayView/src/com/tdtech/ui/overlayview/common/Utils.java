/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;

/**
 * Create Date: 2015-4-9<br>
 * Create Author: cWX239887<br>
 * Description : 图形绘制工具类
 */
public class Utils
{
    public static int dp2Px(Context context, float dp)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /** 绘制环形数据点 */
    public static void drawRingCircular(Canvas canvas, Paint paint, ValuePoint centerPoint, float radiusPixel,
            float ringWidth, int ringColor, int centerColor)
    {
        paint.setColor(centerColor);
        paint.setStyle(Style.FILL);
        paint.setAntiAlias(true);
        float cx = centerPoint.mCanvasX;
        float cy = centerPoint.mCanvasY;
        canvas.drawCircle(cx, cy, radiusPixel, paint);

        paint.setColor(ringColor);
        paint.setStyle(Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(ringWidth);
        canvas.drawCircle(cx, cy, radiusPixel, paint);
    }

    /** 绘制带阴影的环形数据点 */
    public static void drawEdgeCircular(Canvas canvas, Paint paint, ValuePoint centerPoint, float radiusPixel,
            int ringColor, int centerColor)
    {
        float cx = centerPoint.mCanvasX;
        float cy = centerPoint.mCanvasY;
        paint.setStyle(Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(ringColor);
        paint.setAlpha(110);
        canvas.drawCircle(cx, cy, radiusPixel + radiusPixel / 2, paint);

        paint.setColor(centerColor);
        paint.setAlpha(225);
        canvas.drawCircle(cx, cy, radiusPixel, paint);
    }

    public static int calcTextIncreaseSize(int totalWidth, int maxTextPxSize, int textNum)
    {
        int increaseSize = 1;
        for (int i = 1; i < textNum; i = i * 2)
        {
            float textInterval = totalWidth / (textNum / i);

            if (textInterval < maxTextPxSize)
                continue;
            else
            {
                increaseSize = i;
                break;
            }
        }
        return increaseSize;
    }

    public static void drawTriangle(Canvas canvas, CanvasCoordinate coordinate, Path path, ValuePoint centerPoint,
            float radiusPixel, Paint paint, float ringWidth, int ringColor, int centerColor)
    {
        float radius = coordinate.getValueX(radiusPixel) - coordinate.getValueX(0);
        // 三个点，顺时针方向,数学坐标系
        float centerX = centerPoint.getValueX();
        float centerY = centerPoint.getValueY();

        float leftX = (float) (centerX - radius * Math.acos(Math.PI / 6.0));
        float leftY = (float) (centerY - radius * Math.asin(Math.PI / 6.0));

        float topX = centerX;
        float topY = centerY + radius;

        float rightX = (float) (centerX + radius * Math.acos(Math.PI / 6.0));
        float rightY = (float) (centerY - radius * Math.asin(Math.PI / 6.0));

        path.moveTo(coordinate.getCanvasX(leftX), coordinate.getCanvasY(leftY));
        path.lineTo(coordinate.getCanvasX(topX), coordinate.getCanvasY(topY));
        path.lineTo(coordinate.getCanvasX(rightX), coordinate.getCanvasY(rightY));
        path.close();

        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        paint.setColor(centerColor);

        canvas.drawPath(path, paint);
        paint.setStyle(Style.STROKE);
        paint.setColor(ringColor);
        paint.setStrokeWidth(ringWidth);
        canvas.drawPath(path, paint);
    }

    public static double maxValue(double... values)
    {
        if (values.length <= 0)
            return 0;
        double max = values[0];
        for (double d : values)
        {
            if (d > max)
                max = d;
        }
        return max;
    }

    public static double sumValue(double... values)
    {
        if (values.length <= 0)
            return 0;
        double sum = values[0];
        for (double d : values)
        {
            sum = sum + d;
        }
        return sum;
    }
}
