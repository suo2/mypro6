package com.tdtech.ui.overlayview.common;

/**
 * Create Date: 2015-3-24<br>
 * Create Author: cWX239887<br>
 * Description :坐标系转换类（画布坐标系<-->笛卡尔坐标系）
 */
public class CanvasCoordinate
{
    /** X轴最大值（单位：根据输入数值决定） */
    private float mMaxX;
    /** Y轴最大值 （单位：根据输入数值决定） */
    private float mMaxY;
    /** 画布高度（单位：像素） */
    private float mCanvasHeight;
    /** 画布宽度 （单位：像素） */
    private float mCanvasWidth;
    /** 画布左侧边距（单位：像素） */
    private float mCanvasPaddingLeft;
    /** 画布顶部边距（单位：像素） */
    private float mCanvasPaddingTop;
    /** 画布右侧边距（单位：像素） */
    private float mCanvasPaddingRight;
    /** 画布底部边距（单位：像素） */
    private float mCanvasPaddingBottom;
    /** 画布与数值的转换比例 */
    private float mCanvasAndValueRatio;

    public static final float MIN_VALUE_Y = 1;
    public CanvasCoordinate()
    {
        super();
    }

    public float getValidCanvasWidth()
    {
        float ret = mCanvasWidth - mCanvasPaddingLeft - mCanvasPaddingRight;
        return ret < 0 ? 0 : ret;
    }

    public float getValidCanvasHeight()
    {
        float ret = mCanvasHeight - mCanvasPaddingTop - mCanvasPaddingBottom;
        return ret < 0 ? 0 : ret;
    }

    /**
     * 获取X轴数值在画布上的坐标
     * 
     * @param valueX
     *            输入的坐标值（X轴）
     * @return 返回画布上的X轴坐标（单位：像素）
     */
    public float getCanvasX(float valueX)
    {
        if (valueX < 0)
            valueX = 0;

        // 获取可用画布宽度
        float validCanvasWidth = getValidCanvasWidth();
        if(mMaxX <= 0)
            return validCanvasWidth;
        
        // 计算X轴实际坐标
        float pixelX = (valueX / mMaxX) * validCanvasWidth;
        // 计算X轴画布坐标
        float canvasX = pixelX + mCanvasPaddingLeft;
        return canvasX;
    }

    public float getValueX(float canvasX)
    {
        // 获取可用画布宽度
        float validCanvasWidth = getValidCanvasWidth();

        if (validCanvasWidth <= 0)
            return mMaxX;

        float pixelX = canvasX - mCanvasPaddingLeft;
        float valueX = (pixelX / validCanvasWidth) * mMaxX;
        return valueX;
    }

    /**
     * 获取Y轴数值在画布上的坐标
     * 
     * @param valueY
     *            输入的坐标值（Y轴）
     * @return 返回画布上的Y轴坐标（单位：像素）
     */
    public float getCanvasY(float valueY)
    {
        if (valueY < 0)
            valueY = 0;

        // 获取可用画布高度
        float validCanvasHight = getValidCanvasHeight();
        if(mMaxY <= 0)
            return validCanvasHight;
            
        // 计算Y轴实际坐标
        float pixelY = (valueY / mMaxY) * validCanvasHight;
        // 计算Y轴画布坐标
        float canvasY = (validCanvasHight - pixelY) + mCanvasPaddingTop;
        return canvasY;
    }

    public float getValueY(float canvasY)
    {
        // 获取可用画布高度
        float validCanvasHight = getValidCanvasHeight();
        if (validCanvasHight <= 0)
            return mMaxY;

        float pixelY = mCanvasPaddingTop - canvasY + validCanvasHight;
        float valueY = (pixelY / validCanvasHight) * mMaxY;
        return valueY;
    }

    public float getMaxX()
    {
        return mMaxX;
    }

    public float getMaxY()
    {
        return mMaxY;
    }

    public float getCanvasHeight()
    {
        return mCanvasHeight;
    }

    public float getCanvasWidth()
    {
        return mCanvasWidth;
    }

    public float getCanvasPaddingLeft()
    {
        return mCanvasPaddingLeft;
    }

    public float getCanvasPaddingTop()
    {
        return mCanvasPaddingTop;
    }

    public float getCanvasPaddingRight()
    {
        return mCanvasPaddingRight;
    }

    public float getCanvasPaddingBottom()
    {
        return mCanvasPaddingBottom;
    }

    public float getCanvasAndValueRatio()
    {
        return mCanvasAndValueRatio;
    }

    public void setMaxX(float maxY)
    {
        // 获取可用画布宽度
        float validCanvasWidth = getValidCanvasWidth();
        // 获取可用画布高度
        float validCanvasHight = getValidCanvasHeight();
        if(validCanvasHight <= 0)
            mMaxX = maxY;
        else
            mMaxX = validCanvasWidth / validCanvasHight * maxY;
        
        //计算数值与画布的转换比例
        if(mMaxX <= 0)
            mCanvasAndValueRatio = 1;
        else
            mCanvasAndValueRatio = validCanvasWidth > 0 ? validCanvasWidth / mMaxX : 1; 
    }

    public void setMaxY(float maxY)
    {
        if(maxY <= 0)
            mMaxY = MIN_VALUE_Y;
        else
            mMaxY = maxY;
    }

    public void setCanvasHeight(float canvasHeight)
    {
        mCanvasHeight = canvasHeight;
    }

    public void setCanvasWidth(float canvasWidth)
    {
        mCanvasWidth = canvasWidth;
    }

    public void setCanvasPaddingLeft(float canvasPaddingLeft)
    {
        mCanvasPaddingLeft = canvasPaddingLeft < 0 ? 0 : canvasPaddingLeft;
    }

    public void setCanvasPaddingTop(float canvasPaddingTop)
    {
        mCanvasPaddingTop = canvasPaddingTop < 0 ? 0 : canvasPaddingTop;
    }

    public void setCanvasPaddingRight(float canvasPaddingRight)
    {
        mCanvasPaddingRight = canvasPaddingRight < 0 ? 0 : canvasPaddingRight;
    }

    public void setCanvasPaddingBottom(float canvasPaddingBottom)
    {
        mCanvasPaddingBottom = canvasPaddingBottom < 0 ? 0 : canvasPaddingBottom;
    }

    public void setCanvasAndValueRatio(float canvasAndValueRatio)
    {
        mCanvasAndValueRatio = canvasAndValueRatio;
    }

    @Override
    public String toString()
    {
        return "CanvasCoordinate [mMaxX=" + mMaxX + ", mMaxY=" + mMaxY + ", mCanvasHeight=" + mCanvasHeight
                + ", mCanvasWidth=" + mCanvasWidth + ", mCanvasPaddingLeft=" + mCanvasPaddingLeft
                + ", mCanvasPaddingTop=" + mCanvasPaddingTop + ", mCanvasPaddingRight=" + mCanvasPaddingRight
                + ", mCanvasPaddingBottom=" + mCanvasPaddingBottom + "]";
    }
}