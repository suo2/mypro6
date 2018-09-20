/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.tdtech.ui.overlayview.animationview.AnimationBarListener;
import com.tdtech.ui.overlayview.animationview.AnimationLine;
import com.tdtech.ui.overlayview.animationview.AnimationLineListener;
import com.tdtech.ui.overlayview.common.CanvasCoordinate;
import com.tdtech.ui.overlayview.common.Utils;
import com.tdtech.ui.overlayview.common.ValuePoint;

/**
 * Create Date: 2015-4-23<br>
 * Create Author: cWX239887<br>
 * Description : 测试用例常量数据
 */
public class Constant
{

    /** 数值长度列表 */
    static final int[] ARRAY_SIZE;
    /** 默认数值长度 */
    static final int DEFAULT_ARRAY_SIZE;
    /** 画布边距列表 */
    static final RectF[] CANVAS_PADDINGS;
    /** 默认画布边距 */
    static final RectF DEFAULT_CANVAS_PADDINGS;
    /** 数值数组 */
    static final double[] DOUBLE_VALUES;
    /** 默认数值 */
    static final double DEFAULT_DOUBLE_VALUE;
    /** 柱状图监听器 */
    static final BarListener[] BAR_LISTENERS;
    /** 默认的柱状图监听器 */
    static final BarListener DEFAULT_BAR_LISTENER;
    /** 柱状图监听器 */
    static final LineListener[] LINE_LISTENERS;
    /** 默认的柱状图监听器 */
    static final LineListener DEFAULT_LINE_LISTENER;
    /** 动画时间数组 */
    static final long[] ANIMATION_DURATION;
    /** 默认动画时间 */
    static final long DEFAULT_ANIMATION_DURATION;
    /** 动画延迟启动时间数组 */
    static final long[] ANIMATION_START_DELAYS;
    /** 默认动画延迟启动时间 */
    static final long DEFAULT_ANIMATION_START_DELAY;
    /** 数值比例 */
    static final double[][] VALUE_RATIO;
    /** 默认数值比例 */
    static final double[] DEFAULT_VALUE_RATIO;
    /** 禁止绘制点的集合索引 */
    static final int[][] DISABLE_POINTS;
    /** 默认被禁止绘制的点索引 */
    static final int[] DEFAULT_DISABLE_POINTS;
    
    static
    {
        ARRAY_SIZE = new int[]{
                0,1,2,3,4,5,6,7,8,9,10,
                11,12,13,14,15,16,17,18,19,20,
                21,22,23,24,25,26,27,28,29,30,
                31,32,33,34,35,36,37,38,39,40,
        };
    }
    
    static
    {
        CANVAS_PADDINGS = new RectF[]{
                new RectF(-1, -1, -1, -1),
                new RectF(0, 0, 0, 0),
                new RectF(1, 1, 1, 1),
                new RectF(10, 10, 10, 10),
                new RectF(1000, 1000, 1000, 1000),
        };
    }
    
    static
    {
        DOUBLE_VALUES = new double[]{
                Double.MIN_VALUE,
                Double.MAX_VALUE,
                Double.MAX_EXPONENT,
                Double.MIN_NORMAL,
                Double.NaN,
                Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY,
                Float.MIN_VALUE,
                Float.MAX_VALUE,
                Float.MAX_EXPONENT,
                Float.MIN_NORMAL,
                Float.NaN,
                Float.NEGATIVE_INFINITY,
                Float.POSITIVE_INFINITY,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                Long.MIN_VALUE,
                Long.MAX_VALUE,
                -1,1,100,1000,1000000,
        };
    }
    
    static
    {
        int length = 40;
        BAR_LISTENERS = new BarListener[length];
        for (int i = 0; i < length; i++)
            BAR_LISTENERS[i] = new BarListener(i);
    }
    
    static
    {
        int length = 40;
        LINE_LISTENERS = new LineListener[length];
        for (int i = 0; i < length; i++)
            LINE_LISTENERS[i] = new LineListener(i);
    }
    
    
    static
    {
        ANIMATION_DURATION = new long[]{
                -1,0,10,100,1000,2000,10000
        };
    }
    
    static
    {
        ANIMATION_START_DELAYS = new long[]{
                -1,0,10,100,1000,2000,10000
        };
    }
    
    static
    {
        VALUE_RATIO = new double[][]{
                new double[]{1,1,1},
                new double[]{1,2,3},
                new double[]{2,3,4},
                new double[]{20,1,1},
                new double[]{30,1,1},
                new double[]{40,1,1},
                new double[]{50,1,1},
                new double[]{60,1,1},
                new double[]{20,20,1},
                new double[]{30,30,1},
                new double[]{40,40,1},
                new double[]{50,50,1},
                new double[]{60,60,1},
        };
    }
    
    static
    {
        DISABLE_POINTS = new int[][]{
          new int[]{0},  
          new int[]{1},      
          new int[]{2},      
          new int[]{0,2},      
          new int[]{1,3,5},      
          new int[]{0,2,4,6},      
          new int[]{1,3,5,7},      
          new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,
                  15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31},      
        };
    }
    
    static 
    {
        DEFAULT_ARRAY_SIZE = 16;
        DEFAULT_CANVAS_PADDINGS = new RectF(20, 30, 20, 30);
        DEFAULT_DOUBLE_VALUE = 2000;
        DEFAULT_BAR_LISTENER = new BarListener(2);
        DEFAULT_ANIMATION_DURATION = 1500;
        DEFAULT_ANIMATION_START_DELAY = 500;
        DEFAULT_VALUE_RATIO = new double[]{1,1,1};
        DEFAULT_LINE_LISTENER = new LineListener(2);
        DEFAULT_DISABLE_POINTS = new int[]{2};
    }
    
    static int nextArraySize(int index)
    {
        int ret = DEFAULT_ARRAY_SIZE;
        if(index < ARRAY_SIZE.length)
            ret = ARRAY_SIZE[index];
        return ret;
    }
    
    static RectF nextRectPadding(int index)
    {
        RectF ret= DEFAULT_CANVAS_PADDINGS;
        if(index < CANVAS_PADDINGS.length)
            ret = CANVAS_PADDINGS[index];
        return ret;
    }
    
    static double[] nextDoubleValues(int size, int index)
    {
        double baseValue = DEFAULT_DOUBLE_VALUE;
        if(index < DOUBLE_VALUES.length)
            baseValue = DOUBLE_VALUES[index];
        
        double[] values = new double[size];
        for (int i = 0; i < size; i++)
        {
            values[i] = Math.sin(10 * i) * baseValue + baseValue;
        }
        
        return values;
    }
    
    static AnimationBarListener nextBarListener(int index)
    {
        AnimationBarListener listener = DEFAULT_BAR_LISTENER;
        if(index < BAR_LISTENERS.length)
            listener = BAR_LISTENERS[index];
        
        return listener;
    }
    
    static AnimationLineListener nextListener(int index)
    {
        LineListener listener = DEFAULT_LINE_LISTENER;
        if(index < LINE_LISTENERS.length)
            listener = LINE_LISTENERS[index];
        
        return listener;
    }
    
    static long nextAnimDuration(int index)
    {
        long ret = DEFAULT_ANIMATION_DURATION;
        if(index < ANIMATION_DURATION.length)
            ret = ANIMATION_DURATION[index];
        return ret;
    }
    
    static long nextStartDelay(int index)
    {
        long ret = DEFAULT_ANIMATION_START_DELAY;
        if(index < ANIMATION_START_DELAYS.length)
            ret = ANIMATION_START_DELAYS[index];
        return ret;
    }
    
    static double[] nextRatio(int index)
    {
        double[] ret = DEFAULT_VALUE_RATIO;
        if(index < VALUE_RATIO.length)
            ret = VALUE_RATIO[index];
        
        return ret;
    }
    
    static int[] nextDisablePoints(int index)
    {
        int[] ret = DEFAULT_DISABLE_POINTS;
        if(index < DISABLE_POINTS.length)
            ret = DISABLE_POINTS[index];
        
        return ret;
    }
    
    static double[][] nextArrayDoubles(int size,int index)
    {
        double[] values = nextDoubleValues(size, index);
        double[] ratio = nextRatio(index);
        double[][] ret = new double[values.length][ratio.length]; 
        for (int i = 0; i < values.length; i++)
        {
            double total = 0;
            for (double ds : ratio)
                total =  ds;
            
            ret[i] = new double[ratio.length];
            for (int j = 0; j < ret[i].length; j++)
                ret[i][j] = values[i] * ratio[j]/total;
        }
        
        return ret;
    }
    
    static int maxTestCaseSize()
    {
        int maxValue = 0;
        maxValue = Math.max(maxValue, ARRAY_SIZE.length);
        maxValue = Math.max(maxValue, CANVAS_PADDINGS.length);
        maxValue = Math.max(maxValue, DOUBLE_VALUES.length);
        maxValue = Math.max(maxValue, BAR_LISTENERS.length);
        maxValue = Math.max(maxValue, ANIMATION_DURATION.length);
        maxValue = Math.max(maxValue, ANIMATION_START_DELAYS.length);
        maxValue = Math.max(maxValue, VALUE_RATIO.length);
        return maxValue;
    }
    
    static class BarListener implements AnimationBarListener
    {
        int[] mDisablePos;
        
        public BarListener(int... mDisablePos)
        {
            super();
            this.mDisablePos = mDisablePos;
        }

        @Override
        public boolean isPointEnable(int positon, double lineValue)
        {
            for (int pos : mDisablePos)
            {
                if(pos == positon)
                    return false;
            }
            return true;
        }
    }
    
    
    static class LineListener implements AnimationLineListener
    {
        int[] mDisablePos;
        
        public LineListener(int... mDisablePos)
        {
            super();
            this.mDisablePos = mDisablePos;
        }

        @Override
        public void onDrawPoint(AnimationLine animationLine, Canvas canvas, CanvasCoordinate coordinate, ValuePoint point, int positon, Paint paint,
                double lineValue)
        {

            if (positon == 1)
                Utils.drawTriangle(canvas, coordinate, new Path(), point, animationLine.getPointRadius(), paint,
                        animationLine.getRingWidth(), Color.RED, Color.WHITE);
            else
                Utils.drawRingCircular(canvas, paint, point, animationLine.getPointRadius(),
                        animationLine.getRingWidth(), Color.RED, Color.WHITE);
        }

        @Override
        public boolean isPointEnable(int positon, double lineValue)
        {
            for (int pos : mDisablePos)
            {
                if(pos == positon)
                    return false;
            }
            return true;
        }
        
    }
}
