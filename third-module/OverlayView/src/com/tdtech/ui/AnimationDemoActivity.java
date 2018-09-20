/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.tdtech.ui.overlayview.BaseLineProperty;
import com.tdtech.ui.overlayview.CoordinateBaseAdapter;
import com.tdtech.ui.overlayview.R;
import com.tdtech.ui.overlayview.animationview.AnimationBar;
import com.tdtech.ui.overlayview.animationview.AnimationBarListener;
import com.tdtech.ui.overlayview.animationview.AnimationBars;
import com.tdtech.ui.overlayview.animationview.AnimationLine;
import com.tdtech.ui.overlayview.animationview.AnimationLineListener;
import com.tdtech.ui.overlayview.common.CanvasCoordinate;
import com.tdtech.ui.overlayview.common.Utils;
import com.tdtech.ui.overlayview.common.ValuePoint;
import com.tdtech.ui.overlayview.coordinateview.SidewaysView;

/**
 * Create Date: 2015-4-14<br>
 * Create Author: cWX239887<br>
 * Description : 演示用的activity
 */
public class AnimationDemoActivity extends Activity
{

    private double[][] mBarsValues;
    private double[] mBarsValue;
    private double[] mLinessValue;

    EditText mEtSpeed;
    EditText mEtNum;
    Button mButton;

    private AnimationBars mAnimationBars;
    private AnimationBar mAnimationBar;
    private AnimationLine mAnimationLine;
    private SidewaysView mCoordinateView;

    private int[] mPhaseColors = new int[]
    { 0xFF8CD0FF, 0XFF3FABF4, 0XFF98AAEF };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.LinearLayout);
        mEtSpeed = (EditText) findViewById(R.id.editText1);
        mEtNum = (EditText) findViewById(R.id.editText2);
        mCoordinateView = new SidewaysView(this);
        mAnimationBar = new AnimationBar(this);
        mAnimationBars = new AnimationBars(this, mPhaseColors);
        mAnimationLine = new AnimationLine(this);
        layout.addView(mCoordinateView);
        layout.addView(mAnimationBar);
        layout.addView(mAnimationBars);
        layout.addView(mAnimationLine);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(mClickListener);
        mButton.performClick();
    }

    OnClickListener mClickListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            generateData(Integer.parseInt(mEtNum.getText().toString()));
            String sp = mEtSpeed.getText().toString();
            long timeDuration = Long.parseLong(sp);

            float maxY = (float) Math.max(Utils.maxValue(mBarsValue), Utils.maxValue(mLinessValue));

            mCoordinateView.setAnimationPadding(20, 50, 20, 30);
            mCoordinateView.setMaxValueY(maxY + maxY / 3);
            mCoordinateView.setPointLength(mBarsValue.length);
            CoordinateAdapter adapter = new CoordinateAdapter();
            mCoordinateView.setCoordinateAdapter(adapter);
            mCoordinateView.invalidate();

            mAnimationBar.setAnimationPadding(20, 50, 20, 30);
            mAnimationBar.setMaxValueY(maxY + maxY / 3);
            mAnimationBar.setAnimationDuration(timeDuration);
            mAnimationBar.setAnimationStartDelay(100);
            mAnimationBar.setAnimationBarListener(mAnimationBarListener);
            mAnimationBar.setAnimationMinStepSize(2);
//            mAnimationBar.setBarsValue(mBarsValue);

            mAnimationBars.setAnimationPadding(20, 50, 20, 30);
            mAnimationBars.setMaxValueY(maxY + maxY / 3);
            mAnimationBars.setAnimationDuration(timeDuration);
            mAnimationBars.setAnimationStartDelay(50);
            mAnimationBars.setAnimationBarListener(mAnimationBarListener);
            mAnimationBars.setAnimationMinStepSize(2);
            mAnimationBars.setBarsValues(mBarsValues);

            mAnimationLine.setPaintColor(Color.BLUE);
            mAnimationLine.setAnimationPadding(20, 50, 20, 30);
            mAnimationLine.setMaxValueY(maxY + maxY / 3);
            mAnimationLine.setAnimationDuration(timeDuration);
            mAnimationLine.setAnimationStartDelay(100);
            mAnimationLine.setAnimationLineListener(mAnimationLineListener);
            mAnimationLine.setAnimationMinStepSize(2);
            mAnimationLine.setLinesValues(mLinessValue);

            BaseLineProperty lineProperty = new BaseLineProperty(Color.RED, 1.8f, maxY, "100%");
            mAnimationLine.clearStaticProperty();
            mAnimationLine.addStaticProperty(lineProperty);
        }
    };

    public void generateData(int length)
    {
        mBarsValues = new double[length][3];
        mBarsValue = new double[length];
        mLinessValue = new double[length];
        // SecureRandom random = new SecureRandom();

        for (int i = 0; i < mBarsValue.length; i++)
        {
            mBarsValue[i] = Math.sin(i * 15) * 500 + 600;
        }

        for (int i = 0; i < mBarsValues.length; i++)
        {
            double[] ds = mBarsValues[i];
            for (int j = 0; j < ds.length; j++)
                ds[j] = Math.sin(i * 15) * 200 + 300;
        }

        for (int i = 0; i < mLinessValue.length; i++)
        {
            mLinessValue[i] = Math.sin(i * 15) * 800 + 900;
        }
    }

    /** 柱状图监听器 */
    private AnimationBarListener mAnimationBarListener = new AnimationBarListener()
    {

        @Override
        public boolean isPointEnable(int positon, double lineValue)
        {
            if (positon == 3)
                return false;
            else return true;
        }
    };

    /** 线条监听器 */
    private AnimationLineListener mAnimationLineListener = new AnimationLineListener()
    {

        @Override
        public void onDrawPoint(AnimationLine animationLine, Canvas canvas, CanvasCoordinate coordinate,
                ValuePoint point, int positon, Paint paint, double lineValue)
        {

            if (positon == 1)
            {
                Utils.drawTriangle(canvas, coordinate, new Path(), point, animationLine.getPointRadius(), paint,
                        animationLine.getRingWidth(), Color.RED, Color.WHITE);
            }
            else
            {
                Utils.drawEdgeCircular(canvas, paint, point, animationLine.getPointRadius(), Color.BLUE, Color.BLUE);
            }
        }

        @Override
        public boolean isPointEnable(int positon, double lineValue)
        {
            if (positon == 3)
                return false;
            else return true;
        }
    };

    public class CoordinateAdapter extends CoordinateBaseAdapter
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
            if (position == 3)
            {
                return Color.GREEN;
            }
            return Color.BLUE;
        }

    }
}
