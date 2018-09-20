/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview;

/**
 * Create Date: 2015-4-14<br>
 * Create Author: cWX239887<br>
 * Description : 坐标控件监听器
 */
public abstract class CoordinateBaseAdapter
{
    /** 获取相应位置的字符串 */
    public abstract String getText(int position, int totalPositon);

    /** 获取最大的字符串，用于测量字符宽度 */
    public abstract String getMaxText(int totalPositon);

    /** 获取指定点的颜色 */
    public abstract int getColor(int position, int totalPosition);
}
