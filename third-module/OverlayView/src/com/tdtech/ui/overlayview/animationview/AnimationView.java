/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.animationview;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.tdtech.ui.overlayview.common.AnimStaticProperty;
import com.tdtech.ui.overlayview.common.CanvasCoordinate;

/**
 * Create Date: 2015-3-25<br>
 * Create Author: cWX239887<br>
 * Description : 抽象的动画基类
 */
public abstract class AnimationView extends View
{
    /** 画布颜色 */
    protected int mCanvasColor = Color.TRANSPARENT;
    /** 画笔颜色 */
    protected int mPaintColor = Color.GREEN;
    /** 总的动画时间(单位：毫秒) */
    protected long mAnimationDuration = 0;
    /** 动画起始延迟时间(单位：毫秒) */
    protected long mAnimationStartDelay = 0;
    /** 画笔 */
    protected Paint mPaint = new Paint();
    /** 画布坐标系对象 */
    protected CanvasCoordinate mCoordinate = new CanvasCoordinate();
    /** 动画静态属性列表 */
    protected final LinkedList<AnimStaticProperty> mAnimProperties = new LinkedList<AnimStaticProperty>();
    /** 动画最小步进（单位：像素） */
    protected int mAnimationMinStepSize = 1;
    
    /** 启动动画 */
    protected abstract void startAnimation();

    /** 取消所有动画，主要在重启动画时取消之前的动画行为 */
    public abstract void cancelAnimation();

    public AnimationView(Context context)
    {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(mCanvasColor);
        mCoordinate.setCanvasWidth(getMeasuredWidth());
        mCoordinate.setCanvasHeight(getMeasuredHeight());
        mCoordinate.setMaxX(mCoordinate.getMaxY());
    }

    /** 绘制静态属性，绘制附属在动画之上的静态元素 */
    protected void drawStaticProperties(Canvas canvas)
    {
        for (AnimStaticProperty property : mAnimProperties)
            property.onDrawIt(this, canvas, mCoordinate);
    }

    /** 设置动画持续时间 */
    public void setAnimationDuration(long duration)
    {
        mAnimationDuration = duration;
    }

    /** 设置画布边距 */
    public void setAnimationPadding(float left, float top, float right, float bottom)
    {
        mCoordinate.setCanvasPaddingLeft(left);
        mCoordinate.setCanvasPaddingTop(top);
        mCoordinate.setCanvasPaddingRight(right);
        mCoordinate.setCanvasPaddingBottom(bottom);
    }

    /** 设置X-Y轴最大值，X轴最大值根据Y轴最大值和画布比例计算得出 */
    public void setMaxValueY(float maxY)
    {
        mCoordinate.setMaxY(maxY);
    }

    public int getCanvasColor()
    {
        return mCanvasColor;
    }

    public int getPaintColor()
    {
        return mPaintColor;
    }

    public long getAnimationDuration()
    {
        return mAnimationDuration;
    }

    public long getAnimationStartDelay()
    {
        return mAnimationStartDelay;
    }

    public Paint getPaint()
    {
        return mPaint;
    }

    public CanvasCoordinate getCoordinate()
    {
        return mCoordinate;
    }

    public void setCanvasColor(int canvasColor)
    {
        mCanvasColor = canvasColor;
    }

    public void setPaintColor(int paintColor)
    {
        mPaintColor = paintColor;
    }

    public void setAnimationStartDelay(long animationStartDelay)
    {
        mAnimationStartDelay = animationStartDelay;
    }

    public void setPaint(Paint paint)
    {
        mPaint = paint;
    }

    public void setCoordinate(CanvasCoordinate coordinate)
    {
        mCoordinate = coordinate;
    }

    public void addStaticProperty(AnimStaticProperty property)
    {
        if (property != null)
            mAnimProperties.add(property);
    }

    public void clearStaticProperty()
    {
        mAnimProperties.clear();
    }

    public int getmAnimationMinStepSize()
    {
        return mAnimationMinStepSize;
    }

    public void setAnimationMinStepSize(int animationMinStepSize)
    {
        mAnimationMinStepSize = animationMinStepSize;
    }

    /** 计算数值步进长度 */
    public float calcXStepSize(int pointLength)
    {
        float stepSize = 0;
        float maxValueX = mCoordinate.getMaxX();
        float rectLength = pointLength;
        stepSize = maxValueX / rectLength;
        return stepSize;
    }
}
