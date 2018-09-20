/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.common;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

import com.tdtech.ui.overlayview.animationview.AnimationLineListener;

/**
 * Create Date: 2015-3-25<br>
 * Create Author: cWX239887<br>
 * Description : 多点二维坐标数值动画类，目的是将N个PointAnimator连贯起来，实现折线图
 */
public class ListPointAnimator
{
    public static final String TAG = "ListPointAnimator";

    /** 输入的数值点 */
    private ValuePoint[] mValuePoints;
    /** 起始动画类 */
    private PointAnimator mAnimatorHead;
    /** 动画效果/插值监听器 */
    private ListPointListener mListPointListener;

    private long mDuration;

    public ListPointAnimator()
    {
        super();
    }

    public void cancelAnimation()
    {
        if (mAnimatorHead == null)
        {
            return;
        }
        PointAnimator tempAnimator = mAnimatorHead;
        while (tempAnimator != null)
        {
            tempAnimator.cancel();
            tempAnimator = tempAnimator.getNextAnimator();
        }
    }

    public ValuePoint[] getValuePoints()
    {
        return mValuePoints;
    }

    public void setValuePoints(ValuePoint... valuePoints)
    {
        mValuePoints = valuePoints;
    }

    private float calcTotalLineLength(AnimationLineListener animationLineListener, double[] lineValues)
    {
        ValuePoint lastPoint = null;
        float totalLineLength = 0;
        for (int i = 0; i < mValuePoints.length; i++)
        {
            if (!animationLineListener.isPointEnable(i, lineValues[i]))
                continue;

            ValuePoint curPoint = mValuePoints[i];

            if (lastPoint == null)
                lastPoint = curPoint;

            double lenX = Math.abs(curPoint.mValueX - lastPoint.mValueX);
            double lenY = Math.abs(curPoint.mValueY - lastPoint.mValueY);
            float lineLength = (float) Math.sqrt(lenX * lenX + lenY * lenY);
            totalLineLength += lineLength;
            lastPoint = curPoint;
        }
        return totalLineLength;
    }
    
    public void start(AnimationLineListener animationLineListener, double[] lineValues, long startDelay)
    {
        ValuePoint lastPoint = null;
        mAnimatorHead = null;
        // 计算总的数值长度
        float totalLineLength = calcTotalLineLength(animationLineListener, lineValues);

        // 计算数值绘制速度
        float speed = (float) (mDuration / totalLineLength);

        lastPoint = null;
        PointAnimator lastAnimator = null;
        long sumDelay = startDelay < 0 ? 0 : startDelay;
        for (int i = 0; i < mValuePoints.length; i++)
        {
            if (!animationLineListener.isPointEnable(i, lineValues[i]))
                continue;

            ValuePoint curPoint = mValuePoints[i];
            if (lastPoint == null)
                lastPoint = curPoint;

            double lenX = Math.abs(curPoint.mValueX - lastPoint.mValueX);
            double lenY = Math.abs(curPoint.mValueY - lastPoint.mValueY);
            float lineLength = (float) Math.sqrt(lenX * lenX + lenY * lenY);
            PointAnimator animator = new PointAnimator();
            // 设置起始动画
            if (mAnimatorHead == null)
                mAnimatorHead = animator;

            // 关联不同的动画对象，保证后一个动画发生在前一个动画完成后
            if (lastAnimator != null)
                lastAnimator.setNextAnimator(animator);
            lastAnimator = animator;

            animator.setPointValues(lastPoint, curPoint);
            long sectionDuration = (long) (lineLength * speed);
            animator.setDuration(sectionDuration);
            animator.setStartDelay(sumDelay);
            addListener(animator, i, mListPointListener);
            lastPoint = curPoint;
            sumDelay += sectionDuration;
            animator.start();
        }
    }

    private void addListener(ValueAnimator animator, final int position, ListPointListener listener)
    {
        animator.addUpdateListener(new AnimatorUpdateListener()
        {

            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                mListPointListener.onAnimationUpdate(position, animation);
            }
        });

        animator.addListener(new AnimatorListener()
        {

            @Override
            public void onAnimationStart(Animator animation)
            {
                mListPointListener.onAnimationStart(position, animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {
                mListPointListener.onAnimationRepeat(position, animation);
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                mListPointListener.onAnimationEnd(position, animation);
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {
                mListPointListener.onAnimationCancel(position, animation);
            }
        });

    }

    public long getDuration()
    {
        return mDuration;
    }

    public void setDuration(long duration)
    {
        mDuration = duration;
    }

    public ListPointListener getListPointListener()
    {
        return mListPointListener;
    }

    public void setListPointListener(ListPointListener listPointListener)
    {
        mListPointListener = listPointListener;
    }
}
