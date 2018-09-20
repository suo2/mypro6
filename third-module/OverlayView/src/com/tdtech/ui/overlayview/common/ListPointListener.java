/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.tdtech.ui.overlayview.common;

import android.animation.Animator;

/**
 * Create Date: 2015-3-27<br>
 * Create Author: cWX239887<br>
 * Description : 列表动画监听器
 */
public interface ListPointListener
{
    void onAnimationCancel(int position, Animator animation);

    void onAnimationEnd(int position, Animator animation);

    void onAnimationRepeat(int position, Animator animation);

    void onAnimationStart(int position, Animator animation);

    void onAnimationUpdate(int position, Animator animation);

}
