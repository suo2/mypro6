/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdtech.ui.overlayview.BaseLineProperty;
import com.tdtech.ui.overlayview.R;
import com.tdtech.ui.overlayview.animationview.AnimationBar;
import com.tdtech.ui.overlayview.animationview.AnimationBars;
import com.tdtech.ui.overlayview.animationview.AnimationLine;
import com.tdtech.ui.overlayview.common.Utils;
import com.tdtech.ui.overlayview.coordinateview.SidewaysView;


/**
 * Create Date: 2015-4-23<br>
 * Create Author: cWX239309<br>
 * Description : 动画的测试
 */
public class TestAnimationMainActivity extends Activity implements OnClickListener
{
    /** 手动触发的按钮 */
    private Button mManualBtn;
    /** 停止的按钮 */
    private Button mStopBtn;
    /** 开始按钮 */
    private Button mStartBtn;
    /** 继续 */
    private Button mGoonBtn;
    /** 测试的总数 */
    private TextView mSumTest;
    /** 测试现在的数目 */
    private TextView mNowTest;
    /** 测试的数据 */
    private EditText mResult;
    /** 报表的控件 */
    private AnimationBars mAnimationBars;
    private AnimationBar mAnimationBar;
    private AnimationLine mAnimationLine;
    private SidewaysView mCoordinateView;
    private int[] mPhaseColors = new int[]
    { 0xFF8CD0FF, 0XFF3FABF4, 0XFF98AAEF };
    private int mIndex = -1;
    private int mMaxLength = 0;
    private boolean mIsStop = false;
    private LinkedList<AnimationBarDataBuilder> mBarsValueList;
    private LinkedList<AnimationLinesDataBuilder> mLinesValueList;
    private LinkedList<CoordinateViewDataBuilder> coordianteData;
    private LinkedList<AnimationBarsDataBuilder> mSumBarsList;
    private CoordinateViewDataBuilder mCoordinate;
    private long mTimeDuration = 1500L;

    @SuppressLint("HandlerLeak")
    private Handler mControl = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            ++mIndex;
            if (mIndex >= mMaxLength)
            {
                mIndex = -1;
            }
            else
            {
                mNowTest.setText((mIndex + 1) + "");
                generationReport();
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main_activity);
        mManualBtn = (Button) findViewById(R.id.btn_manual);
        mStopBtn = (Button) findViewById(R.id.btn_stop);
        mSumTest = (TextView) findViewById(R.id.tv_test_sum);
        mNowTest = (TextView) findViewById(R.id.tv_test_now);
        mResult = (EditText) findViewById(R.id.tv_test_result);
        mStartBtn = (Button) findViewById(R.id.btn_start);
        mGoonBtn = (Button) findViewById(R.id.btn_goon);
        mManualBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);
        mStartBtn.setOnClickListener(this);
        mGoonBtn.setOnClickListener(this);
        mCoordinateView = new SidewaysView(this);
        mAnimationBar = new AnimationBar(this);
        mAnimationBars = new AnimationBars(this, mPhaseColors);
        mAnimationLine = new AnimationLine(this);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.rlly_report_container);
        layout.addView(mCoordinateView);
        layout.addView(mAnimationBar);
        layout.addView(mAnimationBars);
        layout.addView(mAnimationLine);
        mBarsValueList = AnimationBarDataBuilder.BAR_DATAS;
        mLinesValueList = AnimationLinesDataBuilder.BAR_DATAS;
        coordianteData = CoordinateViewDataBuilder.DATA_BUILDERS;
        mSumBarsList = AnimationBarsDataBuilder.BARS_DATAS;
        mMaxLength = Constant.maxTestCaseSize();
        mSumTest.setText(mMaxLength + "");
        mNowTest.setText("");
    }

    private void generationReport()
    {
        AnimationBarDataBuilder barsData = mBarsValueList.get(mIndex);
        AnimationLinesDataBuilder linesData = mLinesValueList.get(mIndex);
        AnimationBarsDataBuilder sumBarsData = mSumBarsList.get(mIndex);
        mTimeDuration = Math.max(barsData.mAnimDuration, linesData.mAnimDuration);
        double[] mBarsValue = barsData.mBarsValues;
        double[] mLinessValue = linesData.mLinesValues;
        mCoordinate = coordianteData.get(mIndex);
        float maxY = (float) Math.max(Utils.maxValue(mBarsValue), Utils.maxValue(mLinessValue));

        mCoordinateView.setAnimationPadding(mCoordinate.mPaddingRect.left, mCoordinate.mPaddingRect.top,
                mCoordinate.mPaddingRect.right, mCoordinate.mPaddingRect.bottom);
        mCoordinateView.setMaxValueY(mCoordinate.mMaxYValue + mCoordinate.mMaxYValue / 3);
        mCoordinateView.setPointLength(mCoordinate.mPointLength);
        mCoordinateView.setCoordinateAdapter(mCoordinate.mAdapter);
        mCoordinateView.invalidate();

        mAnimationBar.setAnimationPadding(barsData.mPaddingRect.left, barsData.mPaddingRect.top,
                barsData.mPaddingRect.right, barsData.mPaddingRect.bottom);
        mAnimationBar.setMaxValueY((float) (barsData.mMaxYValue + barsData.mMaxYValue / 3));
        mAnimationBar.setAnimationDuration(mTimeDuration);
        mAnimationBar.setAnimationStartDelay(barsData.mAnimStartDelay);
        mAnimationBar.setAnimationBarListener(barsData.listener);
        mAnimationBar.setBarsValue(mBarsValue);

        // mAnimationBars.setAnimationPadding(sumBarsData.mPaddingRect.left, sumBarsData.mPaddingRect.top,
        // sumBarsData.mPaddingRect.right, sumBarsData.mPaddingRect.bottom);
        // mAnimationBars.setMaxValueY((float) (sumBarsData.mMaxYValue + sumBarsData.mMaxYValue / 3));
        // mAnimationBars.setAnimationDuration(mTimeDuration);
        // mAnimationBars.setAnimationStartDelay(100);
        // mAnimationBars.setAnimationBarListener(sumBarsData.listener);
        // mAnimationBars.setBarsValues(sumBarsData.mBarsValues);

        mAnimationLine.setPaintColor(Color.BLUE);
        mAnimationLine.setAnimationPadding(linesData.mPaddingRect.left, linesData.mPaddingRect.top,
                linesData.mPaddingRect.right, linesData.mPaddingRect.bottom);
        mAnimationLine.setMaxValueY((float) (linesData.mMaxYValue + linesData.mMaxYValue / 3));
        mAnimationLine.setAnimationDuration(mTimeDuration);
        mAnimationLine.setAnimationStartDelay(linesData.mAnimStartDelay);
        mAnimationLine.setAnimationLineListener(linesData.listener);
        mAnimationLine.setLinesValues(mLinessValue);

        BaseLineProperty lineProperty = new BaseLineProperty(Color.RED, 1.8f, maxY, "100%");
        mAnimationLine.clearStaticProperty();
        mAnimationLine.addStaticProperty(lineProperty);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_start:
                mIsStop = false;
                start();
                break;
            case R.id.btn_manual:
                mIsStop = true;
                mControl.sendEmptyMessage(1);
                break;
            case R.id.btn_stop:
                mIsStop = true;
                int index = mIndex;
                if (mIndex == -1)
                {
                    index = mMaxLength - 1;
                }
                mNowTest.setText((index + 1) + "");
                if (mIsStop)
                {
                    mResult.setText("mIndex: " + (index + 1) + "\ncoordinate: " + mCoordinate.toString()
                            + "\nBarsValue: " + mBarsValueList.get(index).toString() + "\nLinesValue: "
                            + mLinesValueList.get(index).toString());
                }
                break;
            case R.id.btn_goon:
                mIsStop = false;
                break;
            default:
                break;
        }
    }

    private boolean mThreadOver = true;
    Thread thread = new Thread(new Runnable()
    {

        @Override
        public void run()
        {

            while (mThreadOver)
            {
                int i = mIndex;
                for (; i < mMaxLength && !mIsStop; i++)
                {
                    if (!mIsStop)
                    {
                        mControl.sendEmptyMessage(1);
                    }
                    try
                    {
                        Thread.sleep(mTimeDuration <= 0 ? 1000 : mTimeDuration + 3000);
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                if (i >= mMaxLength)
                {
                    i = 0;
                    mIsStop = true;
                }
            }
        }
    });

    private void start()
    {
        thread.start();
        mStartBtn.setEnabled(false);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mThreadOver = false;
    }

}
