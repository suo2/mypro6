/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.animationview;

/**
 * Create Date: 2015-4-14<br>
 * Create Author: cWX239887<br>
 * Description : 动画效果的柱状图监听器
 */
public interface AnimationBarListener
{
    /** 当前节点是否需要绘制 */
    boolean isPointEnable(int positon, double lineValue);
}
