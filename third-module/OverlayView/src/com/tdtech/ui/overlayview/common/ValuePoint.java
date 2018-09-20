/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.common;

/**
 * Create Date: 2015-3-25<br>
 * Create Author: cWX239887<br>
 * Description : 画布坐标类
 */
public class ValuePoint
{
    /** 数值X坐标 */
    public final float mValueX;
    /** 数值Y坐标 */
    public final float mValueY;
    /** 画布X坐标 */
    public float mCanvasX;
    /** 画布X坐标 */
    public float mCanvasY;
    /** 当前坐标点的索引 */
    public int mPosition;

    public ValuePoint(float valueX, float valueY)
    {
        super();
        mValueX = valueX;
        mValueY = valueY;
    }

    public float getCanvasX()
    {
        return mCanvasX;
    }

    public float getCanvasY()
    {
        return mCanvasY;
    }

    public void setCanvasX(float canvasX)
    {
        mCanvasX = canvasX;
    }

    public void setCanvasY(float canvasY)
    {
        mCanvasY = canvasY;
    }

    public float getValueX()
    {
        return mValueX;
    }

    public float getValueY()
    {
        return mValueY;
    }

    public int getPosition()
    {
        return mPosition;
    }

    public void setPosition(int position)
    {
        mPosition = position;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(mCanvasX);
        result = prime * result + Float.floatToIntBits(mCanvasY);
        result = prime * result + mPosition;
        result = prime * result + Float.floatToIntBits(mValueX);
        result = prime * result + Float.floatToIntBits(mValueY);
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ValuePoint other = (ValuePoint) obj;
        if (Float.floatToIntBits(mCanvasX) != Float.floatToIntBits(other.mCanvasX))
            return false;
        if (Float.floatToIntBits(mCanvasY) != Float.floatToIntBits(other.mCanvasY))
            return false;
        if (mPosition != other.mPosition)
            return false;
        if (Float.floatToIntBits(mValueX) != Float.floatToIntBits(other.mValueX))
            return false;
        if (Float.floatToIntBits(mValueY) != Float.floatToIntBits(other.mValueY))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "ValuePoint [mValueX=" + mValueX + ", mValueY=" + mValueY + ", mCanvasX=" + mCanvasX + ", mCanvasY="
                + mCanvasY + ", mPosition=" + mPosition + "]";
    }

}
