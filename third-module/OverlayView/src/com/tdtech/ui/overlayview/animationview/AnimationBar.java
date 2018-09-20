/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.animationview;

import java.util.Arrays;
import java.util.LinkedList;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.view.animation.OvershootInterpolator;

import com.tdtech.ui.overlayview.common.AnimatorUpdateCtrl;
import com.tdtech.ui.overlayview.common.Utils;

/**
 * Create Date: 2015-3-23<br>
 * Create Author: cWX239887<br>
 * Description : 带动画效果的柱状图
 */
public class AnimationBar extends AnimationView
{
    public static final String TAG = "AnimationBar";

    /** 动画效果张力系数 */
    public static final float TENSION_FACTOR = 2.0f;
    /** 各动画的连贯系数 */
    public static final float CONSISTENT_FACTOR = 2.0f;
    /** 圆角比例 */
    public static final float CORNER_RADIUS_RATIO = 0.25f;
    /** 画线路径 */
    protected Path mPath = new Path();
    /** 待绘制的数值 */
    protected double[] mBarsValue = new double[0];
    /** 数值动画对象数组 */
    protected ValueAnimator[] mValueAnimators;
    /** 待绘制的矩形对象列表 */
    protected LinkedList<RectF> mRectFs = new LinkedList<RectF>();
    /** 最大柱状图宽度(单位：dp) */
    protected float mMaxBarDpWidth = 20;
    /** 动画效果是否已开始 */
    private boolean mIsStarted = false;
    /** 柱状图监听器 */
    protected AnimationBarListener mAnimationBarListener = new AnimationBarListener()
    {

        @Override
        public boolean isPointEnable(int positon, double lineValue)
        {
            return true;
        }
    };

    /**
     * 时间插值计算器
     * <ol>
     * <li>AccelerateDecelerateInterpolator：先加速再减速。
     * <li>AccelerateInterpolator：一直加速。
     * <li>AnticipateInterpolator：先往后一下，再嗖的一声一往无前。
     * <li>AnticipateOvershootInterpolator：先往后一下，再一直往前超过终点，再往回收一下。
     * <li>BounceInterpolator：最后像个小球弹几下。
     * <li>CycleInterpolator：重复几次，类似环形进度条。
     * <li>DecelerateInterpolator：一直减速。
     * <li>LinearInterpolator：线性插值。
     * <li>OvershootInterpolator：到了终点之后，超过一点，再往回走。
     * </ol>
     */
    protected TimeInterpolator mTimeInterpolator = new OvershootInterpolator(TENSION_FACTOR);

    public AnimationBar(Context context)
    {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        // 画线厚度引起的常量误差
        int length = mRectFs.size();
        for (int i = 0; i < length; i++)
        {
            if (!mAnimationBarListener.isPointEnable(i, mBarsValue[i]))
                continue;

            mPath.reset();
            RectF rectF = mRectFs.get(i);
            mPaint.setStyle(Style.FILL);
            mPaint.setAntiAlias(true);
            mPaint.setColor(mPaintColor);
            float barWidth = rectF.right - rectF.left;
            float pxRadius = barWidth * CORNER_RADIUS_RATIO;
            float[] radii = new float[]
            { pxRadius, pxRadius, pxRadius, pxRadius, 0, 0, 0, 0 };
            mPath.addRoundRect(rectF, radii, Direction.CCW);
            canvas.drawPath(mPath, mPaint);
        }
        if (!mIsStarted)
        {
            mIsStarted = true;
            startAnimation();
        }
        drawStaticProperties(canvas);
    }

    @Override
    public void cancelAnimation()
    {
        mRectFs.clear();

        if (mValueAnimators == null)
            return;

        for (ValueAnimator animator : mValueAnimators)
        {
            if(animator != null)
                animator.cancel();
        }
    }

    /**
     * 设置柱状图数值，外部传入的数值会拷贝一份放入内部，避免外部修改数组影响动画功能
     * @param barsValue	柱状图数组，绘制动画时会根据数据决定绘制的效果
     */
    public void setBarsValue(double[] barsValue)
    {

        cancelAnimation();

        if (barsValue == null || barsValue.length <= 0)
            return;

        mBarsValue = Arrays.copyOf(barsValue, barsValue.length);
        //不支持负值数据展示
        for (int i = 0; i < barsValue.length; i++)
            barsValue[i] = barsValue[i] < 0 ? 0 : barsValue[i];

        mIsStarted = false;
        invalidate();
    }

    @Override
    protected void startAnimation()
    {
        if (mBarsValue == null || mBarsValue.length <= 0)
            return;

        mValueAnimators = new ValueAnimator[mBarsValue.length];
        // 计算总绘制高度
        double totalLength = 0;
        
        for (int i = 0; i < mBarsValue.length; i++)
        {
            if (!mAnimationBarListener.isPointEnable(i, mBarsValue[i]))
                continue;
            
            totalLength += Math.abs(mBarsValue[i]);
        }
        
        double averageSpeed = totalLength / mAnimationDuration;
        
        // 计算连贯性减少时间量
        double COEFFICIENT = mBarsValue.length / CONSISTENT_FACTOR;
        averageSpeed = averageSpeed / COEFFICIENT;

        // 计算每一个矩形动画的起始时间
        long startTime = mAnimationStartDelay;
        for (int i = 0; i < mBarsValue.length; i++)
        {
            float value = (float) mBarsValue[i];
            // 设置属性动画
            ValueAnimator animator = ValueAnimator.ofFloat(0, value);

            // 设置动画持续时间
            long duration = 0;
            if (mAnimationBarListener.isPointEnable(i, mBarsValue[i]))
                duration = (long) (Math.abs(value) / averageSpeed);
            
            animator.setDuration(duration);
            // 设置时间动画计算器
            animator.setInterpolator(mTimeInterpolator);
            mValueAnimators[i] = animator;
            // 设置动画数值监听器
            float maxPxWidth = Utils.dp2Px(getContext(), mMaxBarDpWidth);
            ListAnimatorUpdateListener listener = new ListAnimatorUpdateListener(value, i, maxPxWidth);
            animator.addUpdateListener(listener);
            animator.setStartDelay(startTime);
            startTime = startTime + (long) (duration / COEFFICIENT);
            animator.start();
        }
    }

    public double[] getBarsValue()
    {
        return Arrays.copyOf(mBarsValue, mBarsValue.length);
    }

    public void setTimeInterpolator(TimeInterpolator timeInterpolator)
    {
        mTimeInterpolator = timeInterpolator;
    }

    public AnimationBarListener getAnimationBarListener()
    {
        return mAnimationBarListener;
    }

    public void setAnimationBarListener(AnimationBarListener animationBarListener)
    {
        if (animationBarListener == null)
            return;
        mAnimationBarListener = animationBarListener;
    }

    public float getMaxBarDpWidth()
    {
        return mMaxBarDpWidth;
    }

    public void setMaxBarDpWidth(float maxBarDpWidth)
    {
        mMaxBarDpWidth = maxBarDpWidth;
    }

    private class ListAnimatorUpdateListener implements AnimatorUpdateListener
    {
        /** 当前属性动画所在列表位置 */
        private final int mPositon;
        /** 矩形所占步进长度的比例 */
        private static final float RECT_PROPORTION = 0.618f;
        /** 最大柱状图宽度(单位：像素) */
        private final float mMaxPxWitdh;
        /** 动画更新控制器对象 */
        private final AnimatorUpdateCtrl mAnimatorUpdateCtrl;

        public ListAnimatorUpdateListener(float endValue, int positon, float maxPxWitdh)
        {
            mAnimatorUpdateCtrl = new AnimatorUpdateCtrl(endValue, mAnimationMinStepSize, mCoordinate);
            mPositon = positon;
            mMaxPxWitdh = maxPxWitdh;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation)
        {
            if (!mAnimatorUpdateCtrl.needUpdate(animation))
                return;

            // 计算出分隔区块的步进
            float stepSize = calcXStepSize(mBarsValue.length);
            float rectSize = stepSize * RECT_PROPORTION;
            // 计算当前绘制矩形的中心点X坐标
            float centerX = (mPositon * stepSize) + stepSize / 2;
            // 计算当前绘制矩形的坐标
            float rectLeft = centerX - rectSize / 2;
            float rectTop = (Float) animation.getAnimatedValue();
            float rectRight = centerX + rectSize / 2;
            float rectBottom = 0f;
            // 转换坐标系（数值坐标系—> 画布坐标系）
            float canvasLeft = mCoordinate.getCanvasX(rectLeft);
            float canvasTop = mCoordinate.getCanvasY(rectTop);
            float canvasRight = mCoordinate.getCanvasX(rectRight);
            float canvasBottom = mCoordinate.getCanvasY(rectBottom);

            float realPxWidth = canvasRight - canvasLeft;
            float diff = realPxWidth - mMaxPxWitdh;
            if (diff > 0)
            {
                canvasLeft += diff / 2;
                canvasRight -= diff / 2;
            }
            // 填充矩形参数对象（RectF）
            RectF rectF = null;
            try
            {
                rectF = mRectFs.get(mPositon);
            }
            catch(Exception e)
            {
                rectF = new RectF();
                mRectFs.add(rectF);
            }
            rectF.set(canvasLeft, canvasTop, canvasRight, canvasBottom);
            // 更新画布
            postInvalidate();
        }
    }

}
