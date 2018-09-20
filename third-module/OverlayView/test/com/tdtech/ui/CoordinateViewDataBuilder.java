/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui;

import java.util.LinkedList;

import com.tdtech.ui.overlayview.CoordinateBaseAdapter;

import android.graphics.Color;
import android.graphics.RectF;

/**
 * Create Date: 2015-4-23<br>
 * Create Author: cWX239887<br>
 * Description : 坐标控件数据构造器
 */
public class CoordinateViewDataBuilder
{

    /** 画布 padding */
    RectF mPaddingRect;
    /** Y坐标最大值 */
    float mMaxYValue;
    /** X坐标绘制的点数目 */
    int mPointLength;
    /** 适配器 */
    CoordinateBaseAdapter mAdapter;

    static final LinkedList<CoordinateViewDataBuilder> DATA_BUILDERS = new LinkedList<CoordinateViewDataBuilder>();

    static
    {
        CoordinateViewDataBuilder data = null;
        RectF rectF = null;
        float maxYValue = 100;
        int pointLength = 12;
        CoordinateBaseAdapter adapter = new CoordinateAdapter();
        int maxTestSize = Constant.maxTestCaseSize();
        for (int i = 0; i < maxTestSize + 1; i++)
        {
            pointLength = Constant.nextArraySize(i);
            rectF = Constant.nextRectPadding(i);
            data = new CoordinateViewDataBuilder(rectF, maxYValue, pointLength, adapter);
            DATA_BUILDERS.add(data);
        }
    }

    public CoordinateViewDataBuilder(RectF mPaddingRect, float mMaxYValue, int mPointLength,
            CoordinateBaseAdapter mAdapter)
    {
        super();
        this.mPaddingRect = mPaddingRect;
        this.mMaxYValue = mMaxYValue;
        this.mPointLength = mPointLength;
        this.mAdapter = mAdapter;
    }

    static class CoordinateAdapter extends CoordinateBaseAdapter
    {

        @Override
        public String getText(int positon, int totalPositon)
        {
            return String.valueOf(positon + 1);
        }

        @Override
        public String getMaxText(int totalPositon)
        {
            return String.valueOf(totalPositon);
        }

        @Override
        public int getColor(int position, int totalPosition)
        {
            return Color.BLUE;
        }

    }

    @Override
    public String toString()
    {
        return "CoordinateViewDataBuilder [mPaddingRect=" + mPaddingRect + ", mMaxYValue=" + mMaxYValue
                + ", mPointLength=" + mPointLength + ", mAdapter=" + mAdapter + "]";
    }
}
