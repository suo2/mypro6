/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.animationview;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.tdtech.ui.overlayview.common.CanvasCoordinate;
import com.tdtech.ui.overlayview.common.ValuePoint;

/**
 * Create Date: 2015-4-14<br>
 * Create Author: cWX239887<br>
 * Description :带动画效果的折线图监听器
 */
public interface AnimationLineListener
{
    /** 绘制节点 */
    void onDrawPoint(AnimationLine animationLine, Canvas canvas, CanvasCoordinate coordinate, ValuePoint point, int positon, Paint paint,
            double lineValue);

    /** 当前节点是否需要绘制 */
    boolean isPointEnable(int positon, double lineValue);
}
